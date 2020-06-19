package com.xnx3.wangmarket.shop.api.controller;

import java.awt.Font;
import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xnx3.Lang;
import com.xnx3.StringUtil;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.service.SqlCacheService;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.service.UserService;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.j2ee.vo.LoginVO;
import com.xnx3.media.CaptchaUtil;
import com.xnx3.wangmarket.shop.core.entity.Store;
import com.xnx3.wangmarket.shop.core.entity.StoreUser;
import com.xnx3.wangmarket.shop.core.pluginManage.interfaces.manage.OrderCreatePluginManage;
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
	 * 验证码图片显示，直接访问此地址可查看图片
	 */
	@RequestMapping("/captcha.jpg")
	public void captcha(HttpServletRequest request,HttpServletResponse response) throws IOException{
		ActionLogUtil.insert(request, "获取验证码显示");
		
		CaptchaUtil captchaUtil = new CaptchaUtil();
	    captchaUtil.setCodeCount(5);                   //验证码的数量，若不增加图宽度的话，只能是1～5个之间
	    captchaUtil.setFont(new Font("Fixedsys", Font.BOLD, 21));    //验证码字符串的字体
	    captchaUtil.setHeight(18);  //验证码图片的高度
	    captchaUtil.setWidth(110);      //验证码图片的宽度
//	    captchaUtil.setCode(new String[]{"我","是","验","证","码"});   //如果对于数字＋英文不满意，可以自定义验证码的文字！
	    com.xnx3.j2ee.util.CaptchaUtil.showImage(captchaUtil, request, response);
	}
	

	/**
	 * 登陆请求验证
	 * @param request {@link HttpServletRequest} 
	 * 		<br/>登陆时form表单需提交三个参数：username(用户名/邮箱)、password(密码)、code（图片验证码的字符）
	 * @return vo.result:
	 * 			<ul>
	 * 				<li>0:失败</li>
	 * 				<li>1:成功</li>
	 * 			</ul>
	 */
	@RequestMapping(value="login${api.suffix}", method = RequestMethod.POST)
	@ResponseBody
	public LoginVO login(HttpServletRequest request,Model model,
			@RequestParam(value = "storeid", required = false, defaultValue="0") int storeid){
		LoginVO vo = new LoginVO();
		
		//验证码校验
		BaseVO capVO = com.xnx3.j2ee.util.CaptchaUtil.compare(request.getParameter("code"), request);
		if(capVO.getResult() == BaseVO.FAILURE){
			ActionLogUtil.insert(request, "用户名密码模式登录失败", "验证码出错，提交的验证码："+StringUtil.filterXss(request.getParameter("code")));
			vo.setBaseVO(capVO);
			return vo;
		}else{
			//验证码校验通过
			
			BaseVO baseVO =  userService.loginByUsernameAndPassword(request);
			vo.setBaseVO(baseVO);
			if(baseVO.getResult() == BaseVO.SUCCESS){
				User user = getUser();
				ActionLogUtil.insert(request, "用户名密码模式登录成功");
				
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
	}
	

	/**
	 * 注册的请求验证
	 * @param username 要注册用户的用户名（必填）
	 * @param password 要注册当用户的密码（必填）
	 * @param code 图片验证码（必填）
	 * @param storeid 此用户是通过哪个店铺注册的（必填）
	 * @param referrerid 推荐人id，这里传入的是推荐人的 user.id 这个在创建用户信息时，会跟storeid一块计入用户的 StoreUser.referrerid。 可不填
	 * @return vo result:
	 * 			<ul>
	 * 				<li>0:失败</li>
	 * 				<li>1:成功</li>
	 * 			</ul>
	 */
	@RequestMapping(value="reg${api.suffix}", method = RequestMethod.POST)
	@ResponseBody
	public LoginVO reg(HttpServletRequest request,Model model,
			@RequestParam(value = "username", required = false, defaultValue="") String username,
			@RequestParam(value = "password", required = false, defaultValue="") String password,
			@RequestParam(value = "code", required = false, defaultValue="") String code,
			@RequestParam(value = "storeid", required = false, defaultValue="0") int storeid,
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
				ActionLogUtil.insert(request,userid, "注册成功","通过用户名密码");
				
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
			}else{
				ActionLogUtil.insert(request, "用户名密码模式注册失败",baseVO.getInfo());
				vo.setBaseVO(BaseVO.FAILURE, baseVO.getInfo());
				return vo;
			}
			
			return vo;
		}
	}
	
	/**
	 * 获取token，也就是获取 sessionid
	 * @return info便是sessionid
	 */
	@RequestMapping(value="getToken${api.suffix}", method = RequestMethod.POST)
	@ResponseBody
	public BaseVO getToken(HttpServletRequest request){
		HttpSession session = request.getSession();
		String token = session.getId();
		ActionLogUtil.insert(request, "获取token", token);
		return success(token);
	}
	
	/**
	 * 退出登录
	 */
	@RequestMapping(value="logout${api.suffix}", method = RequestMethod.POST)
	@ResponseBody
	public BaseVO logout(HttpServletRequest request){
		User user = getUser();
		ActionLogUtil.insert(request, "退出登录", user!=null? user.toString():"");
		SessionUtil.logout();
		return success();
	}
}
