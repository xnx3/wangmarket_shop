package com.xnx3.wangmarket.shop_storeadmin.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xnx3.StringUtil;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.service.UserService;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.entity.Store;
import com.xnx3.wangmarket.shop_storeadmin.util.SessionUtil;

/**
 * 登录、注册
 * @author 管雷鸣
 */
@Controller(value="ShopAdminLoginController")
@RequestMapping("/shop/storeadmin/login/")
public class LoginController extends BaseController {
	@Resource
	private UserService userService;
	@Resource
	private SqlService sqlService;

	
	/**
	 * 登陆页面
	 */
	@RequestMapping("login${url.suffix}")
	public String login(HttpServletRequest request,Model model){
		if(getStore() != null){
			ActionLogUtil.insert(request, "进入登录页面", "已经登录成功，无需再登录，进行跳转");
			return redirect("/shop/storeadmin/index/index.do");
		}
		
		ActionLogUtil.insert(request, "进入登录页面");
		return "/shop/storeadmin/login/login";
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
	@RequestMapping(value="loginSubmit${url.suffix}", method = RequestMethod.POST)
	@ResponseBody
	public BaseVO loginSubmit(HttpServletRequest request,Model model){
		BaseVO vo = new BaseVO();
		
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
				//判断当前用户是否是商家，看用户是否对应 Store
//				Store store = sqlService.findAloneByProperty(Store.class, "userid", getUserId());
				Store store = sqlService.findAloneBySqlQuery("SELECT * FROM shop_store WHERE userid = "+getUserId(), Store.class);
				System.out.println(store);
				if(store == null){
					SessionUtil.logout();	//退出登录
					return error("您不是商铺管理人员，无法登陆");
				}
				//登录成功,BaseVO.info字段将赋予成功后跳转的地址，所以这里要再进行判断
				vo.setInfo("/shop/storeadmin/index/index.do");
				
			}else{
				ActionLogUtil.insert(request, "用户名密码模式登录失败",baseVO.getInfo());
			}
			
			return vo;
		}
	}
	
}
