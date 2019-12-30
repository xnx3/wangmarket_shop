package com.xnx3.wangmarket.weixin.controller;


import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xnx3.Lang;
import com.xnx3.j2ee.controller.BaseController;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.service.UserService;
import com.xnx3.j2ee.util.ConsoleUtil;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.j2ee.vo.LoginVO;
import com.xnx3.j2ee.vo.UserVO;
import com.xnx3.wangmarket.weixin.entity.WeiXinUser;
import com.xnx3.wangmarket.weixin.util.SessionUtil;
import com.xnx3.wangmarket.weixin.util.WeiXinAppletUtil;
import com.xnx3.weixin.vo.Jscode2sessionResultVO;

/**
 * 
 * @author 管雷鸣
 */
@Controller(value="WeiXinLoginController")
@RequestMapping("/")
public class LoginController extends BaseController {

	@Resource
	private SqlService sqlService;
	@Resource
	private UserService userService;
	
	
	/**
	 * 登陆   wx.login
	 * 1. 微信获取随机code，传入此处； 
	 * 2. 服务器通过code拉取用户 unionid；  
	 * 3. 利用 unionid 来判断用户是否存在及设置用户自动登陆状态
	 * @param request
	 * @param model
	 * @return {@link LoginVO} 登陆结果。根据getResult() 进行判断
	 * 		<ul>
	 * 			<li> {@link LoginVO}.getResult() == {@link LoginVO}.SUCCESS : 自动登陆成功。 同时，有一种情况，用户第一次登陆，也就是刚注册时，getInfo() 会返回字符串 "reg" ,小程序端需要根据此判断，若用户是第一次注册，那么需要将用户微信的 userInfo 信息传给服务器,以充实 User 数据表  </li>
	 * 			<li>{@link LoginVO}.getResult() == {@link LoginVO}.FAILURE : 自动登陆失败 ，可用 getInfo() 获取失败原因</li>
	 * 		</ul>
	 */
	@RequestMapping(value="/jscode2session${url.suffix}")
	@ResponseBody
	public LoginVO jscode2session(HttpServletRequest request, Model model,
			@RequestParam(value = "code", required = false, defaultValue="") String code){
		LoginVO vo = new LoginVO();
		vo.setToken(request.getSession().getId());
		
		Jscode2sessionResultVO jvo = WeiXinAppletUtil.getWeixinAppletUtil().jscode2session(code);
		if(jvo.getResult() - Jscode2sessionResultVO.SUCCESS == 0){
			//登陆成功
			
			List<WeiXinUser> weiXinUserList = sqlService.findByProperty(WeiXinUser.class, "unionid", jvo.getUnionid());
			User user = null;
			if(weiXinUserList.size() == 0){
				//此用户还未注册，进行注册用户
				user = new User();
				user.setUsername(Lang.uuid());
				user.setPassword(Lang.uuid());
				BaseVO regVO = userService.reg(user, request);
				if(regVO.getResult() - BaseVO.SUCCESS == 0){
					//自动注册成功，保存 WeixinUser
					WeiXinUser weixinUser = new WeiXinUser();
					weixinUser.setUserid(user.getId());
					weixinUser.setOpenid(jvo.getOpenid());
					weixinUser.setUnionid(jvo.getUnionid());
					sqlService.save(weixinUser);
					//缓存
					SessionUtil.setWeiXinUser(weixinUser);
					
					ConsoleUtil.info("reg --- > "+user.toString());
					vo.setUser(getUser());
					vo.setInfo("reg");
					
					return vo;
				}else{
					//自动注册失败
					vo.setBaseVO(regVO);
					return vo;
				}
			}else{
				//用户已经注册过了
				WeiXinUser weixinUser = weiXinUserList.get(0);
				//设置当前用户为登陆的状态
				BaseVO loginVO = userService.loginForUserId(request, weixinUser.getUserid());
				if(loginVO.getResult() - BaseVO.FAILURE == 0){
					vo.setBaseVO(UserVO.FAILURE, loginVO.getInfo());
					return vo;
				}else{
					//缓存微信相关信息
					SessionUtil.setWeiXinUser(weixinUser);
					
					vo.setUser(getUser());
					return vo;
				}
			}
		}else{
			// jscode2session 获取失败
			vo.setBaseVO(BaseVO.FAILURE, jvo.getInfo());
			return vo;
		}
	}
	
	
}
