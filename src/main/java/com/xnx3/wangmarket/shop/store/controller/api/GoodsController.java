package com.xnx3.wangmarket.shop.store.controller.api;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import com.xnx3.DateUtil;
import com.xnx3.wangmarket.shop.core.entity.GoodsImage;
import com.xnx3.wangmarket.shop.store.vo.GoodsDetailsVO;
import com.xnx3.wangmarket.shop.store.vo.GoodsImageListVO;
import com.xnx3.wangmarket.shop.store.vo.GoodsImageVO;
import com.xnx3.wangmarket.shop.store.vo.GoodsListVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xnx3.j2ee.service.SqlCacheService;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.Sql;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.Goods;
import com.xnx3.wangmarket.shop.core.entity.GoodsData;


/**
 * 商品管理
 * @author 管雷鸣
 */
@Controller(value="ShopStoreApiGoodsController")
@RequestMapping("/shop/store/api/goods/")
public class GoodsController extends BaseController {
	@Resource
	private SqlService sqlService;
	@Resource
	private SqlCacheService sqlCacheService;

	/**
	 * 获取商品列表
	 * @param everyNumber 每页显示多少条数据。取值 1～100，
     *                  <p>最大显示100条数据，若传入超过100，则只会返回100条<p>
     *                  <p>若不传，默认显示15条</p>
	 * @param currentPage 要查看第几页，如要查看第2页，则这里传入 2
	 * @param token 当前操作用户的登录标识 <required>
     *           <p>可通过 <a href="shop.store.api.login.login.json.html">/shop/store/api/login/login.json</a> 取得 </p>
     * @return 若请求成功，则展示商品列表
     * @author 刘鹏
	 */
	@ResponseBody
	@RequestMapping(value = "/list.json" ,method = {RequestMethod.POST})
	public GoodsListVO list(HttpServletRequest request,
							@RequestParam(value = "everyNumber", required = false, defaultValue = "15") int everyNumber) {
		GoodsListVO vo = new GoodsListVO();
		
		//创建Sql
		Sql sql = new Sql(request);
		//配置查询那个表
		sql.setSearchTable("shop_goods");
		//查询条件
		sql.appendWhere("isdelete = " + Goods.ISDELETE_NORMAL);
		sql.appendWhere("storeid = " + getStoreId());
		//配置按某个字端搜索内容
		sql.setSearchColumn(new String[] {"title","typeid"});
		// 查询数据表的记录总条数
		int count = sqlService.count("shop_goods", sql.getWhere());
		
		// 配置每页显示15条
		Page page = new Page(count, everyNumber, request);
		// 查询出总页数
		sql.setSelectFromAndPage("SELECT * FROM shop_goods ", page);
		//选择排序方式 当用户没有选择排序方式时，系统默认降序排序
		sql.setDefaultOrderBy("rank ASC");
		//其余可以选择的排序字段
		sql.setOrderByField(new String[]{"id","inventory","sale","price"});
		// 按照上方条件查询出该实体总数 用集合来装
		List<Goods> list = sqlService.findBySql(sql,Goods.class);
		
		vo.setList(list);
		vo.setPage(page);
		//日志记录
		ActionLogUtil.insert(request, getUserId(), "查看商品列表");
		return vo;
	}

	/**
	 * 获取商品与商品详情
	 * 
	 * @param id 商品id
	 * @param token 当前操作用户的登录标识 <required>
     *           <p>可通过 <a href="shop.store.api.login.login.json.html">/shop/store/api/login/login.json</a> 取得 </p>
     * @return 若请求成功，则展示商品的详情
     * @author 刘鹏
	 */
	@ResponseBody
	@RequestMapping(value = "getGoods.json" ,method = {RequestMethod.POST})
	public GoodsDetailsVO getGoods(HttpServletRequest request,
								   @RequestParam(value = "id", required = false, defaultValue = "0") int id) {
		GoodsDetailsVO vo = new GoodsDetailsVO();

		if(id < 1){
			vo.setBaseVO(GoodsDetailsVO.FAILURE, "请传入商品id");
			return vo;
		}

		//查找商品
		Goods goods = sqlService.findById(Goods.class, id);
		if(goods == null){
			vo.setBaseVO(BaseVO.FAILURE, "商品不存在");
		}
		if(goods.getStoreid() - getStoreId() != 0){
			vo.setBaseVO(BaseVO.FAILURE, "商品不属于你");
		}
		vo.setGoods(goods);
		//查找商品描述
		GoodsData goodsData = sqlService.findAloneByProperty(GoodsData.class, "id", id);
		vo.setGoodsData(goodsData);

		ActionLogUtil.insert(request, getUserId(), "查看商品ID为" + (id == 0 ? "":id)+ "的详情，跳转到编辑页面");
		return vo;
	}


	/**
	 * 保存商品信息，简单的商品信息保存，适用于手机端
	 * @param token 当前操作用户的登录标识 <required>
     *           <p>可通过 <a href="shop.store.api.login.login.json.html">/shop/store/api/login/login.json</a> 取得 </p>
	 * @param goodsid 要修改的商品id， goods.id ， 必填
	 * @param price 要修改的商品的价格，单位是分。 非必填
	 * @param putaway 是否上架在售，1出售中，0已下架。 非必填
	 * @param units 计量，单位。如个、斤、条，限制5字符。非必填
	 * @param rank 排序，数字越小越靠前。非必填
	 * @return 若请求成功，则可以保存商品的信息
	 * @author 管雷鸣
	 */
	@ResponseBody
	@RequestMapping(value="/save.json",method = {RequestMethod.POST})
	public BaseVO save(HttpServletRequest request,
			@RequestParam(value = "goodsid", required = false, defaultValue="0") int goodsid,
			@RequestParam(value = "price", required = false, defaultValue="-1") int price,
			@RequestParam(value = "putaway", required = false, defaultValue="-1") short putaway,
			@RequestParam(value = "units", required = false, defaultValue="") String units,
			@RequestParam(value = "rank", required = false , defaultValue="-1") int rank) {
		if(goodsid < 1) {
			return error("请传入要修改的商品id");
		}

		Goods goods = sqlService.findById(Goods.class, goodsid);
		if(goods == null) {
			return error("商品不存在");
		}
		if(goods.getStoreid() - getStoreId() != 0){
			return error("商品不属于你");
		}

		/*** 进行保存商品 ***/
		if(price > -1){
			goods.setPrice(price);
		}
		if(putaway > -1){
			goods.setPutaway(putaway);
		}
		if(!units.equals("")){
			goods.setUnits(units);
		}
		if(rank > -1){
			goods.setRank(rank);
		}
		sqlService.save(goods);
		
		
		//保存完成后，删除缓存，已达到更新缓存目的
		deleteGoodsCache(goods.getId());
		//日志记录
		ActionLogUtil.insertUpdateDatabase(request, goods.getId(),"Id为" + goods.getId() + "的商品添加或修改，内容:" + goods.toString());
		return success();
	}


	/**
	 * 保存商品，商品全部信息的保存
	 * @param token 当前操作用户的登录标识 <required>
     *           <p>可通过 <a href="shop.store.api.login.login.json.html">/shop/store/api/login/login.json</a> 取得 </p>
	 * @param inputGoods 接受参数的实体类
	 * @return 若请求成功，则保存商品的全部信息
	 * @author 刘鹏
	 */
	@ResponseBody
	@RequestMapping(value="/saveAll.json",method = {RequestMethod.POST})
	public com.xnx3.j2ee.vo.BaseVO saveAll(HttpServletRequest request,Goods inputGoods) {

		Integer id = inputGoods.getId();
		//创建一个实体
		Goods goods;
		if(id == null || id - 0 == 0) {
			// 添加
			goods = new Goods();
			goods.setIsdelete(Goods.ISDELETE_NORMAL);
			goods.setAddtime(DateUtil.timeForUnix10());
			goods.setSale(0);
			goods.setStoreid(getStoreId());
			goods.setUserBuyRestrict(0);
			goods.setStoreid(getStoreId());
		}else {
			//修改
			goods = sqlService.findById(Goods.class, id);
			if(goods == null) {
				return error("根据ID,没查到该商品");
			}
			if(goods.getStoreid() - getStoreId() != 0){
				return error("商品不属于你");
			}
		}
		//接受时间参数

		//给实体赋值
		if(inputGoods.getAlarmNum() == null) {
			goods.setAlarmNum(0);
		}else {
			goods.setAlarmNum(inputGoods.getAlarmNum());
		}

		if(inputGoods.getFakeSale() == null) {
			goods.setFakeSale(0);
		}else {
			goods.setFakeSale(inputGoods.getFakeSale());
		}

		if(inputGoods.getPutaway() == null) {
			goods.setPutaway(Goods.PUTAWAY_SELL);
		}else {
			goods.setPutaway(inputGoods.getPutaway());
		}

		if(inputGoods.getUnits() == null || inputGoods.getUnits().equals("")) {
			goods.setUnits("个");
		}else {
			goods.setUnits(inputGoods.getUnits());
		}
		goods.setInventory(inputGoods.getInventory());
		goods.setOriginalPrice(inputGoods.getOriginalPrice());
		goods.setPrice(inputGoods.getPrice());
		goods.setTitle(inputGoods.getTitle());
		goods.setTypeid(inputGoods.getTypeid());
		goods.setUpdatetime(DateUtil.timeForUnix10());
		goods.setTitlepic(inputGoods.getTitlepic());
		goods.setIntro(inputGoods.getIntro());
		goods.setSpecification(inputGoods.getSpecification());
		//保存实体
		sqlService.save(goods);

		//保存商品详情
		String detail = request.getParameter("detail");
		//根据商品id查找详情
		GoodsData data = sqlService.findAloneByProperty(GoodsData.class, "id", goods.getId());
		//如果为空就new一个
		if(data == null) {
			data = new GoodsData();
			data.setId(goods.getId());
		}
		data.setDetail(detail);
		sqlService.save(data);

		//保存完成后，删除缓存，已达到更新缓存目的
		deleteGoodsCache(goods.getId());

		//日志记录
		ActionLogUtil.insertUpdateDatabase(request, goods.getId(),"Id为" + goods.getId() + "的商品添加或修改，内容:" + goods.toString());

		return success();
	}


	/**
	 * 保存完成后，删除缓存，已达到更新缓存目的
	 * @param goodsid 要删除的缓存的商品id
	 */
	private void deleteGoodsCache(int goodsid){
		sqlCacheService.deleteCacheById(Goods.class, goodsid);
		sqlCacheService.deleteCacheById(GoodsData.class, goodsid);
	}
	
	/**
	 * 删除商品
	 * 
	 * @param goodsid 删除商品id,对应goods.id
	 * @param token 当前操作用户的登录标识 <required>
     *           <p>可通过 <a href="shop.store.api.login.login.json.html">/shop/store/api/login/login.json</a> 取得 </p>
     * @return 若请求成功，则删除商品
     * @author 关光礼
	 */
	@ResponseBody
	@RequestMapping(value="/delete.json",method = {RequestMethod.POST})
	public BaseVO delete(HttpServletRequest request,
			@RequestParam(value = "goodsid",defaultValue = "0", required = false) int goodsid) {
		if(goodsid < 1) {
			return error("请传入id参数");
		}
		
		Goods goods = sqlService.findById(Goods.class, goodsid);
		if(goods == null) {
			return error("根据ID,没查到该实体");
		}
		if(goods.getStoreid() - getStoreId() != 0){
			return error("商品不属于你");
		}
		goods.setIsdelete(Goods.ISDELETE_DELETE);
		sqlService.save(goods);
		
		//操作完成后，删除缓存，已达到更新缓存目的
		deleteGoodsCache(goods.getId());
				
		//日志记录
		ActionLogUtil.insertUpdateDatabase(request, "删除ID是" + goodsid + "的商品", "删除内容:" + goods.toString());
		return success();
	}

	/**
	 * 修改商品的上下架
	 * <p>例如当前商品已下架，再调用此接口即可将其变为上架</p>
	 * @param token 当前操作用户的登录标识 <required>
     *           <p>可通过 <a href="shop.store.api.login.login.json.html">/shop/store/api/login/login.json</a> 取得 </p>
	 * @param id 商品id
	 * @return 若请求成功，则可以设置商品的上下架
	 * @author 关光礼
	 */
	@ResponseBody
	@RequestMapping(value="/updatePutaway.json",method = {RequestMethod.POST})
	public BaseVO updatePutaway(HttpServletRequest request,
										 @RequestParam(value = "id",defaultValue = "0", required = false) int id) {
		if(id < 1) {
			return error("请传入id参数");
		}

		Goods goods = sqlService.findById(Goods.class, id);
		if(goods == null) {
			return error("根据ID,没查到该实体");
		}
		if(goods.getStoreid() - getStoreId() != 0){
			return error("商品不属于你");
		}
		//判断并修改状态
		if(goods.getPutaway() == Goods.PUTAWAY_NOT_SELL) {
			goods.setPutaway(Goods.PUTAWAY_SELL);
		}else {
			goods.setPutaway(Goods.PUTAWAY_NOT_SELL);
		}
		sqlService.save(goods);

		//操作完成后，删除缓存，已达到更新缓存目的
		deleteGoodsCache(goods.getId());

		//日志记录
		ActionLogUtil.insertUpdateDatabase(request, "ID是" + id + "的商品的状态修改", "修改后状态:" + goods.getPutaway());
		return success();
	}

	/**
	 * 获取商品的图片
	 * @param token 当前操作用户的登录标识 <required>
     *           <p>可通过 <a href="shop.store.api.login.login.json.html">/shop/store/api/login/login.json</a> 取得 </p>
	 * @param id 商品id
	 * @return 若请求成功，则可以展示商品的图片
	 * @author 刘鹏
	 */
	@ResponseBody
	@RequestMapping(value = "imgList.json" ,method = {RequestMethod.POST})
	public GoodsImageListVO imgList(HttpServletRequest request,
						  @RequestParam(value = "id", required = false, defaultValue = "0") int id) {
		GoodsImageListVO vo = new GoodsImageListVO();

		if(id < 1) {
			vo.setBaseVO(BaseVO.FAILURE,"请传入ID信息" );
		}

		//创建Sql
		Sql sql = new Sql(request);
		//配置查询那个表
		sql.setSearchTable("shop_goods_image");
		//查询条件
		sql.appendWhere("goodsid = " + id);
		//配置按某个字端搜索内容
		//sql.setSearchColumn(new String[] {"title","typeid"});
		// 查询数据表的记录总条数
		int count = sqlService.count("shop_goods_image", sql.getWhere());

		// 配置每页显示15条
		Page page = new Page(count, 15, request);
		// 查询出总页数
		sql.setSelectFromAndPage("SELECT * FROM shop_goods_image ", page);
		//选择排序方式 当用户没有选择排序方式时，系统默认降序排序
		sql.setDefaultOrderBy("rank ASC");

		// 按照上方条件查询出该实体总数 用集合来装
		List<GoodsImage> list = sqlService.findBySql(sql,GoodsImage.class);

		vo.setList(list);
		vo.setPage(page);
		//日志记录
		ActionLogUtil.insert(request, getUserId(), "查看商品id为" + id + "图片列表");

		vo.setId(id);
		return vo;
	}

	/**
	 * 删除商品图片
	 * @param id 图片id
	 * @param token 当前操作用户的登录标识 <required>
     *           <p>可通过 <a href="shop.store.api.login.login.json.html">/shop/store/api/login/login.json</a> 取得 </p>
	 * @return 若请求成功，则可以删除图片
	 * @author 关光礼
	 */
	@ResponseBody
	@RequestMapping(value = "deleteImg.json" ,method = {RequestMethod.POST})
	public BaseVO deleteImg(HttpServletRequest request,
									 @RequestParam(value = "id", required = false, defaultValue = "0") int id) {
		if(id < 1) {
			return error("请传入图片ID信息" );
		}
		GoodsImage img = sqlService.findById(GoodsImage.class, id);
		if(img == null) {
			return error("根据ID,没查到该实体");
		}
		//查出商品信息，从而判断这个图片是不是这个人的
		Goods goods = sqlService.findById(Goods.class, img.getGoodsid());
		if(goods == null) {
			return error("该图所属商品不存在");
		}
		if(goods.getStoreid() - getStoreId() != 0){
			return error("该图不属于你，无法操作");
		}

		sqlService.delete(img);
		//日志记录
		ActionLogUtil.insertUpdateDatabase(request, id, "Id为" + id + "的商品列表图片删除", "删除内容" + img.toString());
		return success();
	}

	/**
	 * 获取商品图片信息
	 * @param token 当前操作用户的登录标识 <required>
     *           <p>可通过 <a href="shop.store.api.login.login.json.html">/shop/store/api/login/login.json</a> 取得 </p>
	 * @param id 图片id
	 * @param goodId 商品id
	 * @return 若请求成功，则展示商品图片的信息
	 * @author 刘鹏
	 */
	@ResponseBody
	@RequestMapping(value = "getGoodsImage.json" ,method = {RequestMethod.POST})
	public GoodsImageVO getGoodsImage(HttpServletRequest request,
									  @RequestParam(value = "id", required = false, defaultValue = "0") int id,
									  @RequestParam(value = "goodId", required = false, defaultValue = "0") int goodId) {
		GoodsImageVO vo = new GoodsImageVO();
		if(goodId < 1) {
			vo.setBaseVO(BaseVO.FAILURE, "请传入商品Id信息");
		}
		if(id > 0) {
			GoodsImage img = sqlService.findById(GoodsImage.class, id);
			vo.setGoodsImage(img);
		}else {
			vo.setResult(BaseVO.FAILURE);
		}
		ActionLogUtil.insert(request, getUserId(), "查看商品图片ID为" + (id == 0 ? "":id)+ "的详情，跳转到编辑页面");

		vo.setId(goodId);
		return vo;
	}

	/**
	 * 添加、修改商品轮播图
	 * @param token 当前操作用户的登录标识 <required>
     *           <p>可通过 <a href="shop.store.api.login.login.json.html">/shop/store/api/login/login.json</a> 取得 </p>
	 * @param id 图片id
	 * @param goodsid 商品id 必传入
	 * @param rank 图片的排序，数字越小越靠前
	 * @param imageUrl 图片的url绝对路径
	 * @return 若请求成功，则可以修改添加商品轮播图
	 * @author 刘鹏
	 */
	@ResponseBody
	@RequestMapping(value="/goodsImageSave.json",method = {RequestMethod.POST})
	public com.xnx3.j2ee.vo.BaseVO goodsImageSave(HttpServletRequest request,
												  @RequestParam(value = "id",required = false,defaultValue = "0") Integer id,
												  @RequestParam(value = "goodsid",defaultValue = "0") Integer goodsid,
												  @RequestParam(value = "rank",required = false,defaultValue = "0") Integer rank,
												  @RequestParam(value = "imageUrl",required = false,defaultValue = "") String imageUrl) {
		if(goodsid == null) {
			return error("请传入商品ID信息");
        }else if(rank == null ){
			return error("图片排序不能为空");
		}else if(imageUrl == ""){
            return error("列表图片不能为空");
        }
		//创建一个实体
		GoodsImage img;
		if(id == null || id - 0 == 0) {
			//添加
			img = new GoodsImage();
			img.setGoodsid(goodsid);
		}else {
			//修改
			String sql = "SELECT * FROM shop_goods_image WHERE id = " + id + " AND goodsid= " + goodsid;
			img = sqlService.findAloneBySqlQuery(sql, GoodsImage.class);
			if(img == null) {
				return error("根据ID,没查到该商品分类");
			}
			//查出商品信息，从而判断修改的这个图片是不是这个人的
			Goods goods = sqlService.findById(Goods.class, goodsid);
			if(goods == null) {
				return error("该图所属商品不存在");
			}
			if(goods.getStoreid() - getStoreId() != 0){
				return error("该图不属于你，无法操作");
			}
		}
		//给实体赋值
		img.setRank(rank);
		img.setImageUrl(imageUrl);
		//保存实体
		sqlService.save(img);

		//日志记录
		ActionLogUtil.insertUpdateDatabase(request, img.getId(),"Id为" + img.getId() + "的商品的图片添加或修改，内容:" + img.toString());

		return success();
	}
	/**
	 * 更改商品排序
	 * @param token 当前操作用户的登录标识 <required>
     *           <p>可通过 <a href="shop.store.api.login.login.json.html">/shop/store/api/login/login.json</a> 取得 </p>
	 * @param id 栏目id
	 * @param rank 排序编号。数字越小越靠前
	 * @return 若请求成功，则可以对商品进行排序
	 * @author 管雷鸣
	 */
	@RequestMapping(value="updateRank.json", method = RequestMethod.POST)
	@ResponseBody
	public BaseVO updateRank(HttpServletRequest request,
									  @RequestParam(value = "id", required = false , defaultValue="0") int id,
									  @RequestParam(value = "rank", required = false , defaultValue="0") int rank){
		Goods goods = new Goods();
		if(id < 1){
			return error("请传入要操作的栏目编号");
		}
		goods = sqlService.findById(Goods.class, id);
		if(goods == null){
			return error("要操作的栏目不存在");
		}

		goods.setRank(rank);
		sqlService.save(goods);

		//操作完成后，删除缓存，已达到更新缓存目的
		deleteGoodsCache(goods.getId());

		//记录日志
		ActionLogUtil.insertUpdateDatabase(request, goods.getId(), "更改栏目排序", rank+"");

		return success();
	}
}
