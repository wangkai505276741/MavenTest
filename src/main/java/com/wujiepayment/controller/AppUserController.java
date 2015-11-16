package com.wujiepayment.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wujiepayment.been.AppUserInf;
import com.wujiepayment.service.AppUserService;

/** 
 * @author 王凯: 
 * @version 创建时间：2015年6月18日 下午5:12:43 
 * 类说明 终端用户管理
 */
@Controller
public class AppUserController {
	private static Logger log = Logger.getLogger(AppUserController.class);
	@Autowired
	private AppUserService appUserService;
	
	@RequestMapping("/appUserInfIndex")
    public String appUserInfIndex(HttpServletRequest req){
        log.info(" AppUserController appUserInfIndex");
        return "appUserInf/appUserQuery";
	}
	
	@RequestMapping("/appUserInfQuery")
	@ResponseBody
    public List<AppUserInf> appUserInfQuery(AppUserInf appUserInf, HttpServletRequest req){
        log.info(" AppUserController appUserInfQuery: loginName="+appUserInf.getLoginName()+";realName="+appUserInf.getRealName());
        System.out.println(" AppUserController appUserInfQuery: loginName="+appUserInf.getLoginName()+";realName="+appUserInf.getRealName());
        return appUserService.getList(appUserInf);
	}
	
	
	@RequestMapping("/appUserInfDel")
	@ResponseBody
    public Map<String, Object> appUserInfDel(AppUserInf appUserInf, HttpServletRequest req){
        log.info(" AppUserController appUserInfDel: id="+appUserInf.getId());
        System.out.println(" AppUserController appUserInfDel: id="+appUserInf.getId());
        return appUserService.delEntity(appUserInf);
	}
	
	@RequestMapping("/appUserInfEdit")
	@ResponseBody
    public Map<String, Object> appUserInfEdit(AppUserInf appUserInf, HttpServletRequest req){
        log.info(" AppUserController appUserInfEdit: id="+appUserInf.getId());
        System.out.println(" AppUserController appUserInfEdit: id="+appUserInf.getId());
        return appUserService.editEntity(appUserInf);
	}

}
