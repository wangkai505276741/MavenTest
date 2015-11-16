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

import com.wujiepayment.been.EasyLoanInf;
import com.wujiepayment.been.SysUserInf;
import com.wujiepayment.service.EasyLoanService;
import com.wujiepayment.util.DateUtil;

/** 
 * @author 王凯: 
 * @version 创建时间：2015年6月18日 上午10:35:15 
 * 类说明 一键贷款管理类
 */
@Controller
public class EasyLoanController {
	private static Logger log = Logger.getLogger(EasyLoanController.class);
	
	@Autowired
	private EasyLoanService easyLoanService;
	
	@RequestMapping("/easyLoanInfIndex")
    public String easyLoanInfIndex(HttpServletRequest req){
        log.info(" easyLoanController easyLoanInfIndex");
        return "easyLoanInf/easyLoanInfQuery";
	}
	/**
	 * 获取一键贷款列表
	 * @param req
	 * @return
	 */
	@RequestMapping("/easyLoanInfQuery")
    @ResponseBody
    public Map<String, Object> easyLoanInfQuery( HttpServletRequest req){
		log.info(" EasyLoanController easyLoanInfQuery");
		Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("RSPCOD", "00000");
    	resultMap.put("RSPMSG", "查询成功！");
		resultMap.put("easyLoanInf", easyLoanService.getList(new EasyLoanInf()));
		return resultMap;
	}
	
	/**
	 * 获取一键贷款列表
	 * @param req
	 * @return
	 */
	@RequestMapping("/easyLoanInfQuery2")
    @ResponseBody
    public List<EasyLoanInf> easyLoanInfQuery2( HttpServletRequest req){
		log.info(" EasyLoanController easyLoanInfQuery2");
		return easyLoanService.getList(new EasyLoanInf());
	}
	
	/**
	 * 在线办卡信息添加
	 * @param req
	 * @return
	 */
	@RequestMapping("/easyLoanInfAdd")
    public String easyLoanInfAdd(String nameAdd, @RequestParam MultipartFile imageUrlAdd,
    		String urlStrAdd, HttpServletRequest req){
		log.info(" easyLoanInfController easyLoanInfAdd: name="+nameAdd);
        SysUserInf sessionUserInf = (SysUserInf) req.getSession().getAttribute("sysUserInf");
        if(sessionUserInf == null || sessionUserInf.getId() == null ){
            return "login";
        }
		if(imageUrlAdd.isEmpty()){  
			Map<String, Object> resultMap = new HashMap<String, Object>();
            System.out.println("文件未上传");  
            resultMap.put("RSPCOD", "00001");
        	resultMap.put("RSPMSG", "文件未上传！");
            return "easyLoanInf/easyLoanInfQuery";
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
			log.error(e);
			e.printStackTrace();
		}
		EasyLoanInf easyLoanInf  = new EasyLoanInf();
		easyLoanInf.setName(nameAdd);
		easyLoanInf.setUrlStr(urlStrAdd);
		String url = req.getRequestURL().toString();
		String imageUrl = url.substring(0,url.lastIndexOf("/"))+"/images/lastestInf/"+fileName;
		System.out.println("ImageUrl = "+imageUrl);
		easyLoanInf.setImageUrl(imageUrl);
        String nowStr = DateUtil.getFormatDateStr("", new Date());
        easyLoanInf.setCreateTime(nowStr);
        easyLoanInf.setCreateUserId(sessionUserInf.getId());
        easyLoanInf.setUpdateTime(nowStr);
        easyLoanInf.setUpdateUserId(sessionUserInf.getId());
        easyLoanService.addEntity(easyLoanInf);
		return "easyLoanInf/easyLoanInfQuery";
	}
	
	@RequestMapping("/easyLoanInfDel")
	@ResponseBody
    public Map<String, Object> easyLoanInfDel(EasyLoanInf easyLoanInf, HttpServletRequest req){
        log.info(" EasyLoanController easyLoanInfDel: id="+easyLoanInf.getId());
        return easyLoanService.delEntity(easyLoanInf);
	}
}
