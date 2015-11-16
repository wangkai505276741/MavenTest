package com.wujiepayment.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wujiepayment.been.CardInf;
import com.wujiepayment.service.CardInfService;

@Controller
public class CardInfController {
	private static Logger log = Logger.getLogger(CardInfController.class);
	@Autowired
	private CardInfService cardInfService;
	
	@RequestMapping("/appCardInfQuery")
    @ResponseBody
    public Map<String, Object> appCardInfQuery( HttpServletRequest req){
        log.info(" CardInfController appCardInfQuery:");
        System.out.println(" CardInfController appCardInfQuery:");
        Integer appUserId = Integer.parseInt(req.getParameter("appUserId"));
        CardInf cardInf = new CardInf();
        cardInf.setAppUserId(appUserId);
        List<CardInf> cardInfList = cardInfService.getList(cardInf);
        Map<String,Object> resultMap = new HashMap<String, Object>();
        resultMap.put("RSPCOD", "00000");
    	resultMap.put("RSPMSG", "查询成功！");
    	resultMap.put("cardInfList", cardInfList);
        return resultMap;
	}

	@RequestMapping("/appCardInfAdd")
    @ResponseBody
    public Map<String, Object> appCardInfAdd(HttpServletRequest req){
        log.info(" CardInfController appCardInfAdd:");
        System.out.println(" CardInfController appCardInfAdd:");
        Integer appUserId = Integer.parseInt(req.getParameter("appUserId"));
        String cardNo = req.getParameter("cardNo");
        String bankName = req.getParameter("bankName");
        String realName = req.getParameter("realName");
        System.out.println(" CardInfController appCardInfAdd: appUserId:"+appUserId
        		+";cardNo:"+cardNo+";bankName:"+bankName
        		+";realName:"+realName);
        CardInf cardInf = new CardInf();
        cardInf.setAppUserId(appUserId);
        cardInf.setCardNo(cardNo);
        cardInf.setBankName(bankName);
        cardInf.setRealName(realName);
        return cardInfService.addEntity(cardInf);
	}
	
	@RequestMapping("/appCardInfDel")
	@ResponseBody
    public Map<String, Object> appCardInfDel(CardInf cardInf, HttpServletRequest req){
        log.info(" CardInfController appCardInfDel: id="+cardInf.getId());
        System.out.println(" CardInfController appCardInfDel: id="+cardInf.getId());
        return cardInfService.delEntity(cardInf);
	}
	
	
	@RequestMapping("/appCardInfEdit")
	@ResponseBody
    public Map<String, Object> appCardInfEdit(CardInf cardInf, HttpServletRequest req){
        log.info(" CardInfController appCardInfEdit: id="+cardInf.getId());
        System.out.println(" CardInfController appCardInfEdit: id="+cardInf.getId());
        return cardInfService.delEntity(cardInf);
	}

}
