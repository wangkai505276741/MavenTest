package com.wujiepayment.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.wujiepayment.been.AppUserInf;
import com.wujiepayment.service.AppUserService;
import com.wujiepayment.util.DateUtil;
@Service
public class AppUserServiceImpl implements AppUserService {
	Logger logger = Logger.getLogger(AppUserServiceImpl.class);
	private String selectStr = "select id,loginName,loginPassword,activationCode,status, "
			+ " createTime,realName,idCard,email,uniqueCode,createTime,updateTime,loginX,loginY from appUserInf where 1=1 ";

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public AppUserInf getEntity(AppUserInf userInf) {
		String sqlStr = selectStr;
		if (userInf.getId()!=null && userInf.getId()!=0){
			sqlStr += " and id="+userInf.getId()+" ";
		}
		if (userInf.getLoginName()!=null && !"".equals(userInf.getLoginName())){
			sqlStr += " and loginName='"+userInf.getLoginName()+"' ";
		}
		if (userInf.getLoginPassword()!=null && !"".equals(userInf.getLoginPassword())){
			sqlStr += " and loginPassword='"+userInf.getLoginPassword()+"' ";
		}
		if (userInf.getUniqueCode()!=null && !"".equals(userInf.getUniqueCode())){
			sqlStr += " and uniqueCode='"+userInf.getUniqueCode()+"' ";
		}
		sqlStr += " limit 0,1 ";
		RowMapper<AppUserInf> rm = ParameterizedBeanPropertyRowMapper.newInstance(AppUserInf.class);
		try{
			userInf = jdbcTemplate.queryForObject(sqlStr, rm);
			return userInf;
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<AppUserInf> getList(AppUserInf t) {
		String sqlStr = selectStr;
		if (t.getLoginName()!=null && !"".equals(t.getLoginName())){
			sqlStr += " and loginName='"+t.getLoginName()+"' ";
		}
		if (t.getRealName()!=null && !"".equals(t.getRealName())){
			sqlStr += " and realName='"+t.getRealName()+"' ";
		}
		if (t.getActivationCode()!=null && !"".equals(t.getActivationCode())){
			sqlStr += " and activationCode='"+t.getActivationCode()+"' ";
		}
		sqlStr += " order by createTime desc";
		try{
			System.out.println("sql="+sqlStr);
			List list = jdbcTemplate.queryForList(sqlStr);
			return list;
		}catch(Exception e){
			System.out.println("数据库操作错误！sql = " + sqlStr);
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Map<String, Object> addEntity(AppUserInf userInf) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String sql = "select status from activationInf where activationCode = '" + userInf.getActivationCode() + "' ";
        System.out.println(sql);
        try{
			List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);	
	        if(resultList == null || resultList.size()==0) {
	        	resultMap.put("RSPCOD", "00001");
	        	resultMap.put("RSPMSG", "该激活码不存在，请输入正确的激活码！");
	        	return resultMap;
	        }
	        String status = resultList.get(0).get("status") + "";
	        System.out.println("status = " + status);
	        if("1".equals(status)){//激活码状态为待使用时
	        	AppUserInf user = new AppUserInf();
	        	user.setLoginName(userInf.getLoginName());
	        	user = this.getEntity(user);
	        	if(user == null || user.getId()==0 ){
	        		sql = "select ifnull(max(id),0)+1 from appUserInf";
	    			Integer id = jdbcTemplate.queryForObject(sql, Integer.class);
	        		System.out.println("id = " + id);
	        		userInf.setId(id);
	        		String nowDate = DateUtil.getFormatDateStr("", new Date());
	        		userInf.setCreateTime(nowDate);
	        		userInf.setStatus("1");
	        		sql = "update activationInf set status = ?,useTime = ?,userId = ? where activationCode = ? ";
	        		Object[] param1 = new Object[]{
	        				"2",nowDate,id,userInf.getActivationCode()
	        		};
	        		jdbcTemplate.update(sql, param1);
	        		if(userInf.getUniqueCode()==null){
	        			userInf.setUniqueCode("");
	        		}
	        		sql = "insert into appUserInf (id,loginName,loginPassword,activationCode,status,"
	        				+ "realName,idCard,email,uniqueCode,createTime) values (?,?,?,?,?,?,?,?,?,?) ";
	        		Object[] param = new Object[]{ userInf.getId(),userInf.getLoginName(),userInf.getLoginPassword(),
	        				userInf.getActivationCode(),userInf.getStatus(),userInf.getRealName(),
	        				userInf.getIdCard(),userInf.getEmail(),userInf.getUniqueCode(),DateUtil.getFormatDateStr("", new Date())};
	        		jdbcTemplate.update(sql, param);
	        		resultMap.put("RSPCOD", "00000");
	            	resultMap.put("RSPMSG", "恭喜您注册成功！");
	            	return resultMap;
	        	} else {
	        		resultMap.put("RSPCOD", "00004");
	            	resultMap.put("RSPMSG", "该手机号码已被注册，请更换！");
	            	return resultMap;
	        	}
	        }else if ("2".equals(status)){
	        	resultMap.put("RSPCOD", "00002");
	        	resultMap.put("RSPMSG", "该激活码已被使用，请更换！");
	        	return resultMap;
	        }else if ("3".equals(status)){
	        	resultMap.put("RSPCOD", "00003");
	        	resultMap.put("RSPMSG", "该激活码已作废，请更换！");
	        	return resultMap;
	        } 
    	
        }catch(Exception e){
        	logger.error("数据库操作错误！");
        	logger.error(e);
        	e.printStackTrace();
			resultMap.put("RSPCOD", "00001");
	    	resultMap.put("RSPMSG", "数据库操作错误！");
	    	return resultMap;
        }
        resultMap.put("RSPCOD", "00001");
    	resultMap.put("RSPMSG", "该激活码不存在，请输入正确的激活码！");
    	return resultMap;
	}

	@Override
	public Map<String, Object> editEntity(AppUserInf t) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(t.getId()==null || t.getId()==0){
			resultMap.put("RSPCOD", "00001");
	    	resultMap.put("RSPMSG", "用户编号不能为空！");
	    	return resultMap;
		}
		String countSql = "select count(*) from appUserInf where id<>"+t.getId()+" and loginName='"+t.getLoginName()+"'";
		try{
			Integer count = jdbcTemplate.queryForObject(countSql, Integer.class);
			if(count>0){
				resultMap.put("RSPCOD", "00003");
		    	resultMap.put("RSPMSG", "手机号"+ t.getLoginName() +"已存在，请更换！");
		    	return resultMap;
			}
			StringBuilder updateSql = new StringBuilder();
			updateSql.append(" update appUserInf set ");
			if( t.getLoginName()!=null&&!"".equals(t.getLoginName())){
				updateSql.append(" loginName='"+t.getLoginName()+"',");
			}
			if( t.getLoginPassword()!=null&&!"".equals(t.getLoginPassword())){
				updateSql.append(" loginPassword='"+t.getLoginPassword()+"',");
			}
			if( t.getRealName()!=null&&!"".equals(t.getRealName())){
				updateSql.append(" realName='"+t.getRealName()+"',");
			}
			if( t.getIdCard()!=null&&!"".equals(t.getIdCard())){
				updateSql.append(" idCard='"+t.getIdCard()+"',");
			}
			if( t.getUniqueCode()!=null&&!"".equals(t.getUniqueCode())){
				updateSql.append(" uniqueCode='"+t.getUniqueCode()+"',");
			}
			if( t.getEmail()!=null&&!"".equals(t.getEmail())){
				updateSql.append(" email='"+t.getEmail()+"',");
			}
			if( t.getLoginX()!=null&&!"".equals(t.getLoginX())){
				updateSql.append(" loginX='"+t.getLoginX()+"',");
			}
			if( t.getLoginY()!=null&&!"".equals(t.getLoginY())){
				updateSql.append(" loginY='"+t.getLoginY()+"',");
			}
			updateSql.append(" updateTime='");
			updateSql.append(DateUtil.getFormatDateStr("", new Date()));
			updateSql.append("' where id="+t.getId());
			System.out.println(updateSql.toString());
    		jdbcTemplate.update(updateSql.toString());
    		resultMap.put("RSPCOD", "00000");
        	resultMap.put("RSPMSG", "保存成功！");
        	return resultMap;
		}catch(Exception e){
	    	System.out.println("数据库操作错误！");
	    	e.printStackTrace();
			resultMap.put("RSPCOD", "00002");
	    	resultMap.put("RSPMSG", "数据库操作错误！");
	    	return resultMap;
		}
	}

	@Override
	public Map<String, Object> getActivationCode() {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String curTime = System.currentTimeMillis() + "";
		StringBuffer activitionCode = new StringBuffer();
		activitionCode.append(curTime.substring(10, 13));
		activitionCode.append(curTime.substring(1, 3));
		activitionCode.append(curTime.substring(3, 9));
		activitionCode.append(curTime.substring(9, 10));
		System.out.println("验证码为："+activitionCode.toString());
		try{

			String sql = "select ifnull(max(id),0)+1 from activationInf" ;
			Integer id = jdbcTemplate.queryForObject(sql, Integer.class);
			SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(  
	                "yyyy-MM-dd HH:mm:ss");  
			String nowData = localSimpleDateFormat.format(new Date());
			sql = "insert into activationInf(id,activationCode,status,CreateTime)"
					+ " values(?,?,?,?) ";
			Object[] param = new Object[]{
					id,activitionCode.toString(),1,nowData
			};
			jdbcTemplate.update(sql, param);
			
		}catch(Exception e){
			System.out.println("数据库操作错误：" + e);
			e.printStackTrace(); 
			resultMap.put("RSPCOD", "00001");
	    	resultMap.put("RSPMSG", "数据库操作错误！");
	    	return resultMap;
		}
		resultMap.put("RSPCOD", "00000");
    	resultMap.put("RSPMSG", "验证码为：" + activitionCode.toString());
    	resultMap.put("activitionCode", activitionCode.toString());
    	return resultMap;
	}

	@Override
	public Map<String, Object> delEntity(AppUserInf t) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(t.getId()==null || t.getId()==0){
			resultMap.put("RSPCOD", "00001");
			resultMap.put("RSPMSG", "请选择要删除的用户！");
			return resultMap;
		}
		String sql = "delete from appUserInf where id = " + t.getId();
		try{
			jdbcTemplate.execute(sql);
			resultMap.put("RSPCOD", "00000");
			resultMap.put("RSPMSG", "用户删除成功！");
			return resultMap;
		} catch (Exception e){
			e.printStackTrace();
			resultMap.put("RSPCOD", "00002");
			resultMap.put("RSPMSG", "用户删除失败！");
			return resultMap;
		}
	}
	
	

}
