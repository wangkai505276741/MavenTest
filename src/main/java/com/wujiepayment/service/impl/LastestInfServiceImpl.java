package com.wujiepayment.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.wujiepayment.been.LastestInf;
import com.wujiepayment.service.LastestInfService;
/** 
 * @author 王凯: 
 * @version 创建时间：2015年6月18日 上午11:20:03 
 * 类说明 
 */
@Service
public class LastestInfServiceImpl implements LastestInfService {
	private Logger log = Logger.getLogger(LastestInfServiceImpl.class);
	private String selectStr = "select id,groupId,name,descStr,imageUrl,urlStr,contentStr,createTime,createUserId, "
			+ " updateTime,updateUserId from lastestInf where 1=1 ";
	private String insertStr = "insert into lastestInf(id,groupId,name,descStr,imageUrl,urlStr,contentStr,createTime,createUserId, "
			+ " updateTime,updateUserId ) values(?,?,?,?,?,?,?,?,?,?,?) ";
	private String formatStr = "yyyy-MM-dd HH:mm:ss";
	private String selectLastestInfStr = "select a.id,a.groupId,b.name as groupName,a.name,a.descStr,a.imageUrl,a.urlStr,"
			+ "a.createTime,a.createUserId,a.updateTime,a.updateUserId from lastestInf a,lastestinfgroup b where a.groupId=b.id";
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public LastestInf getEntity(LastestInf t) {
		StringBuilder sql = new StringBuilder(selectStr);
		if(t.getId()!=null&&t.getId()!=0){
			sql.append(" and id="+t.getId());
		}
		sql.append(" limit 0,1 ");
		RowMapper<LastestInf> rm = ParameterizedBeanPropertyRowMapper.newInstance(LastestInf.class);
		try{
			return jdbcTemplate.queryForObject(sql.toString(), rm);
		}catch(Exception e){
			log.error(e);
			return null;
		}
	}

	@Override
	public List<LastestInf> getList(LastestInf t) {
		String sql = selectStr;
		if(t.getGroupId()!=null && t.getGroupId()!=0){
			sql += " and groupId="+t.getGroupId();
		}
		sql += " order by id desc";
		try{
			List list = jdbcTemplate.queryForList(sql);
			return list;
		}catch(Exception e){
			log.error("数据库操作错误！sql = " + sql);
			log.error(e);
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Map<String, Object> addEntity(LastestInf t) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
    	try{
    		String sql = "select ifnull(max(id),0)+1 from lastestInf";
			Integer id = jdbcTemplate.queryForObject(sql, Integer.class);
    		System.out.println("id = " + id);
    		t.setId(id);
    		if(t.getUrlStr()==null || "".equals(t.getUrlStr())){
    			t.setUrlStr("http://106.3.44.235:8080/MavenTest/getLastestInfJsp.do?id="+id);
    		}
    		Object[] param = new Object[]{ t.getId(),t.getGroupId(),t.getName(),t.getDescStr(),t.getImageUrl(),
    				t.getUrlStr(),t.getContentStr(),t.getCreateTime(),t.getCreateUserId(),t.getUpdateTime(),t.getUpdateUserId()};
    		jdbcTemplate.update(insertStr,param);
    		resultMap.put("RSPCOD", "00000");
        	resultMap.put("RSPMSG", "保存成功！");
        	return resultMap; 
	    }catch(Exception e){
			log.error("数据库操作错误！sql = " + insertStr);
			log.error(e);
	    	e.printStackTrace();
			resultMap.put("RSPCOD", "00001");
	    	resultMap.put("RSPMSG", "数据库操作错误！");
	    	return resultMap;
	    }
	}

	@Override
	public Map<String, Object> editEntity(LastestInf t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> delEntity(LastestInf t) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(t.getId()==null || t.getId()==0){
			resultMap.put("RSPCOD", "00001");
			resultMap.put("RSPMSG", "请选择要删除的数据！");
			return resultMap;
		}
		String sql = "delete from lastestInf where id = " + t.getId();
		try{
			jdbcTemplate.execute(sql);
			resultMap.put("RSPCOD", "00000");
			resultMap.put("RSPMSG", "删除成功！");
			return resultMap;
		} catch (Exception e){
			log.error(e);
			e.printStackTrace();
			resultMap.put("RSPCOD", "00002");
			resultMap.put("RSPMSG", "删除失败！");
			return resultMap;
		}
	}

	/**
	 * 查询最新资讯信息（关联组）
	 */
	@Override
	public List<Map<String, Object>> getLastestInfList(LastestInf t) {
		String sql = selectLastestInfStr;
		if(t.getGroupId()!=null && t.getGroupId()!=0){
			sql += " and a.groupId="+t.getGroupId();
		}
		sql += " order by a.id desc";
		try{
			List list = jdbcTemplate.queryForList(sql);
			return list;
		}catch(Exception e){
			System.out.println("数据库操作错误！sql = " + sql);
			log.error("数据库操作错误！sql = " + sql);
			log.error(e);
			e.printStackTrace();
			return null;
		}
	}

}
