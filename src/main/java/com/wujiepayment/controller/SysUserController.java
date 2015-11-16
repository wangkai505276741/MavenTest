package com.wujiepayment.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wujiepayment.been.SysUserInf;
import com.wujiepayment.service.SysUserService;
import com.wujiepayment.util.DateUtil;
import com.wujiepayment.util.MD5Util;

/** 
 * @author 王凯: 
 * @version 创建时间：2015年6月20日 下午2:29:13 
 * 类说明 系统用户管理
 */
@Controller
public class SysUserController {

	private static Logger log = Logger.getLogger(SysUserController.class);
	@Autowired
	private SysUserService sysUserService;

	@RequestMapping("/sysUserInfIndex")
    public String sysUserInfIndex(HttpServletRequest req){
        log.info(" SysLoginController sysUserInfIndex");
        return "sysUserInf/sysUserQuery";
	}
	@RequestMapping("/sysUserInfQuery")
	@ResponseBody
    public List<SysUserInf> sysUserInfQuery(SysUserInf sysUserInf, HttpServletRequest req){
        log.info(" SysUserController sysUserInfQuery: userName="+sysUserInf.getUserName());
        System.out.println(" SysUserController sysUserInfQuery: userName="+sysUserInf.getUserName());
        return sysUserService.getList(sysUserInf);
	}
	
	@RequestMapping("/sysUserInfAdd")
	@ResponseBody
    public Map<String, Object> sysUserInfAdd(SysUserInf sysUserInf, HttpServletRequest req){
        log.info(" SysUserController sysUserInfAdd: id="+sysUserInf.getId());
        System.out.println(" SysUserController sysUserInfAdd: id="+sysUserInf.getId());
        SysUserInf sessionUserInf = (SysUserInf) req.getSession().getAttribute("sysUserInf");
        sysUserInf.setCreateTime(DateUtil.getFormatDateStr("", new Date()));
        sysUserInf.setCreateUserId(sessionUserInf.getId());
        sysUserInf.setUpdateTime(sysUserInf.getUpdateTime());
        sysUserInf.setUpdateUserId(sessionUserInf.getId());
        sysUserInf.setPassword(MD5Util.MD5(sysUserInf.getPassword()));
        return sysUserService.addEntity(sysUserInf);
	}
	
	@RequestMapping("/sysUserInfDel")
	@ResponseBody
    public Map<String, Object> sysUserInfDel(SysUserInf sysUserInf, HttpServletRequest req){
        log.info(" SysUserController sysUserInfDel: id="+sysUserInf.getId());
        System.out.println(" SysUserController sysUserInfDel: id="+sysUserInf.getId());
        return sysUserService.delEntity(sysUserInf);
	}
	
	@RequestMapping("/sysUserInfEdit")
	@ResponseBody
    public Map<String, Object> sysUserInfEdit(SysUserInf sysUserInf, HttpServletRequest req){
        log.info(" SysUserController sysUserInfEdit: id="+sysUserInf.getId());
        System.out.println(" SysUserController sysUserInfEdit: id="+sysUserInf.getId());
        SysUserInf sessionUserInf = (SysUserInf) req.getSession().getAttribute("sysUserInf");
        sysUserInf.setUpdateTime(DateUtil.getFormatDateStr("", new Date()));
        sysUserInf.setUpdateUserId(sessionUserInf.getId());
        sysUserInf.setPassword(MD5Util.MD5(sysUserInf.getPassword()));
        return sysUserService.editEntity(sysUserInf);
	}
	

	@RequestMapping("/sysUserPasswordEdit")
	@ResponseBody
    public Map<String, Object> sysUserPasswordEdit(String oldPassword, String newPassword,HttpServletRequest req){
        log.info(" SysUserController sysUserPasswordEdit: oldPassword="+oldPassword + ";newPassword="+newPassword);
        System.out.println(" SysUserController sysUserInfEdit: oldPassword="+oldPassword + ";newPassword="+newPassword);
        SysUserInf sessionUserInf = (SysUserInf) req.getSession().getAttribute("sysUserInf");
        if(!MD5Util.MD5(oldPassword).equals(sessionUserInf.getPassword())){
        	System.out.println("sessionPassword="+sessionUserInf.getPassword()+";oldPassword="+MD5Util.MD5(oldPassword));
        	Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("RSPCOD", "00001");
			resultMap.put("RSPMSG", "原密码错误！");
			return resultMap;
        }
        SysUserInf sysUserInf = new SysUserInf();
        sysUserInf.setId(sessionUserInf.getId());
        sysUserInf.setUpdateUserId(sessionUserInf.getId());
        sysUserInf.setPassword(MD5Util.MD5(newPassword));
        return sysUserService.editEntity(sysUserInf);
	}

}
