package com.wujiepayment.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wujiepayment.been.SysUserInf;
import com.wujiepayment.service.SysUserService;
import com.wujiepayment.util.DateUtil;
import com.wujiepayment.util.MD5Util;

@Controller
public class SysLoginController {
	private static Logger log = Logger.getLogger(SysLoginController.class);
	@Autowired
	private SysUserService sysUserService;

	private ModelAndView mav = new ModelAndView("/login");
	
	@RequestMapping("/sysLoginCheck")
    @ResponseBody
    public Map<String, Object> sysLogin(SysUserInf u, HttpServletRequest req){
        log.info(" SysLoginController sysLogin");
        System.out.println(" SysLoginController sysLogin: userName = " + u.getUserName()
        		+ " ; password = " + u.getPassword());
        Map<String,Object> resultMap = new HashMap<String, Object>();
        String codes = req.getParameter("codes");
		String code = (String) req.getSession().getAttribute("code");

        if(u.getUserName() == null || "".equals(u.getUserName())){
            resultMap.put("RSPCOD", "00002");
        	resultMap.put("RSPMSG", "请输入用户名！");
        	return resultMap;
		}
		if(u.getPassword() == null || "".equals(u.getPassword())){
            resultMap.put("RSPCOD", "00003");
        	resultMap.put("RSPMSG", "请输入密码！");
        	return resultMap;
		}
		if(codes == null || "".equals(codes)){
            resultMap.put("RSPCOD", "00004");
        	resultMap.put("RSPMSG", "请输入验证码！");
        	return resultMap;
		}
		if(code == null || !code.equals(codes)){
            resultMap.put("RSPCOD", "00001");
        	resultMap.put("RSPMSG", "验证码错误，请重新输入！");
        	return resultMap;
		}
		u.setPassword(MD5Util.MD5(u.getPassword()));
		System.out.println(u.getPassword());
        u = sysUserService.getEntity(u);
        if(u==null){
            resultMap.put("RSPCOD", "00001");
        	resultMap.put("RSPMSG", "用户名或密码错误，请重新输入！");
        	return resultMap;
        }
        resultMap.put("RSPCOD", "00000");
    	resultMap.put("RSPMSG", "登录成功！");
    	req.getSession().setAttribute("sysUserInf", u);
    	resultMap.put("sysUserInf", u);
    	return resultMap;
	}
	
	
	@RequestMapping("sysLogin")
	public ModelAndView userLoginHandler(HttpServletRequest req){
		System.out.println("sysLogin: ");
		SysUserInf sysUserInf = (SysUserInf) req.getSession().getAttribute("sysUserInf");
		if(sysUserInf!=null&&!"".equals(sysUserInf.getUserName())){
			//获取IP
			if (req.getHeader("x-forwarded-for") == null) {  
				sysUserInf.setLastLoginIP(req.getRemoteAddr());  
			}  else {
				sysUserInf.setLastLoginIP(req.getHeader("x-forwarded-for"));
			}
			//登录时间
			sysUserInf.setLastLoginTime(
					DateUtil.getFormatDateStr("", new Date()));
			sysUserService.editEntity(sysUserInf);
			ModelAndView mav = new ModelAndView("/index");
			return mav;
		}
		return mav;
	}
	@RequestMapping("sysLogout")
	public ModelAndView userLogoutHandler(HttpServletRequest req){
		System.out.println("sysLogout ");
		req.getSession().removeAttribute("sysUserInf");
		return mav;
	}

}
