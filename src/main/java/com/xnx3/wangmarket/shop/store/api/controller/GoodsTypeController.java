package com.xnx3.wangmarket.shop.store.api.controller;

import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.xnx3.wangmarket.shop.store.api.vo.GoodsTypeVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.xnx3.BaseVO;
import com.xnx3.j2ee.generateCache.BaseGenerate;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.AttachmentUtil;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.Sql;
import com.xnx3.j2ee.vo.UploadFileVO;
import com.xnx3.wangmarket.shop.core.Global;
import com.xnx3.wangmarket.shop.core.entity.GoodsType;
import com.xnx3.wangmarket.shop.core.service.GoodsTypeService;
import com.xnx3.wangmarket.shop.core.vo.bean.GoodsTypeBean;
import com.xnx3.wangmarket.shop.store.api.vo.GoodsTypeListVO;


/**
 * 商品分类管理控制器
 * @author 管雷鸣
 */
@Controller(value="ShopStoreApiGoodsTypeController")
@RequestMapping("/shop/store/api/goodsType")
public class GoodsTypeController extends BaseController {
	@Resource
	private SqlService sqlService;
	@Resource
	private GoodsTypeService goodsTypeService;
	
	/**
	 * 查看商品分类
	 * @param everyNumber 每页显示多少条数据。取值 1～100，最大显示100条数据，若传入超过100，则只会返回100条
	 * @param currentPage 要查看第几页，如要查看第2页，则这里传入 2
	 */
	@ResponseBody
	@RequestMapping(value = "/list${api.suffix}" ,method = {RequestMethod.POST})
    public GoodsTypeListVO list(HttpServletRequest request,
                                @RequestParam(value = "everyNumber", required = false, defaultValue = "15") int everyNumber) {
		GoodsTypeListVO vo = new GoodsTypeListVO();
		
		//创建Sql
		Sql sql = new Sql(request);
        //配置按某个字端搜索内容
        sql.setSearchColumn(new String[] {"title"});
		//配置查询那个表
		sql.setSearchTable("shop_goods_type");
		//查询条件
		sql.appendWhere("isdelete = " + GoodsType.ISDELETE_NORMAL);
		sql.appendWhere("storeid = " + getStoreId());
		
		// 查询数据表的记录总条数
		int count = sqlService.count("shop_goods_type", sql.getWhere());
		
		// 配置每页显示200条，也就是一次全部显示出来
        Page page = new Page(count, everyNumber, request);
		// 查询出总页数
		sql.setSelectFromAndPage("SELECT * FROM shop_goods_type ", page);
		//选择排序方式 当用户没有选择排序方式时，系统默认降序排序
		sql.setDefaultOrderBy("rank ASC");
		
		// 按照上方条件查询出该实体总数 用集合来装
		List<GoodsType> list = sqlService.findBySql(sql,GoodsType.class);
		
		vo.setPage(page);
		vo.setList(list);
		
		//日志记录
		ActionLogUtil.insert(request, getStoreId(), "查看商品分类列表");
		return vo;
	}
	
	/**
	 * 上传商品分类图片
	 * @author 关光礼
	 * @param id 上传商品分类id
	 * @param file 上传的图片文件
	 */
	@ResponseBody
	@RequestMapping(value = "/uploadImg${api.suffix}" ,method = {RequestMethod.POST})
	public BaseVO goodsTypeUploadImg(HttpServletRequest request ,
			@RequestParam(value = "id", required = false, defaultValue = "0") int id,
			MultipartFile file) {
		
		// 校验参数
		if(id < 1) {
			return error("ID信息错误");
		}
		// 上传图片
		String path = Global.ATTACHMENT_FILE_CAROUSEL_IMAGE.replace("{storeid}", getStoreId()+"");
		UploadFileVO vo = AttachmentUtil.uploadImageByMultipartFile(path+"goodsType/", file);
		
		if(vo.getResult() == 0) {
			return error(vo.getInfo());
		}
		// 修改 url
		GoodsType goodsType = sqlService.findById(GoodsType.class, id);
		if(goodsType == null){
			return error("要修改的分类不存在");
		}
		if(goodsType.getStoreid() - getStoreId() != 0){
			return error("该分类不属于你，无法操作。");
		}
		goodsType.setIcon(vo.getUrl());
		sqlService.save(goodsType);
		//日志记录
		ActionLogUtil.insertUpdateDatabase(request, id, "Id为" + id + "的商品分类上传图片", "上传图片返回路径:" + vo.getUrl());
		return success();
	}
	
	/**
	 * 获取商品分类信息
	 * @author 刘鹏
	 * @param id 商品分类id
	 */
    @ResponseBody
    @RequestMapping(value = "getGoodsType${api.suffix}" ,method = {RequestMethod.POST})
    public GoodsTypeVO getGoodsType(HttpServletRequest request,
                                  @RequestParam(value = "id", required = false, defaultValue = "0") int id) {
		GoodsTypeVO vo = new GoodsTypeVO();
    	if(id < 1){
			vo.setResult(BaseVO.FAILURE);
		}
        if(id > 0) {
            GoodsType goodsType = sqlService.findById(GoodsType.class, id);
            if(goodsType == null){
                vo.setBaseVO(BaseVO.FAILURE,"要修改的分类不存在");
            }
            if(goodsType.getStoreid() - getStoreId() != 0){
                vo.setBaseVO(BaseVO.FAILURE,"该分类不属于你，无法操作。");
            }
            vo.setGoodsType(goodsType);
            ActionLogUtil.insert(request, getUserId(), "查看商品分类ID为" + id+ "的详情，跳转到编辑页面");
        }
        return vo;
    }

	/**
	 * 添加修改商品分类
	 * @author 关光礼
	 * @param goodsType 接收参数的实体类
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/save${api.suffix}",method = {RequestMethod.POST})
	public com.xnx3.j2ee.vo.BaseVO save(HttpServletRequest request,GoodsType goodsType) {

		//System.out.println(getStoreId());
		Integer id = goodsType.getId();
		//创建一个实体
		GoodsType fGoodsType;
		if(id == null) {
			// 添加
			fGoodsType = new GoodsType();
			fGoodsType.setIsdelete(GoodsType.ISDELETE_NORMAL);
			fGoodsType.setStoreid(getStoreId());
		}else {
			//修改
			fGoodsType = sqlService.findById(GoodsType.class, id);
			if(fGoodsType == null){
				return error("要修改的分类不存在");
			}
			if(fGoodsType.getStoreid() - getStoreId() != 0){
				return error("该分类不属于你，无法操作。");
			}
		}
		
		//给实体赋值
		fGoodsType.setRank(goodsType.getRank());
		fGoodsType.setTitle(goodsType.getTitle());
		//判断是否有图片地址，有就赋值
		if(goodsType.getIcon() != null && !goodsType.getIcon().trim().equals("")) {
			fGoodsType.setIcon(goodsType.getIcon());
		}
		//保存实体
		sqlService.save(fGoodsType);
		
		//日志记录
		ActionLogUtil.insertUpdateDatabase(request, fGoodsType.getId(),"Id为" + fGoodsType.getId() + "的商品分类添加或修改，内容:" + fGoodsType.toString());

		goodsTypeService.clearCache(getStoreId());//清除缓存
		return success();
	}
	
	/**
	 * 删除商品分类
	 * @author 关光礼
	 * @param id 删除商品分类id
	 */
	@ResponseBody
	@RequestMapping(value="/delete${api.suffix}",method = {RequestMethod.POST})
	public BaseVO delete(HttpServletRequest request,
			@RequestParam(value = "id",defaultValue = "0", required = false) int id) {
		
		if(id < 1) {
			return error("请传入id参数");
		}
		
		GoodsType goodsType = sqlService.findById(GoodsType.class, id);
		if(goodsType == null) {
			return error("根据ID,没查到该实体");
		}
		if(goodsType.getStoreid() - getStoreId() != 0){
			return error("该分类不属于你，无法操作。");
		}
		goodsType.setIsdelete(GoodsType.ISDELETE_DELETE);
		sqlService.save(goodsType);
		
		goodsTypeService.clearCache(getStoreId());//清除缓存
		
		//日志记录
		ActionLogUtil.insertUpdateDatabase(request, "删除ID是" + id + "的商品分类", "删除内容:" + goodsType.toString());
		return success();
	}
	
	@ResponseBody
	@RequestMapping(value="/getGoodsTypeJs${api.suffix}",method = {RequestMethod.GET})
	public void getGoodsTypeJs(HttpServletRequest request,HttpServletResponse response) {
		List<GoodsTypeBean> list = goodsTypeService.getGoodsType(getStoreId());
		
		BaseGenerate generate = new BaseGenerate();
		generate.createCacheObject("typeid");
		for (int i = 0; i < list.size(); i++) {
			generate.cacheAdd(list.get(i).getId(), list.get(i).getTitle());
		}
		generate.addCommonJsFunction();
		
		//输出
		response.setCharacterEncoding("utf-8");//第一句，设置服务器端编码
		response.setContentType("text/html;charset=utf-8");//第二句，设置浏览器端解码
		try {
			response.getWriter().write(generate.content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
