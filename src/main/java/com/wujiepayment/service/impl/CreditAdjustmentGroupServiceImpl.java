package com.wujiepayment.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.wujiepayment.been.CreditAdjustmentGroup;
import com.wujiepayment.service.CreditAdjustmentGroupService;

/** 
 * @author 王凯: 
 * @version 创建时间：2015年6月18日 上午11:20:03 
 * 类说明 调额助手小组实现类
 */
@Service
public class CreditAdjustmentGroupServiceImpl implements CreditAdjustmentGroupService{
	private Logger log = Logger.getLogger(CreditAdjustmentGroupServiceImpl.class);
	
	private String selectStr = "select id,name,imageUrl,createTime,createUserId, "
			+ " updateTime,updateUserId from creditAdjustmentGroup where 1=1 ";
	private String insertStr = "insert into creditAdjustmentGroup(id,name,imageUrl,createTime,createUserId, "
			+ " updateTime,updateUserId) values(?,?,?,?,?,?,?) ";
	private String formatStr = "yyyy-MM-dd HH:mm:ss";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public CreditAdjustmentGroup getEntity(CreditAdjustmentGroup t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CreditAdjustmentGroup> getList(CreditAdjustmentGroup t) {
		String sql = selectStr;
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
	public Map<String, Object> addEntity(CreditAdjustmentGroup t) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
    	try{
        		String sql = "select ifnull(max(id),0)+1 from lastestInfGroup";
    			Integer id = jdbcTemplate.queryForObject(sql, Integer.class);
        		System.out.println("id = " + id);
        		t.setId(id);
        		Object[] param = new Object[]{ t.getId(),t.getName(),t.getImageUrl(),
        				t.getCreateTime(),t.getCreateUserId(),t.getUpdateTime(),t.getUpdateUserId()};
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
	public Map<String, Object> editEntity(CreditAdjustmentGroup t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> delEntity(CreditAdjustmentGroup t) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(t.getId()==null || t.getId()==0){
			resultMap.put("RSPCOD", "00001");
			resultMap.put("RSPMSG", "请选择要删除的数据！");
			return resultMap;
		}
		String sql = "delete from lastestInfGroup where id = " + t.getId();
		try{
			jdbcTemplate.execute(sql);
			resultMap.put("RSPCOD", "00000");
			resultMap.put("RSPMSG", "删除成功！");
			return resultMap;
		} catch (Exception e){
			log.error("数据库操作错误！sql = " + sql);
			log.error(e);
			e.printStackTrace();
			resultMap.put("RSPCOD", "00002");
			resultMap.put("RSPMSG", "删除失败！");
			return resultMap;
		}
	}

}
