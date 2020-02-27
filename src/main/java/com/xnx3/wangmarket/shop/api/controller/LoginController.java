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
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.service.UserService;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.j2ee.vo.LoginVO;
import com.xnx3.media.CaptchaUtil;
import com.xnx3.wangmarket.shop.core.entity.Store;
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
				ActionLogUtil.insert(request, "用户名密码模式登录成功");
				
				//登录成功,BaseVO.info字段将赋予成功后跳转的地址，所以这里要再进行判断
				vo.setInfo("admin/index/index.do");
				
				//将sessionid加入vo返回
				HttpSession session = request.getSession();
				vo.setToken(session.getId());
				
				//查询出此用户所在的店铺，加入缓存
				Store store = sqlService.findById(Store.class, storeid);
				if(store == null){
					vo.setBaseVO(BaseVO.FAILURE, "store 不存在");
					SessionUtil.logout();
					return vo;
				}
				SessionUtil.setStore(store);
				
				//加入user信息
				vo.setUser(getUser());
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
			@RequestParam(value = "storeid", required = false, defaultValue="0") int storeid){
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
			BaseVO baseVO = userService.createUser(user, request);
			
			if(baseVO.getResult() == BaseVO.SUCCESS){
				int userid = Lang.stringToInt(baseVO.getInfo(), 0);
				ActionLogUtil.insert(request,userid, "注册成功","通过用户名密码");
				
				//将当前用户变为已登陆状态
				userService.loginForUserId(request, userid);
				
				//将sessionid加入vo返回
				HttpSession session = request.getSession();
				vo.setToken(session.getId());
				
				//查询出此用户所在的店铺，加入缓存
				Store store = sqlService.findById(Store.class, storeid);
				if(store == null){
					vo.setBaseVO(BaseVO.FAILURE, "store 不存在");
					SessionUtil.logout();
					return vo;
				}
				SessionUtil.setStore(store);
				
				//加入user信息
				vo.setUser(getUser());
			}else{
				ActionLogUtil.insert(request, "用户名密码模式注册失败",baseVO.getInfo());
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
