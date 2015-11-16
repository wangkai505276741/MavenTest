package com.wujiepayment.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wujiepayment.been.AppUserInf;
import com.wujiepayment.service.AppUserService;

@Controller
public class AppLoginController {
	private static Logger log = Logger.getLogger(AppLoginController.class);
	
	@Autowired
	private AppUserService appUserService;
	
	@RequestMapping("/appRegister_bak")
    @ResponseBody
    public Map<String, Object> appRegister_bak(@RequestBody AppUserInf u, HttpServletRequest req){
        log.info(" AppLoginController appRegister:" + u.getLoginName());
        System.out.println(" AppLoginController appRegister:" + u.getLoginName());
        
        return appUserService.addEntity(u);
	}
	@RequestMapping("/appRegister")
    @ResponseBody
    public Map<String, Object> appRegister( AppUserInf user,HttpServletRequest req){
        log.info(" AppLoginController appRegister:loginName:"+user.getLoginName()
        		+";loginPassword:"+user.getLoginPassword()+";activationCode:"+user.getActivationCode()
        		+";realName:"+user.getRealName()+";idCard:"+user.getIdCard()+";email:"+user.getEmail()
        		+";uniqueCode:"+user.getUniqueCode());
        /*
        String loginName = req.getParameter("loginName");
        String loginPassword = req.getParameter("loginPassword");
        String activationCode = req.getParameter("activationCode");
        String realName = req.getParameter("realName");
        String idCard    = req.getParameter("idCard");
        String email = req.getParameter("email");
        System.out.println(" AppLoginController appRegister:loginName:"+loginName
        		+";loginPassword:"+loginPassword+";activationCode:"+activationCode
        		+";realName:"+realName+";idCard:"+idCard+";email:"+email);
        AppUserInf user = new AppUserInf();
        user.setLoginName(loginName);
        user.setLoginPassword(loginPassword);
        user.setActivationCode(activationCode);
        user.setRealName(realName);
        user.setIdCard(idCard);
        user.setEmail(email);*/
        return appUserService.addEntity(user);
	}
	
	
	@RequestMapping("/appLogin")
    @ResponseBody
    public Map<String, Object> appLogin(AppUserInf u, HttpServletRequest req){
        log.info(" AppLoginController applogin");
        System.out.println(" AppLoginController applogin: loginName = " + u.getLoginName()
        		+ " ; password = " + u.getLoginPassword());
        Map<String,Object> resultMap = new HashMap<String, Object>();
        u = appUserService.getEntity(u);
        if(u==null){
            resultMap.put("RSPCOD", "00001");
        	resultMap.put("RSPMSG", "用户名或密码错误！");
        	return resultMap;
        }
        resultMap.put("RSPCOD", "00000");
    	resultMap.put("RSPMSG", "登录成功！");
    	resultMap.put("appUserInf", u);
    	return resultMap;
	}
	
	@RequestMapping("/getActivationCode")
    @ResponseBody
    public Map<String, Object> getActivationCode(HttpServletRequest req){
        log.info(" AppLoginController getActivationCode");
        return appUserService.getActivationCode();
	}

}
