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

import com.wujiepayment.been.OnlineCard;
import com.wujiepayment.been.SysUserInf;
import com.wujiepayment.service.OnlineCardService;
import com.wujiepayment.util.DateUtil;

/** 
 * @author 王凯: 
 * @version 创建时间：2015年6月18日 上午10:50:57 
 * 类说明  在线办卡管理类
 */
@Controller
public class OnlineCardController {
	private static Logger log = Logger.getLogger(OnlineCardController.class);
	
	@Autowired
	private OnlineCardService onlineCardService;
	
	@RequestMapping("/onlineCardIndex")
    public String onlineCardIndex(HttpServletRequest req){
        log.info(" OnlineCardController onlineCardIndex");
        return "onlineCard/onlineCardQuery";
	}
	
	/**
	 * 获取在线办卡列表
	 * @param req
	 * @return
	 */
	@RequestMapping("/onlineCardInfQuery")
    @ResponseBody
    public Map<String, Object> onlineCardInfQuery( HttpServletRequest req){
		log.info(" OnlineCardController onlineCardInfQuery");
		System.out.println(" OnlineCardController onlineCardInfQuery");
		Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("RSPCOD", "00000");
    	resultMap.put("RSPMSG", "查询成功！");
		resultMap.put("onlineCardInf", onlineCardService.getList(new OnlineCard()));
		return resultMap;
	}
	
	/**
	 * 获取在线办卡列表(直接返回List)
	 * @param req
	 * @return
	 */
	@RequestMapping("/onlineCardInfQuery2")
    @ResponseBody
    public List<OnlineCard> onlineCardInfQuery2( HttpServletRequest req){
		log.info(" OnlineCardController onlineCardInfQuery2");
		System.out.println(" OnlineCardController onlineCardInfQuery2");
		return onlineCardService.getList(new OnlineCard());
	}

	/**
	 * 在线办卡信息添加
	 * @param req
	 * @return
	 */
	@RequestMapping("/onlineCardAdd")
    public String onlineCardAdd(String nameAdd, @RequestParam MultipartFile imageUrlAdd,
    		String urlStrAdd, HttpServletRequest req){
		log.info(" OnlineCardController onlineCardAdd: name="+nameAdd);
        SysUserInf sessionUserInf = (SysUserInf) req.getSession().getAttribute("sysUserInf");
        if(sessionUserInf == null || sessionUserInf.getId() == null ){
            return "login";
        }
		if(imageUrlAdd.isEmpty()){  
			Map<String, Object> resultMap = new HashMap<String, Object>();
            System.out.println("文件未上传");  
            resultMap.put("RSPCOD", "00001");
        	resultMap.put("RSPMSG", "文件未上传！");
            return "onlineCard/onlineCardQuery";
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
		OnlineCard onlineCard  = new OnlineCard();
		onlineCard.setName(nameAdd);
		onlineCard.setUrlStr(urlStrAdd);
		String url = req.getRequestURL().toString();
		String imageUrl = url.substring(0,url.lastIndexOf("/"))+"/images/lastestInf/"+fileName;
		System.out.println("ImageUrl = "+imageUrl);
		onlineCard.setImageUrl(imageUrl);
        String nowStr = DateUtil.getFormatDateStr("", new Date());
        onlineCard.setCreateTime(nowStr);
        onlineCard.setCreateUserId(sessionUserInf.getId());
        onlineCard.setUpdateTime(nowStr);
        onlineCard.setUpdateUserId(sessionUserInf.getId());
        onlineCardService.addEntity(onlineCard);
		return "onlineCard/onlineCardQuery";
	}

	@RequestMapping("/onlineCardDel")
	@ResponseBody
    public Map<String, Object> onlineCardDel(OnlineCard onlineCard, HttpServletRequest req){
        log.info(" OnlineCardController onlineCardDel: id="+onlineCard.getId());
        return onlineCardService.delEntity(onlineCard);
	}

}
