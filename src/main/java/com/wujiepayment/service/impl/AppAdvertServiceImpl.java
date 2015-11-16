package com.wujiepayment.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.wujiepayment.been.AppAdvertInf;
import com.wujiepayment.service.AppAdvertService;

/** 
 * @author 王凯: 
 * @version 创建时间：2015年6月20日 上午11:14:48 
 * 类说明  APP滚动广告
 */
@Service
public class AppAdvertServiceImpl implements AppAdvertService {
	
	private String selectStr = "select id,name,descStr,imageUrl,urlStr,status,createTime,createUserId, "
			+ " updateTime,updateUserId from appAdvertInf where 1=1 ";
	private String insertStr = "insert into appAdvertInf(id,name,descStr,imageUrl,urlStr,status,createTime,createUserId, "
			+ " updateTime,updateUserId ) values(?,?,?,?,?,?,?,?,?,?,?) ";
	private String formatStr = "yyyy-MM-dd HH:mm:ss";
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public AppAdvertInf getEntity(AppAdvertInf t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AppAdvertInf> getList(AppAdvertInf t) {
		String sql = selectStr;
		if(t.getStatus()!=null && t.getStatus()!=0){
			sql += " and status="+t.getStatus();
		}
		sql += " order by createTime desc";
		try{
			List list = jdbcTemplate.queryForList(sql);
			return list;
		}catch(Exception e){
			System.out.println("数据库操作错误！sql = " + sql);
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Map<String, Object> addEntity(AppAdvertInf t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> editEntity(AppAdvertInf t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> delEntity(AppAdvertInf t) {
		// TODO Auto-generated method stub
		return null;
	}

}
