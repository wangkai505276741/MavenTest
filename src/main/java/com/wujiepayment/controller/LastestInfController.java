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

import com.wujiepayment.been.LastestInf;
import com.wujiepayment.been.LastestInfGroup;
import com.wujiepayment.been.SysUserInf;
import com.wujiepayment.service.LastestInfGroupService;
import com.wujiepayment.service.LastestInfService;
import com.wujiepayment.util.DateUtil;

/**
 * 最新资讯管理类
 * @author wangkai
 *
 */
@Controller
public class LastestInfController {
	private static Logger log = Logger.getLogger(LastestInfController.class);
	@Autowired
	private LastestInfGroupService lastestInfGroupService;
	@Autowired
	private LastestInfService lastestInfService;
	
	@RequestMapping("/lastestInfGroupIndex")
    public String lastestInfGroupIndex(HttpServletRequest req){
        log.info(" LastestInfController lastestInfGroupIndex");
        return "lastestInf/lastestInfGroupQuery";
	}
	@RequestMapping("/lastestInfIndex")
    public String lastestInfIndex(HttpServletRequest req){
        log.info(" LastestInfController lastestInfIndex");
        return "lastestInf/lastestInfQuery";
	}
	
	/**
	 * 获取最新资讯组列表
	 * @param req
	 * @return
	 */
	@RequestMapping("/lastestInfGroupQuery")
    @ResponseBody
    public Map<String, Object> lastestInfGroupQuery( HttpServletRequest req){
		log.info(" LastestInfController lastestInfGroupQuery:");
		Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("RSPCOD", "00000");
    	resultMap.put("RSPMSG", "查询成功！");
		resultMap.put("lastestInfGroup", lastestInfGroupService.getList(new LastestInfGroup()));
		return resultMap;
	}
	
	/**
	 * 下拉框获取最新资讯组列表
	 * @param req
	 * @return
	 */
	@RequestMapping("/lastestInfGroupQuery2")
    @ResponseBody
    public List<LastestInfGroup> lastestInfGroupQuery2( HttpServletRequest req){
		log.info(" LastestInfController lastestInfGroupQuery2:");
		return lastestInfGroupService.getList(new LastestInfGroup());
	}
	
	/**
	 * 获取最新资讯列表
	 * @param req
	 * @return
	 */
	@RequestMapping("/lastestInfQuery")
    @ResponseBody
    public Map<String, Object> lastestInfQuery(LastestInf lastInf, HttpServletRequest req){
		log.info(" LastestInfController lastestInfQuery:groupId="+lastInf.getGroupId());
		Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("RSPCOD", "00000");
    	resultMap.put("RSPMSG", "查询成功！");
		resultMap.put("lastestInf", lastestInfService.getLastestInfList(lastInf));
		return resultMap;
	}
	
	/**
	 * 最新资讯组添加
	 * @param req
	 * @return
	 */
	@RequestMapping("/lastestInfGroupAdd")
    public String lastestInfGroupAdd(String nameAdd,@RequestParam MultipartFile imageUrlAdd, HttpServletRequest req){
		log.info(" LastestInfController lastestInfGroupAdd: name="+nameAdd);
        SysUserInf sessionUserInf = (SysUserInf) req.getSession().getAttribute("sysUserInf");
        if(sessionUserInf == null || sessionUserInf.getId() == null ){
            return "login";
        }
		if(imageUrlAdd.isEmpty()){  
			Map<String, Object> resultMap = new HashMap<String, Object>();
            System.out.println("文件未上传");  
            resultMap.put("RSPCOD", "00001");
        	resultMap.put("RSPMSG", "文件未上传！");
            return "lastestInf/lastestInfGroupQuery";
        }
		String realPath = req.getSession().getServletContext().getRealPath("/images/lastestInf/"); 
		String fileNameOld = imageUrlAdd.getOriginalFilename();
		String fileType = fileNameOld.substring(fileNameOld.lastIndexOf("."));
		String fileName = DateUtil.getFormatDateStr("yyyyMMddHHmmss", new Date()) + fileType;
		try {
			FileUtils.copyInputStreamToFile(imageUrlAdd.getInputStream(), new File(realPath, fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("最新资讯组添加    文件上传错误：");
			log.error(e);
			e.printStackTrace();
		}
		LastestInfGroup lastInfGroup = new LastestInfGroup();
		lastInfGroup.setName(nameAdd);
		String url = req.getRequestURL().toString();
		String imageUrl = url.substring(0,url.lastIndexOf("/"))+"/images/lastestInf/"+fileName;
		lastInfGroup.setImageUrl(imageUrl);
        String nowStr = DateUtil.getFormatDateStr("", new Date());
        lastInfGroup.setCreateTime(nowStr);
		lastInfGroup.setCreateUserId(sessionUserInf.getId());
		lastInfGroup.setUpdateTime(nowStr);
		lastInfGroup.setUpdateUserId(sessionUserInf.getId());
		lastestInfGroupService.addEntity(lastInfGroup);
		return "lastestInf/lastestInfGroupQuery";
	}
	/**
	 * 最新资讯信息添加
	 * @param req
	 * @return
	 */
	@RequestMapping("/lastestInfAdd")
	@ResponseBody
    public Map<String, Object> lastestInfAdd(LastestInf lastestInf, HttpServletRequest req){
        log.info(" LastestInfController lastestInfAdd: groupId="+lastestInf.getGroupId());
        SysUserInf sessionUserInf = (SysUserInf) req.getSession().getAttribute("sysUserInf");
        lastestInf.setCreateTime(DateUtil.getFormatDateStr("", new Date()));
        lastestInf.setCreateUserId(sessionUserInf.getId());
        lastestInf.setUpdateTime(lastestInf.getCreateTime());
        lastestInf.setUpdateUserId(sessionUserInf.getId());
        return lastestInfService.addEntity(lastestInf);
	}
	
	/**
	 * 最新资讯信息添加
	 * @param req
	 * @return
	 */
	@RequestMapping("/lastestInfAdd2")
    public String lastestInfAdd2(String nameAdd, Integer groupIdAdd,@RequestParam MultipartFile imageUrlAdd,
    		String urlStrAdd, String descStrAdd, String contentStrAdd, HttpServletRequest req){
		log.info(" LastestInfController lastestInfAdd2: name="+nameAdd);
        SysUserInf sessionUserInf = (SysUserInf) req.getSession().getAttribute("sysUserInf");
        if(sessionUserInf == null || sessionUserInf.getId() == null ){
            return "login";
        }
		if(imageUrlAdd.isEmpty()){  
			Map<String, Object> resultMap = new HashMap<String, Object>();
            System.out.println("文件未上传");  
            resultMap.put("RSPCOD", "00001");
        	resultMap.put("RSPMSG", "文件未上传！");
            return "lastestInf/lastestInfQuery";
        }
		String realPath = req.getSession().getServletContext().getRealPath("/images/lastestInf/"); 
		String fileNameOld = imageUrlAdd.getOriginalFilename();
		String fileType = fileNameOld.substring(fileNameOld.lastIndexOf("."));
		String fileName = DateUtil.getFormatDateStr("yyyyMMddHHmmss", new Date()) + fileType;
		try {
			FileUtils.copyInputStreamToFile(imageUrlAdd.getInputStream(), new File(realPath, fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("最新资讯添加    文件上传错误：");
			log.error(e);
			e.printStackTrace();
		}
		LastestInf lastInf  = new LastestInf();
		lastInf.setName(nameAdd);
		lastInf.setGroupId(groupIdAdd);
		lastInf.setUrlStr(urlStrAdd);
		lastInf.setDescStr(descStrAdd);
		String url = req.getRequestURL().toString();
		String imageUrl = url.substring(0,url.lastIndexOf("/"))+"/images/lastestInf/"+fileName;
		lastInf.setImageUrl(imageUrl);
        String nowStr = DateUtil.getFormatDateStr("", new Date());
        lastInf.setCreateTime(nowStr);
		lastInf.setCreateUserId(sessionUserInf.getId());
		lastInf.setUpdateTime(nowStr);
		lastInf.setUpdateUserId(sessionUserInf.getId());
		lastestInfService.addEntity(lastInf);
		return "lastestInf/lastestInfQuery";
	}
	
	@RequestMapping("/lastestInfGroupDel")
	@ResponseBody
    public Map<String, Object> lastestInfGroupDel(LastestInfGroup lastInfGroup, HttpServletRequest req){
        log.info(" LastestInfController lastestInfGroupDel: id="+lastInfGroup.getId());
        return lastestInfGroupService.delEntity(lastInfGroup);
	}
	@RequestMapping("/lastestInfDel")
	@ResponseBody
    public Map<String, Object> lastestInfDel(LastestInf lastInf, HttpServletRequest req){
        log.info(" LastestInfController lastestInfDel: id="+lastInf.getId());
        return lastestInfService.delEntity(lastInf);
	}
	@RequestMapping("/getLastestInfJsp")
	public ModelAndView getLastestInfJsp(LastestInf lastestInf, HttpServletRequest req){
        log.info(" LastestInfController getLastestInfJsp: Id="+lastestInf.getId());
        lastestInf = lastestInfService.getEntity(lastestInf);
        log.info("titleStr="+lastestInf.getName()+";contentStr="+lastestInf.getContentStr());
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("titleStr", lastestInf.getName());
        map.put("contentStr", lastestInf.getContentStr());
        ModelAndView mv = new ModelAndView("100",map);
		return mv;
	}

}
