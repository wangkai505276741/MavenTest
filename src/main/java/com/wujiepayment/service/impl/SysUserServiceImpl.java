package com.wujiepayment.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.wujiepayment.been.AppUserInf;
import com.wujiepayment.been.SysUserInf;
import com.wujiepayment.service.SysUserService;
import com.wujiepayment.util.DateUtil;
@Service
public class SysUserServiceImpl implements SysUserService {
	
	private String selectStr = "select id,userName,password,lastLoginIP,lastLoginTime,createTime,createUserId, "
			+ " updateTime,updateUserId from sysUserInf where 1=1 ";
	private String insertStr = "insert into sysUserInf(id,userName,password,lastLoginIP,lastLoginTime,createTime,createUserId, "
			+ " updateTime,updateUserId ) values(?,?,?,?,?,?,?,?,?) ";
	private String formatStr = "yyyy-MM-dd HH:mm:ss";
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public SysUserInf getEntity(SysUserInf t) {
		StringBuilder sql = new StringBuilder(selectStr);
		if(t.getId()!=null&&t.getId()!=0){
			sql.append(" and id="+t.getId());
		}
		if(t.getUserName()!=null&&!"".equals(t.getUserName())){
			sql.append(" and userName='"+t.getUserName().replaceAll("'", "")+"' ");
		}
		if(t.getPassword()!=null&&!"".equals(t.getPassword())){
			sql.append(" and password='"+t.getPassword().replaceAll("'", "")+"' ");
		}
		sql.append(" limit 0,1 ");
		RowMapper<SysUserInf> rm = ParameterizedBeanPropertyRowMapper.newInstance(SysUserInf.class);
		try{
			return jdbcTemplate.queryForObject(sql.toString(), rm);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<SysUserInf> getList(SysUserInf t) {String sqlStr = selectStr;
	if (t.getUserName()!=null && !"".equals(t.getUserName())){
		sqlStr += " and userName='"+t.getUserName()+"' ";
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
	public Map<String, Object> addEntity(SysUserInf t) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
    	try{
    		SysUserInf user = new SysUserInf();
    		user.setUserName(t.getUserName());
        	user = this.getEntity(user);
        	if(user == null || user.getId()==null || user.getId()==0 ){
        		String sql = "select ifnull(max(id),0)+1 from sysUserInf";
    			Integer id = jdbcTemplate.queryForObject(sql, Integer.class);
        		System.out.println("id = " + id);
        		t.setId(id);
        		String nowDate = DateUtil.getFormatDateStr("", new Date());
        		t.setCreateTime(nowDate);
        		Object[] param = new Object[]{ t.getId(),t.getUserName(),t.getPassword(),
        				t.getLastLoginIP(),t.getLastLoginTime(),t.getCreateTime(),t.getCreateUserId(),
        				t.getUpdateTime(),t.getUpdateUserId()};
        		jdbcTemplate.update(insertStr,param);
        		resultMap.put("RSPCOD", "00000");
            	resultMap.put("RSPMSG", "保存成功！");
            	return resultMap;
        	} else {
        		resultMap.put("RSPCOD", "00004");
            	resultMap.put("RSPMSG", t.getUserName()+"已被注册，请更换！");
            	return resultMap;
        	}
	
	    }catch(Exception e){
	    	System.out.println("数据库操作错误！");
	    	e.printStackTrace();
			resultMap.put("RSPCOD", "00001");
	    	resultMap.put("RSPMSG", "数据库操作错误！");
	    	return resultMap;
	    }
	}

	@Override
	public Map<String, Object> editEntity(SysUserInf t) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(t.getId()==null || t.getId()==0){
			resultMap.put("RSPCOD", "00001");
	    	resultMap.put("RSPMSG", "用户编号不能为空！");
	    	return resultMap;
		}
		String countSql = "select count(*) from sysUserInf where id<>"+t.getId()+" and userName='"+t.getUserName()+"'";
		try{
			Integer count = jdbcTemplate.queryForObject(countSql, Integer.class);
			if(count>0){
				resultMap.put("RSPCOD", "00003");
		    	resultMap.put("RSPMSG", "该用户名已存在，请更换！");
		    	return resultMap;
			}
			StringBuilder updateSql = new StringBuilder();
			updateSql.append(" update sysUserInf set ");
			if( t.getUserName()!=null&&!"".equals(t.getUserName())){
				updateSql.append(" userName='"+t.getUserName()+"',");
			}
			if( t.getPassword()!=null&&!"".equals(t.getPassword())){
				updateSql.append(" password='"+t.getPassword()+"',");
			}
			if( t.getLastLoginIP()!=null&&!"".equals(t.getLastLoginIP())){
				updateSql.append(" lastLoginIP='"+t.getLastLoginIP()+"',");
			}
			if( t.getLastLoginTime()!=null&&!"".equals(t.getLastLoginTime())){
				updateSql.append(" lastLoginTime='"+t.getLastLoginTime()+"',");
			}
			updateSql.append(" updateTime='");
			updateSql.append(DateUtil.getFormatDateStr("", new Date()));
			updateSql.append("', updateUserId='");
			updateSql.append(t.getUpdateUserId());
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
	public Map<String, Object> delEntity(SysUserInf t) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(t.getId()==null || t.getId()==0){
			resultMap.put("RSPCOD", "00001");
			resultMap.put("RSPMSG", "请选择要删除的用户！");
			return resultMap;
		}
		String sql = "delete from sysUserInf where id = " + t.getId();
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
