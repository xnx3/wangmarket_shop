package com.xnx3.wangmarket.plugin.firstOrderAward.controller.store;

import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.plugin.firstOrderAward.entity.Award;
import com.xnx3.wangmarket.plugin.firstOrderAward.vo.AwardVO;
import com.xnx3.wangmarket.shop.core.entity.Goods;
import com.xnx3.wangmarket.shop.core.entity.Store;
import com.xnx3.wangmarket.shop.core.pluginManage.controller.BasePluginController;
import com.xnx3.wangmarket.shop.store.util.SessionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 推广用户，用户下单消费后，推广人获赠某个商品
 * @author 刘鹏
 */
@Controller(value="FirstOrderAwardStoreIndexPluginApiController")
@RequestMapping("/plugin/api/firstOrderAward/store/")
public class IndexController extends BasePluginController {
	@Resource
	private SqlService sqlService;
	

	/**
	 * 设置哪个商品作为赠品，奖品
	 * @param code 64位登录码
	 * @param token 约定的token
	 * @author 刘鹏
	 * @return 若成功，info返回session id
	 */
	@ResponseBody
	@RequestMapping(value = "setAward${api.suffix}",method = {RequestMethod.POST})
	public AwardVO setAward(HttpServletRequest request, Model model,
							@RequestParam(value = "code", required = false, defaultValue="") String code,
							@RequestParam(value = "token", required = false, defaultValue="") String token){
		AwardVO vo = new AwardVO();

		if(!haveStoreAuth()){
			 vo.setBaseVO(BaseVO.FAILURE, "请先登录");
		}
		
		Store store = SessionUtil.getStore();
		Award award = sqlService.findById(Award.class, store.getId());
		if(award == null){
			award = new Award();
			award.setId(store.getId());
			award.setIsUse(Award.IS_USE_NO); //默认不使用
			sqlService.save(award);
		}
//		if(award != null && award.getIsUse() - Award.IS_USE_YES == 0){
//			//如果已经开启了，那么直接跳转到查看统计数据界面
//			model.addAttribute("url", "see.do");
//		}else{
//			//没有设置完，进入设置界面
//			model.addAttribute("url", "set.do");
//		}
//		
		ActionLogUtil.insertUpdateDatabase(request, "进入设置页面");
		vo.setAward(award);
		return vo;
	}
	

	/**
	 * 管理后台设置保存是否使用
	 * @param isUse 是否使用， 1使用， 0不使用
	 * @author 刘鹏
	 */
	@ResponseBody
	@RequestMapping(value = "updateIsUse${api.suffix}",method = {RequestMethod.POST})
	public BaseVO updateIsUse(HttpServletRequest request, Model model,
			@RequestParam(value = "isUse", required = false, defaultValue = "0") int isUse) {
		if(!haveStoreAuth()){
			return error("请先登录");
		}
		
		Store store = SessionUtil.getStore();
		Award award = sqlService.findById(Award.class, store.getId());
		if(award == null){
			award = new Award();
			award.setId(store.getId());
		}
		award.setIsUse(isUse == 1? Award.IS_USE_YES: Award.IS_USE_NO);//默认不使用
		sqlService.save(award);
		
		//日志
		ActionLogUtil.insertUpdateDatabase(request, "修改 isUse 为"+(award.getIsUse()- Award.IS_USE_YES == 0? "使用":"不使用"));
		//MQ通知改动,向 domain 项目发送mq更新消息
//		DomainMQ.send("cnzz", new PluginMQ(site).jsonAppend(JSONObject.fromObject(EntityUtil.entityToMap(cnzz))).toString());
		
		return success();
	}
	

	/**
	 * 商家管理后台设置奖品的 goodsid
	 * @param goodsid 奖品的goods.id
	 * @author 管雷鸣
	 */
	@ResponseBody
	@RequestMapping(value = "/updateGoodsid${api.suffix}",method = RequestMethod.POST)
	public BaseVO updateGoodsid(HttpServletRequest request, Model model,
			@RequestParam(value = "goodsid", required = false, defaultValue = "0") int goodsid) {
		if(!haveStoreAuth()){
			return error("请先登录");
		}
		
		Store store = SessionUtil.getStore();
		Goods goods = sqlService.findById(Goods.class, goodsid);
		if(goods == null){
			return error("商品不存在");
		}
		if(goods.getStoreid() - store.getId() != 0){
			return error("该商品不属于您，您无权使用");
		}
		
		Award award = sqlService.findById(Award.class, store.getId());
		if(award == null){
			award = new Award();
			award.setId(store.getId());
			award.setIsUse(Award.IS_USE_YES);//因为都要设置goodsid了，肯定是启用了
		}
		award.setGoodsid(goodsid);
		sqlService.save(award);
		
		//日志
		ActionLogUtil.insertUpdateDatabase(request, "修改推广送礼的奖品");
		//MQ通知改动,向 domain 项目发送mq更新消息
//		DomainMQ.send("cnzz", new PluginMQ(site).jsonAppend(JSONObject.fromObject(EntityUtil.entityToMap(cnzz))).toString());
		
		return success(goods.getTitle());
	}
	
}