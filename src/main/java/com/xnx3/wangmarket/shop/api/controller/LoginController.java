package com.xnx3.wangmarket.shop.api.controller;

import java.awt.Font;
import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xnx3.Lang;
import com.xnx3.StringUtil;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.Global;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.service.SqlCacheService;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.service.UserService;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.j2ee.vo.LoginVO;
import com.xnx3.media.CaptchaUtil;
import com.xnx3.wangmarket.shop.core.entity.Store;
import com.xnx3.wangmarket.shop.core.entity.StoreUser;
import com.xnx3.wangmarket.shop.core.pluginManage.interfaces.manage.RegPluginManage;
import com.xnx3.wangmarket.shop.api.util.SessionUtil;

/**
 * 登录、注册
 * @author 管雷鸣
 */
@Controller(value="ShopLoginController")
@RequestMapping("/shop/api/login/")
public class LoginController extends BaseController {
	@Resource
	private UserService userService;
	@Resource
	private SqlService sqlService;
	@Resource
	private SqlCacheService sqlCacheService;
	

	/**
	 * 验证码图片
	 * <p>直接访问此地址可查看验证码图片</p>
	 * <p>此需要配合 <a href="shop.api.login.login.json.html">/shop/api/login/login.json</a> 一起使用 </p>
	 * @param token 当前操作用户的登录标识 <required>
	 * 				<p>可通过 <a href="shop.api.login.login.json.html">/shop/api/login/login.json</a> 取得 </p>
	 * @author 管雷鸣
	 * @return 图片，需要用 <textarea disabled="disabled" rows="1"><img src="..." /></textarea> 来显示
	 */
	@RequestMapping("/captcha.jpg")
	@ResponseBody
	public void captcha(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//日志记录
		ActionLogUtil.insert(request, "获取验证码显示");
		
		CaptchaUtil captchaUtil = new CaptchaUtil(); //captchaUtil.setCode(new String[]{"我","是","验","证","码"});如果对于数字＋英文不满意，可以自定义验证码的文字！
		captchaUtil.setCodeCount(5);                   //验证码的数量，若不增加图宽度的话，只能是1～5个之间
		captchaUtil.setFont(new Font("Fixedsys", Font.BOLD, 21));    //验证码字符串的字体
		captchaUtil.setHeight(18);  //验证码图片的高度
	 	captchaUtil.setWidth(110);      //验证码图片的宽度 
		com.xnx3.j2ee.util.CaptchaUtil.showImage(captchaUtil, request, response);
	}
	

	/**
	 * 登陆请求验证
	 * <p>
	 * 		<b>登录步骤描述</b>
	 * 		<ul>
	 * 			<li>首先调用 <a href="shop.api.login.getToken.json.html">/shop/api/login/getToken.json</a> 接口,获取到 token</li>
	 * 			<li>通过 <a href="shop.api.login.captcha.jpg.html">/shop/api/login/captcha.jpg?token=xxxxx</a> 获取验证码图片显示给用户</li>
	 * 			<li>通过此 login.json 接口进行登录</li>
	 * 		</ul>
	 * 		注意：这三步中，第一步获取到的token，跟第二三步中使用的token一定是一样的。
	 * </p>
	 * <p>
	 * 		<b>为了接口测试方便，加了一个专门用于接口调试的</b>
	 * 		<p>username:guanleiming</p>
	 * 		<p>password:123456</p>
	 * 		<p>使用这个固定的username、password，不需要输入验证码即可直接登录</p>
	 * <p>
	 * @param username 登录的用户名
	 * @param password 登录的密码
	 * @param code 图片验证码的字符 <required> <example=xxxxx>
	 * @param storeid 要登录的商家的编号，是登录商家的商城
	 * @param request {@link HttpServletRequest} 
	 * 		<br/>登陆时form表单需提交三个参数：username(用户名/邮箱)、password(密码)、code（图片验证码的字符）
	 * @author 管雷鸣
	 * @return vo.result:
	 * 			<ul>
	 * 				<li>0:失败</li>
	 * 				<li>1:成功</li>
	 * 			</ul>
	 */
	@RequestMapping(value="login.json", method = RequestMethod.POST)
	@ResponseBody
	public LoginVO login(HttpServletRequest request,Model model,
			@RequestParam(value = "storeid", required = true, defaultValue="1") int storeid,
			@RequestParam(required = true, defaultValue="guanleiming") String username,
			@RequestParam(required = true, defaultValue="123456") String password){
		LoginVO vo = new LoginVO();
		
		//判断是否是测试API的模式
		if(username.equalsIgnoreCase("guanleiming") && password.equalsIgnoreCase("123456")) {
			//不用输入验证码就可以登录的测试账号
			
			//查询当前数据库中是否有 guanleiming 这个用户了
			User user = sqlCacheService.findAloneByProperty(User.class, "username", "guanleiming");
			if(user == null) {
				//新创建这个测试账号
				
				user = new User();
				user.setUsername("guanleiming");
				user.setPassword("123456");
				BaseVO baseVO = userService.createUser(user, request);
				
				//注册成功后，将这个用户加入这个商家名下，是这个商家的客户
				StoreUser storeUser = new StoreUser();
				storeUser.setId(user.getId()+"_"+storeid);
				storeUser.setStoreid(storeid);
				storeUser.setUserid(user.getId());
				sqlService.save(storeUser);
				
			}else {
				String md5Password = new Md5Hash("123456", user.getSalt(),Global.USER_PASSWORD_SALT_NUMBER).toString();
				if(!user.getPassword().equals(md5Password)) {
					//密码不一致，之前有人恶意改过密码，那再改回来
					user.setPassword(md5Password);
					sqlService.save(user);
				}
			}
			
		}else{
			//其他的都要经过验证码验证
			
			//验证码校验
			BaseVO capVO = com.xnx3.j2ee.util.CaptchaUtil.compare(request.getParameter("code"), request);
			if(capVO.getResult() == BaseVO.FAILURE){
				ActionLogUtil.insert(request, "用户名密码模式登录失败", "验证码出错，提交的验证码："+StringUtil.filterXss(request.getParameter("code")));
				vo.setBaseVO(capVO);
				return vo;
			}
		}
		
		//验证码校验通过，进行登录操作
		BaseVO baseVO =  userService.loginByUsernameAndPassword(request);
		vo.setBaseVO(baseVO);
		if(baseVO.getResult() == BaseVO.SUCCESS){
			User user = getUser();
			//日志记录
			ActionLogUtil.insert(request, "用户名密码模式登录成功", user.toString());
			
			Store store = null;	//此用户所在的店铺
			//判断一下是否传入了storeid这个参数
			if(storeid > 0){
				//判断一下用户是否已经关联上这个商家了，如果没关联，还要将这个用户关联为这个商家的用户
				StoreUser storeUser = sqlCacheService.findBySql(StoreUser.class, "userid="+user.getId()+" AND storeid="+storeid);
				if(storeUser == null){
					storeUser = new StoreUser();
					storeUser.setId(user.getId()+"_"+storeid);
					storeUser.setStoreid(storeid);
					storeUser.setUserid(user.getId());
					sqlService.save(storeUser);
				}
				
				//查询出此用户所在的店铺，加入缓存
				store = sqlService.findById(Store.class, storeid);
				if(store == null){
					vo.setBaseVO(BaseVO.FAILURE, "store 不存在");
					SessionUtil.logout();
					return vo;
				}
				SessionUtil.setStore(store);
			}
			
			//登录成功,BaseVO.info字段将赋予成功后跳转的地址，所以这里要再进行判断
			vo.setInfo("admin/index/index.do");
			
			//将sessionid加入vo返回
			HttpSession session = request.getSession();
			vo.setToken(session.getId());
			
			//加入user信息
			vo.setUser(user);
		}else{
			ActionLogUtil.insert(request, "用户名密码模式登录失败",baseVO.getInfo());
		}
		
		return vo;
	}
	

	/**
	 * 注册的请求验证
	 * @param username 要注册用户的用户名（必填）
	 * @param password 要注册当用户的密码（必填）
	 * @param code 图片验证码（必填）
	 * @param storeid 此用户是通过哪个店铺注册的（必填）
	 * @param referrerid 推荐人id，这里传入的是推荐人的 user.id 这个在创建用户信息时，会跟storeid一块计入用户的 StoreUser.referrerid。 可不填
	 * @author 管雷鸣
	 * @return vo result:
	 * 			<ul>
	 * 				<li>0:失败</li>
	 * 				<li>1:成功</li>
	 * 			</ul>
	 */
	@RequestMapping(value="reg.json", method = RequestMethod.POST)
	@ResponseBody
	public LoginVO reg(HttpServletRequest request,Model model,
			@RequestParam(value = "username", required = true, defaultValue="") String username,
			@RequestParam(value = "password", required = true, defaultValue="") String password,
			@RequestParam(value = "code", required = true, defaultValue="") String code,
			@RequestParam(value = "storeid", required = true, defaultValue="0") int storeid,
			@RequestParam(value = "referrerid", required = false, defaultValue="0") int referrerid){
		LoginVO vo = new LoginVO();
		if(username.length() == 0){
			vo.setBaseVO(BaseVO.FAILURE, "请传入用户名");
			return vo;
		}
		if(password.length() == 0){
			vo.setBaseVO(BaseVO.FAILURE, "请传入密码");
			return vo;
		}
		if(code.length() == 0){
			vo.setBaseVO(BaseVO.FAILURE, "请传入验证码");
			return vo;
		}
		if(storeid < 1){
			vo.setBaseVO(BaseVO.FAILURE, "请传入店铺id");
			return vo;
		}
		//注册的用户所在的店铺
		Store store = sqlService.findById(Store.class, storeid);
		if(store == null){
			vo.setBaseVO(BaseVO.FAILURE, "你所在的商铺不存在");
			return vo;
		}
		
		//验证码校验
		BaseVO capVO = com.xnx3.j2ee.util.CaptchaUtil.compare(request.getParameter("code"), request);
		if(capVO.getResult() == BaseVO.FAILURE){
			//日志记录
			ActionLogUtil.insert(request, "用户名密码模式注册失败", "验证码出错，提交的验证码："+StringUtil.filterXss(request.getParameter("code")));
			vo.setBaseVO(capVO);
			return vo;
		}else{
			//验证码校验通过
			User user = new User();
			user.setUsername(StringUtil.filterXss(username));
			user.setPassword(password);
			user.setReferrerid(referrerid);
			BaseVO baseVO = userService.createUser(user, request);
			
			if(baseVO.getResult() == BaseVO.SUCCESS){
				int userid = Lang.stringToInt(baseVO.getInfo(), 0);
				
				//注册成功后，将这个用户加入这个商家名下，是这个商家的客户
				StoreUser storeUser = new StoreUser();
				storeUser.setId(user.getId()+"_"+storeid);
				storeUser.setStoreid(storeid);
				storeUser.setUserid(userid);
				//判断其是否有推荐人
				if(referrerid > 0){
					//从StoreUser表中，看是否有这个 id ,也就是这个推荐人是否真的存在
					StoreUser referrerStoreUser = sqlCacheService.findById(StoreUser.class, referrerid+"_"+storeid);
					if(referrerStoreUser != null){
						storeUser.setReferrerid(referrerStoreUser.getId());
					}
				}
				sqlService.save(storeUser);
				
				//将当前用户变为已登陆状态
				userService.loginForUserId(request, userid);
				
				/*** 注册成功后触发 ***/
				try {
					RegPluginManage.regFinish(user, store);
				} catch (Exception e) {
					e.printStackTrace();
				}
				/*********/
				
				//将sessionid加入vo返回
				HttpSession session = request.getSession();
				vo.setToken(session.getId());
				SessionUtil.setStore(store);
				
				//加入user信息
				vo.setUser(getUser());
				//日志记录
				ActionLogUtil.insertUpdateDatabase(request,userid, "通过用户名密码注册成功", getUser().toString());
			}else{
				//日志记录
				ActionLogUtil.insert(request, "用户名密码模式注册失败",baseVO.getInfo());
				vo.setBaseVO(BaseVO.FAILURE, baseVO.getInfo());
				return vo;
			}
			
			return vo;
		}
	}
	
	/**
	 * 获取token，也就是获取 sessionid
	 * @author 管雷鸣
	 * @return 如果获取成功，info便是返回token
	 */
	@RequestMapping(value="getToken.json", method = RequestMethod.POST)
	@ResponseBody
	public BaseVO getToken(HttpServletRequest request){
		HttpSession session = request.getSession();
		String token = session.getId();
		//日志记录
		ActionLogUtil.insert(request, "获取token", token);
		return success(token);
	}
	
	/**
	 * 退出登录
	 * @param token 当前操作用户的登录标识 <required>
	 * 				<p>可通过 <a href="shop.api.login.login.json.html">/shop/api/login/login.json</a> 取得 </p>
	 * @author 管雷鸣
	 * @return 操作结果
	 */
	@RequestMapping(value="logout.json", method = RequestMethod.POST)
	@ResponseBody
	public BaseVO logout(HttpServletRequest request){
		User user = getUser();
		//日志记录
		ActionLogUtil.insert(request, "退出登录", user!=null? user.toString():"");
		SessionUtil.logout();
		return success();
	}
}
