package com.wujiepayment.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wujiepayment.been.AppAdvertInf;
import com.wujiepayment.service.AppAdvertService;

/** 
 * @author 王凯: 
 * @version 创建时间：2015年6月20日 上午11:20:58 
 * 类说明  APP滚动广告
 */
@Controller
public class AppAdvertController {
	private Logger log = Logger.getLogger(AppAdvertController.class);
	@Autowired
	private AppAdvertService appAdvertService;
	
	/**
	 * 获取 APP滚动广告列表
	 * @param req
	 * @return
	 */
	@RequestMapping("/appAdvertInfQuery")
    @ResponseBody
    public Map<String, Object> appAdvertInfQuery(AppAdvertInf appAdvertInf, HttpServletRequest req){
		log.info(" AppAdvertController appAdvertInfQuery");
		System.out.println(" AppAdvertController appAdvertInfQuery");
		Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("RSPCOD", "00000");
    	resultMap.put("RSPMSG", "查询成功！");
		resultMap.put("weathManagementInf", appAdvertService.getList(appAdvertInf));
		return resultMap;
	}

}
