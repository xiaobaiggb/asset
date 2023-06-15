package com.javaee.lqsx.asset.controller;


import com.javaee.lqsx.asset.po.Admin;
import com.javaee.lqsx.asset.po.Manage;
import com.javaee.lqsx.asset.po.User;
import com.javaee.lqsx.asset.service.AdminService;
import com.javaee.lqsx.asset.service.ManageService;
import com.javaee.lqsx.asset.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 系统操作控制器
 */

//登录
@Controller
public class LoginController {

	// 依赖注入
	@Autowired
	private ManageService manageService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private UserService userService;


	/**
	 * 去登录
	 */
	@RequestMapping(value = "/gologin")
	public String gologin() {
		return "login";
	}

	/**
	 * 登录
	 * 将提交数据(username,password)写入Admin对象
	 */
	@RequestMapping(value = "/login")
	public String login(Manage manage, Model model, HttpSession session, HttpServletRequest request) {
		if(manage.getNo()==null || manage.getNo().length()<=0 ){
			model.addAttribute("msg", "请输入登录名！");
			return "login";
		}
		if(manage.getPassword()==null || manage.getPassword().length()<1){
			model.addAttribute("msg", "请输入密码！");
			return "login";
		}
		if(manage.getType()==null || manage.getType().length()<1){
			model.addAttribute("msg", "请选择人员类型！");
			return "login";
		}
		Map mp = new HashMap();
		mp.put("no",manage.getNo());
		mp.put("password",manage.getPassword());
		if(manage.getType().equals("01")){
			List<Admin> ad = adminService.queryFilter(mp);
			if(ad!=null && ad.size()==1){
				session.setAttribute("ad", ad.get(0));
				session.setAttribute("type", "01");
				return "homepage1";
			}else{
				model.addAttribute("msg", "请确定账户信息是否正确！");
				return "login";
			}
		}else if(manage.getType().equals("02")){
			mp.put("type","01");
			List<Manage> ad = manageService.queryFilter(mp);
			if(ad!=null && ad.size()==1){
				session.setAttribute("ad", ad.get(0));
				session.setAttribute("type", "02");
				return "homepage2";
			}else{
				model.addAttribute("msg", "请确定账户信息是否正确！");
				return "login";
			}
		}else if(manage.getType().equals("03")){
			List<User> ad = userService.queryFilter(mp);
			if(ad!=null && ad.size()==1){
				session.setAttribute("ad", ad.get(0));
				session.setAttribute("type", "03");
				return "homepage3";
			}else{
				model.addAttribute("msg", "请确定账户信息是否正确！");
				return "login";
			}
		}else{
			mp.put("type","02");
			List<Manage> ad = manageService.queryFilter(mp);
			if(ad!=null && ad.size()==1){
				session.setAttribute("ad", ad.get(0));
				session.setAttribute("type", "04");
				return "homepage4";
			}else{
				model.addAttribute("msg", "请确定账户信息是否正确！");
				return "login";
			}
		}
	}

	/**
	 * 退出登录
	 */
	@RequestMapping(value = "/loginOut")
	public String loginOut(HttpSession session) {
		session.invalidate();
		return "login";
	}


	/**
	 * 查询个人信息
	 */
	@RequestMapping(value = "/info")
	public String info(HttpServletRequest request) {
		return "queryInfo";
	}



	/**
	 * 进入修改
	 */
	@RequestMapping(value = "/updateInfo")
	public String updateInfo(HttpServletRequest request) {
		return "updateInfo";
	}



	/**
	 * 修改信息
	 */
	@RequestMapping( value = "/updateInfoAdmin", method = RequestMethod.POST)
	@ResponseBody
	public String updateInfoAdmin(Manage manage,Model model,HttpServletRequest request,HttpSession session1) {
		HttpSession session = request.getSession();
		if(session.getAttribute("ad") == null){
			session.setAttribute("msg", "对不起，请登录！");
			return "202";
		}
		if(manage.getNo().length()<1){
			return "203";
		}
		if(manage.getPassword().length()<1){
			return "204";
		}
		String type = (String) session.getAttribute("type");
		if(type.equals("01")){
			Admin admin1 = (Admin) session.getAttribute("ad");
			if(!admin1.getPassword().equals(manage.getPassword())){
				return "201";
			}
			if(!"".equals(manage.getPasswords())){
				manage.setPassword(manage.getPasswords());
			}
			Admin admin = new Admin();
			admin.setId(manage.getId());
			admin.setNo(manage.getNo());
			admin.setPassword(manage.getPassword());
			adminService.updateAdmin(admin);
		}else if(type.equals("02")){
			if(!isMobile(manage.getPhone())){
				return "205";
			}
			Manage manage1 = (Manage) session.getAttribute("ad");
			if(!manage1.getPassword().equals(manage.getPassword())){
				return "201";
			}
			if(!"".equals(manage.getPasswords())){
				manage.setPassword(manage.getPasswords());
			}
			manageService.updateManage(manage);
		}else{
			if(!isMobile(manage.getPhone())){
				return "205";
			}
			User user1 = (User) session.getAttribute("ad");
			if(!user1.getPassword().equals(manage.getPassword())){
				return "201";
			}
			User user = new User();
			BeanUtils.copyProperties(manage, user);
			if(!"".equals(manage.getPasswords())){
				user.setPassword(manage.getPasswords());
			}
			userService.updateUser(user);
		}
		return "200";
	}


	/**
	 * 前往注册
	 */
	@RequestMapping(value = "/register")
	public String register() {
		return "register";
	}


	/**
	 * 判断是否是手机号
	 * @param mobile
	 * @return
	 */
	public static boolean isMobile(String mobile) {
		String regex = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";
		Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(mobile);
		return m.matches();
	}


}
