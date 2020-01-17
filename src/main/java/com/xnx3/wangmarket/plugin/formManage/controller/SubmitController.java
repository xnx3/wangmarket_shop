package com.xnx3.wangmarket.plugin.formManage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xnx3.DateUtil;
import com.xnx3.StringUtil;
import com.xnx3.j2ee.pluginManage.controller.BasePluginController;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.IpUtil;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.SafetyUtil;
import com.xnx3.j2ee.util.Sql;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.admin.entity.InputModel;
import com.xnx3.wangmarket.admin.entity.Site;
import com.xnx3.wangmarket.admin.util.ActionLogUtil;
import com.xnx3.wangmarket.admin.util.SessionUtil;
import com.xnx3.wangmarket.plugin.formManage.bean.Frequency;
import com.xnx3.wangmarket.plugin.formManage.entity.Form;
import com.xnx3.wangmarket.plugin.formManage.entity.FormData;

/**
 * Form插件,5.0以前的版本
 * @author 管雷鸣
 */
@Controller(value="FormSubmitPluginController")
@RequestMapping("/")
public class SubmitController extends BasePluginController {

	@Resource
	private SqlService sqlService;

	/**
	 * 反馈频率控制，避免被人利用而已提交
	 */
	public static Map<String, Frequency> frequencyMap = new HashMap<String, Frequency>();
	static{
		//开启一个线程，每天都清理一次 frequencyMap的数据
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(1*24*60*60);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				frequencyMap.clear();
			}
		}).start();
	}
	//两次提交表单的时间间隔。控制在一小时。1 * 60 * 60;
	public final static int FeedbackTimeInterval = 5;
	public final static int textMaxLength = 10000;	//表单提交的formData.text信息最大字数允许在1万以内
	
	
	/**
	 * 提交反馈信息，只限 post 提交
	 * @param siteid 站点的id，这条反馈属于哪个站点， Site.id
	 * @param 反馈信息的title字段，将会在网站管理后台-功能插件-表单反馈列表中显示的
	 * @deprecated 请使用 /plugin/form/formAdd.do 此保留，只是为了兼容5.0以前的版本
	 */
	@RequestMapping(value="formAdd${url.suffix}", method = RequestMethod.POST)
	@ResponseBody
	public BaseVO formAdd(HttpServletRequest request, Model model,
			@RequestParam(value = "siteid", required = false , defaultValue="0") int siteid,
			@RequestParam(value = "title", required = false , defaultValue="") String title){
		return submit(request, siteid, title);
	}
	
	/**
	 * 提交反馈信息，只限 post 提交
	 * 网市场5.0后的版本都用这个
	 * @param siteid 站点的id，这条反馈属于哪个站点， Site.id
	 * @param 反馈信息的title字段，将会在网站管理后台-功能插件-表单反馈列表中显示的
	 * @deprecated 请使用 /plugin/form/formAdd.do
	 */
	@RequestMapping(value="/plugin/form/add${url.suffix}", method = RequestMethod.POST)
	@ResponseBody
	public BaseVO add(HttpServletRequest request, Model model,
			@RequestParam(value = "siteid", required = false , defaultValue="0") int siteid,
			@RequestParam(value = "title", required = false , defaultValue="") String title){
		return submit(request, siteid, title);
	}
	
	/**
	 * 提交反馈信息保存
	 * @param siteid 站点的id，这条反馈属于哪个站点， Site.id
	 * @param 反馈信息的title字段，将会在网站管理后台-功能插件-表单反馈列表中显示的
	 * @return {@link BaseVO}
	 */
	private BaseVO submit(HttpServletRequest request,int siteid,String title){
		String ip = IpUtil.getIpAddress(request);
		Frequency frequency = frequencyMap.get(ip);
		int currentTime = DateUtil.timeForUnix10();	//当前10位时间戳
		//今天尚未提交过，那么创建一个记录
		if(frequency == null){
			frequency = new Frequency();
			frequency.setIp(ip);
		}
		
		//判断当前是否允许提交反馈信息，如果不允许，需要记录，并返回不允许的提示。
		if(frequency.getForbidtime() > currentTime){
			//间隔时间太短，不允许提交反馈信息
			frequency.setErrorNumber(frequency.getErrorNumber()+1);
			frequencyMap.put(ip, frequency);	//临时存储，这个存储时间是一天,每天清除一次
			return error("距离上次提交时间太短，等会再试试吧");
		}
		
		/** 下面就是允许提交的逻辑处理了 **/
		frequency.setLasttime(currentTime);	//设置当前为最后一次提交的时间
		frequency.setForbidtime(currentTime + FeedbackTimeInterval);	//设置下次允许提交反馈的时间节点，这个时间节点之前是不允许在此提交的
		frequencyMap.put(ip, frequency);	//临时存储，这个存储时间是一天,每天清除一次
		
		
		title = StringUtil.filterXss(title);
		if(siteid <= 0){
			return error("请传入您的站点id（siteid），不然，怎么知道此反馈表单是属于哪个网站的呢？");
		}
		
		Form form = new Form();
		form.setAddtime(DateUtil.timeForUnix10());
		form.setSiteid(siteid);
		form.setState(Form.STATE_UNREAD);
		form.setTitle(title);
		sqlService.save(form);
		if(form.getId() != null && form.getId() > 0){
			//成功，进而存储具体内容。存储内容时，首先要从提交的数据中，便利出所有表单数据.这里是原始提交的结果，需要进行xss过滤
			Map<String, String[]> params = new HashMap<String, String[]>();
			params.putAll(request.getParameterMap());
			//删除掉siteid、title的参数
			params.remove("siteid");
			if(params.get("title") != null){
				params.remove("title");
			}
			
			JSONArray jsonArray = new JSONArray();	//text文本框所存储的内容
			for (Map.Entry<String, String[]> entry : params.entrySet()) { 
				JSONObject json = new JSONObject();
				JSONArray valueJsonArray = new JSONArray();
				
				for (int i = 0; i < entry.getValue().length; i++) {
					valueJsonArray.add(StringUtil.filterXss(entry.getValue()[i]));
				}
				json.put(StringUtil.filterXss(entry.getKey()), valueJsonArray);
				jsonArray.add(json);
			}
			String text = jsonArray.toString();
			if(text.length() > textMaxLength){
				return error("信息太长，非法提交！");
			}
			
			FormData formData = new FormData();
			formData.setId(form.getId());
			formData.setText(text);
			sqlService.save(formData);
			
			//记录日志
			ActionLogUtil.insert(request, form.getId(), "plugin formManage 提交表单反馈", SafetyUtil.xssFilter(form.getTitle()));
			
			return success();
		}else{
			return error("保存失败");
		}
	}
	
}