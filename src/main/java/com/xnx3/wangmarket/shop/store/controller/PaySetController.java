package com.xnx3.wangmarket.shop.store.controller;

import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xnx3.Lang;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.CacheUtil;
import com.xnx3.j2ee.util.AttachmentMode.LocalServerMode;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.Global;
import com.xnx3.wangmarket.shop.core.entity.PaySet;
import com.xnx3.wangmarket.shop.core.entity.Store;
import com.xnx3.wangmarket.shop.core.service.PayService;
import com.xnx3.wangmarket.shop.store.util.FileUtil;


/**
 * 商户自己支付方面的设置
 * @author 管雷鸣
 */
@Controller(value="ShopStorePaySetController")
@RequestMapping("/shop/store/paySet/")
public class PaySetController extends BaseController {
	@Resource
	private SqlService sqlService;
	@Resource
	private PayService payService;
	
	/**
	 * 设置的首页
	 */
	@RequestMapping("/index${url.suffix}")
	public String index(HttpServletRequest request,Model model) {
		Store store = getStore();	//当前登录的商家的信息
		PaySet paySet = sqlService.findById(PaySet.class, store.getId());
		if(paySet == null){
			//为空，那么可能是这个商家第一次使用，还没有支付设置，这里创建一个出来
			paySet = new PaySet();
			paySet.setId(store.getId());
			//保存，创建这条记录关联
			sqlService.save(paySet);
		}
		// 将信息保存到model中 
		model.addAttribute("paySet", paySet);
		//日志记录
		ActionLogUtil.insert(request, getUserId(), "进入商家支付设置首页");
		return "/shop/store/paySet/index";
	}
	
	/**
	 * 设置 payset 表的字段
	 * @param name 当前设置的是哪种支付或者哪个参数，可传入参数为 payset 表的字段，如： alipay private weixinPay alipayAppId alipayAppPrivateKey weixinMchId weixinMchKey weixinAppletAppid
	 * @param value 要设置的值。如 name 是 alipay private weixinPay，那么这个value便是0、1
	 */
	@ResponseBody
	@RequestMapping(value="update${url.suffix}",method = {RequestMethod.POST})
	public BaseVO update(HttpServletRequest request,
			@RequestParam(value = "name", required = false, defaultValue="") String name,
			@RequestParam(value = "value", required = false, defaultValue="0") String value) {
		Store store = getStore();	//当前登录的商家的信息
		PaySet paySet = sqlService.findById(PaySet.class, store.getId());
		if(paySet == null){
			//为空，那么可能是这个商家第一次使用，还没有支付设置，这里创建一个出来
			paySet = new PaySet();
			paySet.setId(store.getId());
		}
		
		if(name.equalsIgnoreCase("alipay")){
			paySet.setUseAlipay((short) (value.equals("1")? 1:0));
		}else if (name.equalsIgnoreCase("private")) {
			paySet.setUsePrivatePay((short) (value.equals("1")? 1:0));
		}else if (name.equalsIgnoreCase("weixinPay")) {
			paySet.setUseWeixinPay((short) (value.equals("1")? 1:0));
		}else if (name.equalsIgnoreCase("alipayAppId")) {
			paySet.setAlipayAppId(value);
		}else if (name.equalsIgnoreCase("alipayAppPrivateKey")) {
			paySet.setAlipayAppPrivateKey(value);
		}else if (name.equalsIgnoreCase("weixinMchId")) {
			paySet.setWeixinMchId(value);
		}else if (name.equalsIgnoreCase("weixinMchKey")) {
			paySet.setWeixinMchKey(value);
		}else if (name.equalsIgnoreCase("weixinAppletAppid")) {
			paySet.setWeixinAppletAppid(value);
		}else if (name.equalsIgnoreCase("weixinOfficialAccountsAppid")) {
			paySet.setWeixinOfficialAccountsAppid(value);
			//清理weixinService的缓存
			CacheUtil.delete(Global.CACHE_KEY_STORE_WEIXIN_UTIL.replace("{storeid}", store.getId()+""));
		}else if (name.equalsIgnoreCase("weixinOfficialAccountsAppSecret")) {
			paySet.setWeixinOfficialAccountsAppSecret(value);
			//清理weixinService的缓存
			CacheUtil.delete(Global.CACHE_KEY_STORE_WEIXIN_UTIL.replace("{storeid}", store.getId()+""));
		}else if (name.equalsIgnoreCase("weixinOfficialAccountsToken")) {
			paySet.setWeixinOfficialAccountsToken(value);
			//清理weixinService的缓存
			CacheUtil.delete(Global.CACHE_KEY_STORE_WEIXIN_UTIL.replace("{storeid}", store.getId()+""));
		}else{
			return error("name 错误");
		}
		sqlService.save(paySet);
		//更新持久缓存
		payService.setPaySet(paySet);
		
		//日志记录
		ActionLogUtil.insert(request, "商家支付设置，设置"+name, paySet.toString());
		return success();
	}
	

	/**
	 * 此接口专门用来上传 crt 证书
	 * @param name 当前是要上传哪种证书，可传入参数： alipayCertPublicKeyRSA2 alipayRootCert alipayAppCertPublicKey
	 */
	@ResponseBody
	@RequestMapping(value="/uploadCrt${url.suffix}",method = {RequestMethod.POST})
	public BaseVO uploadCrt(HttpServletRequest request,
			@RequestParam(value = "name", required = false, defaultValue="") String name,
			MultipartFile file) {
		Store store = getStore();	//当前登录的商家的信息
		PaySet paySet = sqlService.findById(PaySet.class, store.getId());
		if(paySet == null){
			//为空，那么可能是这个商家第一次使用，还没有支付设置，这里创建一个出来
			paySet = new PaySet();
			paySet.setId(store.getId());
		}
		
		if(!Lang.findFileSuffix(file.getOriginalFilename()).equalsIgnoreCase("crt")){
			//不是crt后缀，提示错误
			return error("请上传后缀为crt的证书文件");
		}
		
		if(name.equalsIgnoreCase("alipayCertPublicKeyRSA2")){
			paySet.setAlipayCertPublicKeyRSA2("alipayCertPublicKeyRSA2.crt");
		}else if (name.equalsIgnoreCase("alipayRootCert")) {
			paySet.setAlipayRootCert("alipayRootCert.crt");
		}else if (name.equalsIgnoreCase("alipayAppCertPublicKey")) {
			paySet.setAlipayAppCertPublicKey("alipayAppCertPublicKey.cet");
		}else{
			return error("name 错误");
		}
		
		//将文件上传到服务器本身
		try {
			BaseVO vo = FileUtil.put(Global.CERTIFICATE_PATH.replace("{storeid}", store.getId()+"")+name+".crt", file.getInputStream());
			if(vo.getResult() - BaseVO.FAILURE == 0){
				return vo;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return error(e.getMessage());
		}
		
		//保存数据
		sqlService.save(paySet);
		//更新持久缓存
		payService.setPaySet(paySet);
		
		//日志记录
		ActionLogUtil.insert(request, "商家支付设置，上传"+name+".crt");
		return success();
	}
}
