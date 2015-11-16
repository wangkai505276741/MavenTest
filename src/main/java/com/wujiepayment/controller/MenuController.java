package com.wujiepayment.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MenuController {

	@RequestMapping("getMenuSet")
	public void getMenuSetHandler(@RequestParam(value="menuId" )String menuId,
			HttpServletRequest request, HttpServletResponse response) {
		if(menuId==null || "".equals(menuId)){
			
		}
		
	}
}
