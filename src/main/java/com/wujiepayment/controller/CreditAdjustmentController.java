package com.wujiepayment.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.wujiepayment.been.CreditAdjustmentGroup;
import com.wujiepayment.been.CreditAdjustmentInf;
import com.wujiepayment.been.SysUserInf;
import com.wujiepayment.service.CreditAdjustmentGroupService;
import com.wujiepayment.service.CreditAdjustmentService;
import com.wujiepayment.util.DateUtil;

/** 
 * @author 王凯: 
 * @version 创建时间：2015年6月18日 上午11:25:51 
 * 类说明 调额助手管理类
 */
@Controller
public class CreditAdjustmentController {
	private static Logger log = Logger.getLogger(CreditAdjustmentController.class);
	
	@Autowired
	private CreditAdjustmentGroupService creditAdjustmentGroupService;
	@Autowired
	private CreditAdjustmentService creditAdjustmentService;
	
	@RequestMapping("/creditAdjustmentInfGroupIndex")
    public String creditAdjustmentInfGroupIndex(HttpServletRequest req){
        log.info(" creditAdjustmentInfController creditAdjustmentInfGroupIndex");
        return "creditAdjustmentInf/creditAdjustmentInfGroupQuery";
	}
	@RequestMapping("/creditAdjustmentInfIndex")
    public String creditAdjustmentInfIndex(HttpServletRequest req){
        log.info(" creditAdjustmentInfController creditAdjustmentInfIndex");
        return "creditAdjustmentInf/creditAdjustmentInfQuery";
	}
	
	/**
	 * 获取调额助手组列表
	 * @param req
	 * @return
	 */
	@RequestMapping("/creditAdjustmentInfGroupQuery")
    @ResponseBody
    public Map<String, Object> creditAdjustmentInfGroupQuery( HttpServletRequest req){
		log.info(" CreditAdjustmentController creditAdjustmentInfGroupQuery:");
		Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("RSPCOD", "00000");
    	resultMap.put("RSPMSG", "查询成功！");
		resultMap.put("creditAdjustmentGroup", creditAdjustmentGroupService.getList(new CreditAdjustmentGroup()));
		return resultMap;
	}
	/**
	 * 下拉框获取调额助手组列表
	 * @param req
	 * @return
	 */
	@RequestMapping("/creditAdjustmentInfGroupQuery2")
    @ResponseBody
    public List<CreditAdjustmentGroup> creditAdjustmentInfGroupQuery2( HttpServletRequest req){
		log.info(" creditAdjustmentInfController creditAdjustmentInfGroupQuery2:");
		return creditAdjustmentGroupService.getList(new CreditAdjustmentGroup());
	}
	
	
	/**
	 * 获取调额助手列表
	 * @param req
	 * @return
	 */
	@RequestMapping("/creditAdjustmentInfQuery")
    @ResponseBody
    public Map<String, Object> creditAdjustmentInfQuery(CreditAdjustmentInf creditAdjustmentInf, HttpServletRequest req){
		log.info(" CreditAdjustmentController creditAdjustmentInfQuery:groupId="+creditAdjustmentInf.getGroupId());
		Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("RSPCOD", "00000");
    	resultMap.put("RSPMSG", "查询成功！");
		resultMap.put("creditAdjustmentInf", creditAdjustmentService.getCreditAdjustmentInfList(creditAdjustmentInf));
		return resultMap;
	}
	

	/**
	 * 调额助手组添加
	 * @param req
	 * @return
	 */
	@RequestMapping("/creditAdjustmentInfGroupAdd")
    public String creditAdjustmentInfGroupAdd(String nameAdd,@RequestParam MultipartFile imageUrlAdd, HttpServletRequest req){
		log.info(" creditAdjustmentInfController creditAdjustmentInfGroupAdd: name="+nameAdd);
        SysUserInf sessionUserInf = (SysUserInf) req.getSession().getAttribute("sysUserInf");
        if(sessionUserInf == null || sessionUserInf.getId() == null ){
            return "login";
        }
		if(imageUrlAdd.isEmpty()){  
			Map<String, Object> resultMap = new HashMap<String, Object>();
            System.out.println("文件未上传");  
            resultMap.put("RSPCOD", "00001");
        	resultMap.put("RSPMSG", "文件未上传！");
            return "creditAdjustmentInf/creditAdjustmentInfGroupQuery";
        }
		String realPath = req.getSession().getServletContext().getRealPath("/images/creditAdjustmentInf/"); 
		String fileNameOld = imageUrlAdd.getOriginalFilename();
		String fileType = fileNameOld.substring(fileNameOld.lastIndexOf("."));
		String fileName = DateUtil.getFormatDateStr("yyyyMMddHHmmss", new Date()) + fileType;
		try {
			FileUtils.copyInputStreamToFile(imageUrlAdd.getInputStream(), new File(realPath, fileName));
		} catch (IOException e) {
			log.error("调额助手组添加    文件上传错误：");
			log.error(e);
			e.printStackTrace();
		}
		CreditAdjustmentGroup creditAdjustmentGroup = new CreditAdjustmentGroup();
		creditAdjustmentGroup.setName(nameAdd);
		String url = req.getRequestURL().toString();
		String imageUrl = url.substring(0,url.lastIndexOf("/"))+"/images/creditAdjustmentInf/"+fileName;
		creditAdjustmentGroup.setImageUrl(imageUrl);
        String nowStr = DateUtil.getFormatDateStr("", new Date());
        creditAdjustmentGroup.setCreateTime(nowStr);
        creditAdjustmentGroup.setCreateUserId(sessionUserInf.getId());
        creditAdjustmentGroup.setUpdateTime(nowStr);
        creditAdjustmentGroup.setUpdateUserId(sessionUserInf.getId());
		creditAdjustmentGroupService.addEntity(creditAdjustmentGroup);
		return "creditAdjustmentInf/creditAdjustmentInfGroupQuery";
	}
	/**
	 * 调额助手信息添加
	 * @param req
	 * @return
	 */
	@RequestMapping("/creditAdjustmentInfAdd")
	@ResponseBody
    public Map<String, Object> creditAdjustmentInfAdd(CreditAdjustmentInf creditAdjustmentInf, HttpServletRequest req){
        log.info(" creditAdjustmentInfController creditAdjustmentInfAdd: groupId="+creditAdjustmentInf.getGroupId());
        SysUserInf sessionUserInf = (SysUserInf) req.getSession().getAttribute("sysUserInf");
        creditAdjustmentInf.setCreateTime(DateUtil.getFormatDateStr("", new Date()));
        creditAdjustmentInf.setCreateUserId(sessionUserInf.getId());
        creditAdjustmentInf.setUpdateTime(creditAdjustmentInf.getCreateTime());
        creditAdjustmentInf.setUpdateUserId(sessionUserInf.getId());
        return creditAdjustmentService.addEntity(creditAdjustmentInf);
	}
	
	@RequestMapping("/creditAdjustmentInfGroupDel")
	@ResponseBody
    public Map<String, Object> creditAdjustmentInfGroupDel(CreditAdjustmentGroup creditAdjustmentGroup, HttpServletRequest req){
        log.info(" creditAdjustmentInfController creditAdjustmentInfGroupDel: id="+creditAdjustmentGroup.getId());
        return creditAdjustmentGroupService.delEntity(creditAdjustmentGroup);
	}
	@RequestMapping("/creditAdjustmentInfDel")
	@ResponseBody
    public Map<String, Object> creditAdjustmentInfDel(CreditAdjustmentInf creditAdjustmentInf, HttpServletRequest req){
        log.info(" creditAdjustmentInfController creditAdjustmentInfDel: id="+creditAdjustmentInf.getId());
        return creditAdjustmentService.delEntity(creditAdjustmentInf);
	}
	@RequestMapping("/getCreditAdjustmentInfJsp")
	public ModelAndView getcreditAdjustmentInfJsp(CreditAdjustmentInf creditAdjustmentInf, HttpServletRequest req){
        log.info(" creditAdjustmentInfController getcreditAdjustmentInfJsp: Id="+creditAdjustmentInf.getId());
        creditAdjustmentInf = creditAdjustmentService.getEntity(creditAdjustmentInf);
        log.info("titleStr="+creditAdjustmentInf.getName()+";contentStr="+creditAdjustmentInf.getContentStr());
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("titleStr", creditAdjustmentInf.getName());
        map.put("contentStr", creditAdjustmentInf.getContentStr());
        ModelAndView mv = new ModelAndView("100",map);
		return mv;
	}

}
