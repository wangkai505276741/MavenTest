package com.wujiepayment.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServlet;


/** 
* @author 王凯: 
* @version 创建时间：2015年8月18日 上午10:35:04 
* 类说明 
*/

public class TestConnect extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final ThreadLocal<Socket> threadConnect = new ThreadLocal<Socket>(); 
	
	private static final String HOST = "106.3.44.235";

	private static final int PORT = 8888;
	
	//发送至通道方
	private static Socket client;
	
	//接收本地消息
	private static ServerSocket serverSocket;
	
	//本地客户端
	private static Socket localClient;
	
	private static OutputStream outStr = null;
	
	private static InputStream inStr = null;
	
	private static Thread tKeep = new Thread(new KeepThread());
	private static Thread tRecv = new Thread(new RecvThread());
	private static Thread tSend = new Thread(new SendThread());
	private static Thread tClient = new Thread(new ClientThread());

	public static void connect() throws UnknownHostException, IOException  {
		if(client == null){
			client = new Socket(HOST, PORT);
			threadConnect.set(client);
			tKeep.start();
			System.out.println("========链接开始！========");
		}
		outStr = client.getOutputStream();
		inStr = client.getInputStream();
	}
	
	public static void disconnect() {
		try {
			outStr.close();
			inStr.close();
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static String sendMsg(String str){
		System.out.println("======发送数据："+str+"====");
		try {
			outStr.write(str.getBytes());
			while (true) {
				byte[] b = new byte[1024];
				int r = inStr.read(b);
				if(r>-1){
					System.out.println(new String(b).trim());
					return new String(b).trim();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * 向外发送——保持心跳包
	 * @author 王凯
	 *
	 */
	private static class KeepThread implements Runnable {
		public void run() {
			try {
				System.out.println("=====================开始发送心跳包==============");
				while (true) {
					try {
						Thread.sleep(30000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//System.out.println("发送心跳数据包");
					outStr.write("send heart beat data package !".getBytes());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	/**
	 * 向外接收——接收并转发给本地短连接
	 * @author wangkai
	 *
	 */
	private static class RecvThread implements Runnable {
		
		public void run() {
			try {
				System.out.println("RecvThread 开始接收上游渠道信息信息！");
				while (!Thread.currentThread().isInterrupted()) {
					PushbackInputStream serverinput = new PushbackInputStream(inStr);
					byte[] inbyte = new byte[serverinput.available()];
					int len = serverinput.read(inbyte);
					if(len > 0){
						System.out.println("RecvThread len:" + len + "; " + byte2hex(inbyte));
						localClient.getOutputStream().write(inbyte);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	
	/**
	 * 向外发送——接收本地短连接并发送给上游渠道
	 * @author wangkai
	 *
	 */
	private static class SendThread implements Runnable {
		public void run() {
			try {
				System.out.println("SendThread 开始接收本地端链接信息！");
				while(!Thread.currentThread().isInterrupted()){
					PushbackInputStream serverinput = new PushbackInputStream(localClient.getInputStream());
					byte[] inbyte = new byte[serverinput.available()];
					int len = serverinput.read(inbyte);
					if(len > 0){
						System.out.println("SendThread len:" + len + "; " +  byte2hex(inbyte));
						outStr.write(inbyte);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	
	private static class ClientThread implements Runnable {
		private static boolean flag = false;
		public void run() {
			try {
				while (true) {    
	                // 一旦有堵塞, 则表示服务器与客户端获得了连接    
					localClient = serverSocket.accept(); 
					System.out.println("localClient 已获取到：" +localClient.getPort());
					if(!flag){
						tRecv.start();
						tSend.start();
						flag = true;
					}
	            }   
			} catch (IOException e ) {
				e.printStackTrace();
			}

		}
	}
	public static String byte2hex(byte[] b){
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++){
			stmp = Integer.toHexString(b[n] & 0xFF);
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}
	public void init(){
		System.out.println("=============================init function ================");
		try {
			TestConnect.connect();
			TestConnect.service();
			//tRecv.start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void destroy(){
		System.out.println("=============================destroy function ================");
		TestConnect.disconnect();
	}
	
	public static void service(){
		try {
			System.out.println("=============================service function ================");
			serverSocket = new ServerSocket(9999);
			tClient.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
} 
