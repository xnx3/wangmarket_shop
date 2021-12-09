package com.xnx3.wangmarket.shop.api.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xnx3.j2ee.pluginManage.controller.BasePluginController;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.Sql;
import com.xnx3.wangmarket.shop.core.entity.Goods;
import com.xnx3.wangmarket.shop.core.service.GoodsService;
import com.xnx3.wangmarket.shop.core.vo.GoodsDetailsVO;
import com.xnx3.wangmarket.shop.core.vo.GoodsListVO;

/**
 * 商品相关
 * @author 管雷鸣
 */
@Controller(value="ShopGoodsController")
@RequestMapping("/shop/api/goods/")
public class GoodsController extends BasePluginController {
	@Resource
	private SqlService sqlService;
	@Resource
	private GoodsService goodsService;

	/**
	 * 获取商品列表
	 * @param storeid 要获取哪个商铺的商品，这里是商店的ID，Store.id。  必填
	 * @param typeid 要查询商铺中，哪个分类的商品。如果不传，则是查询这个商店中所有的商品
	 * @param title 要搜索的商品名字,可模糊搜索。
	 * 				<p>比如搜索白菜，可以搜索白菜花、白菜叶、白菜根</p>
	 * @param orderBy 商品排序方式
	 * 				<ul>
	 * 					<li>0:默认排序方式，也就是根据商家后台自定义的商品排序，来进行的排序</li>
	 * 					<li>1:按总销量由高往低来排序</li>
	 * 					<li>2:最新商品，按发布时间，由高往低来排序，最后发布的商品在最前面</li>
	 * 				</ul>
	 * 				如果不传此参数，默认按照 0 进行排序
	 * @param everyNumber 每页显示多少条数据。取值 1～100，
	 * 					<p>最大显示100条数据，若传入超过100，则只会返回100条<p>
	 * 					<p>若不传，默认显示15条</p>
	 * @param currentPage 要查看第几页，如要查看第2页，则这里传入 2
	 * @author 管雷鸣
	 * @return {@link GoodsListVO}
	 */
	@RequestMapping(value="list.json", method = RequestMethod.POST)
	@ResponseBody
	public GoodsListVO list(HttpServletRequest request,
			@RequestParam(value = "storeid", required = true, defaultValue="1") int storeid,
			@RequestParam(value = "typeid", required = false, defaultValue="0") int typeid,
			@RequestParam(value = "orderBy", required = false, defaultValue="0") int orderBy,
			@RequestParam(value = "title", required = false, defaultValue="") String title,
			@RequestParam(value = "everyNumber", required = false, defaultValue = "15") int everyNumber){
		GoodsListVO vo = new GoodsListVO();
		if(everyNumber > 100){
			everyNumber = 100;
		}
		if(storeid < 1){
			vo.setBaseVO(GoodsListVO.FAILURE, "您要查哪个店铺的商品");
			return vo;
		}
		//排序方式
		String orderBySql = "";
		switch (orderBy) {
		case 1:
			orderBySql = "sale DESC";
			break;
		case 2:
			orderBySql = "id ASC";
			break;
		default:
			orderBySql = "rank ASC"; 
			break;
		}
		Sql sql = new Sql(request);
	    /*
	     * 设置可搜索字段。这里填写的跟user表的字段名对应。只有这里配置了的字段，才会有效。这里没有配置，则不会进行筛选
	     * 具体规则可参考： http://note.youdao.com/noteshare?id=3ccef2de6a5cda01f95f832b02e356d0&sub=D53E681BBFF04822977C7CFBF8827863
	     */
	    sql.setSearchColumn(new String[]{"storeid=","title"});
	    if(typeid > 0) {
	    	sql.appendWhere("typeid = " + typeid);
	    }
	    sql.appendWhere("isdelete = "+Goods.ISDELETE_NORMAL);	//正常状态，未被删除的
	    sql.appendWhere("putaway = "+Goods.PUTAWAY_SELL);		//上下架状态是正常上架中的
	    //查询user数据表的记录总条数。 传入的user：数据表的名字为user
	    int count = sqlService.count("shop_goods", sql.getWhere());
	    //创建分页，并设定每页显示15条
	    Page page = new Page(count, everyNumber, request);
	    //创建查询语句，只有SELECT、FROM，原生sql查询。其他的where、limit等会自动拼接
	    sql.setSelectFromAndPage("SELECT * FROM shop_goods", page);
	    sql.setOrderBy(orderBySql);
	    //因只查询的一个表，所以可以将查询结果转化为实体类，用List接收。
	    List<Goods> list = sqlService.findBySql(sql, Goods.class);
	    vo.setList(list);
	    vo.setPage(page);
	    //日志记录
	    ActionLogUtil.insert(request, storeid,"获取商品列表");	
		return vo;
	}
	

	/**
	 * 获取某个商品的商品详情信息
	 * @param goodsid 要查看的商品id，goods.id
	 * @return GoodsDetailsVO
	 * @author 管雷鸣
	 */
	@RequestMapping(value="detail.json", method = RequestMethod.POST)
	@ResponseBody
	public GoodsDetailsVO detail(HttpServletRequest request,
			@RequestParam(value = "goodsid", required = true, defaultValue="0") int goodsid){
		//日志记录
		ActionLogUtil.insert(request, goodsid,"获取商品详情");	
		return goodsService.getGoodsDetail(goodsid);
	}
	
}