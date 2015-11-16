package com.wujiepayment.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wujiepayment.service.TestService;
import com.wujiepayment.test.TestConnect;

@Controller
public class TestController {
	
	@Autowired
	private TestService testService;

	@RequestMapping("helloworld")
	@ResponseBody
    public String getNewName(HttpServletRequest request){
           return "helloworld!123";

    }
	
	@RequestMapping("helloworld2")
    public void getNewsInf( HttpServletRequest req, HttpServletResponse res){
        try {
			PrintWriter out = res.getWriter();
			//JsonUtils.readJson("");
			String curTime = System.currentTimeMillis() + "";
			System.out.println(curTime);
			System.out.println(curTime.substring(0, 4));
			System.out.println(curTime.substring(4, 8));
			System.out.println(curTime.substring(8, 12));
			StringBuffer activitionCode = new StringBuffer();
			activitionCode.append(curTime.substring(10, 13));
			activitionCode.append(curTime.substring(1, 3));
			activitionCode.append(curTime.substring(3, 9));
			activitionCode.append(curTime.substring(9, 10));
			System.out.println(activitionCode.toString());
			out.println("Hello world!");
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	@RequestMapping("sendMsgTest")
	@ResponseBody
	public String sendMsgTest(HttpServletRequest req, HttpServletResponse res){
		try {
			TestConnect.connect();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("++++++++++++++++++++++++++++++++++++++++");
		String str = TestConnect.sendMsg(System.currentTimeMillis()+"");
		System.out.println("=====================================");
		System.out.println(str);
		System.out.println("=====================================");
		return str;
	}
	
	@RequestMapping("getString")
	@ResponseBody
    public String getString(String str,HttpServletRequest request){
		System.out.println(str);
           return str;

    }
	
}
