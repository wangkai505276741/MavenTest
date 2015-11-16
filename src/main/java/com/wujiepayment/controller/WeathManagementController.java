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

import com.wujiepayment.been.SysUserInf;
import com.wujiepayment.been.WeathManagementInf;
import com.wujiepayment.service.WeathManagementService;
import com.wujiepayment.util.DateUtil;

/** 
 * @author 王凯: 
 * @version 创建时间：2015年6月18日 上午10:02:15 
 * 类说明 投资理财管理类
 */
@Controller
public class WeathManagementController {
	private static Logger log = Logger.getLogger(WeathManagementController.class);
	@Autowired
	private WeathManagementService weathManagementService;
	
	@RequestMapping("/weathManagementIndex")
    public String weathManagementIndex(HttpServletRequest req){
        log.info(" weathManagementController weathManagementIndex");
        return "weathManagement/weathManagementQuery";
	}
	
	/**
	 * 获取投资理财列表
	 * @param req
	 * @return
	 */
	@RequestMapping("/weathManagementInfQuery")
    @ResponseBody
    public Map<String, Object> weathManagementInfQuery( HttpServletRequest req){
		log.info(" WeathManagementController weathManagementInfQuery");
		Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("RSPCOD", "00000");
    	resultMap.put("RSPMSG", "查询成功！");
		resultMap.put("weathManagementInf", weathManagementService.getList(new WeathManagementInf()));
		return resultMap;
	}
	
	/**
	 * 获取投资理财列表
	 * @param req
	 * @return
	 */
	@RequestMapping("/weathManagementInfQuery2")
    @ResponseBody
    public List<WeathManagementInf> weathManagementInfQuery2( HttpServletRequest req){
		log.info(" WeathManagementController weathManagementInfQuery2");
		return weathManagementService.getList(new WeathManagementInf());
	}
	
	/**
	 * 投资理财信息添加
	 * @param req
	 * @return
	 */
	@RequestMapping("/weathManagementAdd")
    public String weathManagementAdd(String nameAdd, @RequestParam MultipartFile imageUrlAdd,
    		String urlStrAdd, HttpServletRequest req){
		log.info(" weathManagementController weathManagementAdd: name="+nameAdd);
        SysUserInf sessionUserInf = (SysUserInf) req.getSession().getAttribute("sysUserInf");
        if(sessionUserInf == null || sessionUserInf.getId() == null ){
            return "login";
        }
		if(imageUrlAdd.isEmpty()){  
			Map<String, Object> resultMap = new HashMap<String, Object>();
            System.out.println("文件未上传");  
            resultMap.put("RSPCOD", "00001");
        	resultMap.put("RSPMSG", "文件未上传！");
            return "weathManagement/weathManagementQuery";
        }
		String realPath = req.getSession().getServletContext().getRealPath("/images/lastestInf/"); 
		String fileNameOld = imageUrlAdd.getOriginalFilename();
		String fileType = fileNameOld.substring(fileNameOld.lastIndexOf("."));
		String fileName = DateUtil.getFormatDateStr("yyyyMMddHHmmss", new Date()) + fileType;
		System.out.println("fileName="+fileName);
		try {
			FileUtils.copyInputStreamToFile(imageUrlAdd.getInputStream(), new File(realPath, fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("最新资讯添加    文件上传错误：");
			e.printStackTrace();
		}
		WeathManagementInf weathManagement  = new WeathManagementInf();
		weathManagement.setName(nameAdd);
		weathManagement.setUrlStr(urlStrAdd);
		String url = req.getRequestURL().toString();
		String imageUrl = url.substring(0,url.lastIndexOf("/"))+"/images/lastestInf/"+fileName;
		System.out.println("ImageUrl = "+imageUrl);
		weathManagement.setImageUrl(imageUrl);
        String nowStr = DateUtil.getFormatDateStr("", new Date());
        weathManagement.setCreateTime(nowStr);
        weathManagement.setCreateUserId(sessionUserInf.getId());
        weathManagement.setUpdateTime(nowStr);
        weathManagement.setUpdateUserId(sessionUserInf.getId());
        weathManagementService.addEntity(weathManagement);
		return "weathManagement/weathManagementQuery";
	}

	@RequestMapping("/weathManagementDel")
	@ResponseBody
    public Map<String, Object> weathManagementDel(WeathManagementInf weathManagement, HttpServletRequest req){
        log.info(" weathManagementController weathManagementDel: id="+weathManagement.getId());
        return weathManagementService.delEntity(weathManagement);
	}

}
