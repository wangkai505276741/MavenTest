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
import com.wujiepayment.been.CardInf;
import com.wujiepayment.service.CardInfService;
import com.wujiepayment.util.DateUtil;
/**
 * 卡号登记serviceImpl
 * @author wangkai
 *
 */
@Service
public class CardServiceImpl implements CardInfService {
	
	private String selectStr = "select id,appUserId,cardNo,bankName,realName, "
			+ " createTime,updateTime,remark from cardInf where 1=1 ";
	private String insertStr = "insert into cardInf(id,appUserId,cardNo,bankName,realName, "
			+ " createTime,updateTime,remark) values(?,?,?,?,?,?,?,?) ";
	private String formatStr = "yyyy-MM-dd HH:mm:ss";

	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public CardInf getEntity(CardInf t) {
		String sqlStr = selectStr;
		if (t.getId()!=0){
			sqlStr += " and id="+t.getId()+" ";
		}
		sqlStr += " limit 0,1 ";
		RowMapper<CardInf> rm = ParameterizedBeanPropertyRowMapper.newInstance(CardInf.class);
		try{
			t = jdbcTemplate.queryForObject(sqlStr, rm);
			return t;
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<CardInf> getList(CardInf t) {
		String sqlStr = selectStr;
		if(t.getAppUserId()!=0){
			sqlStr += " and appUserId="+t.getAppUserId();
		}
		try{
			List list = jdbcTemplate.queryForList(sqlStr);
			return list;
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Map<String, Object> addEntity(CardInf t) {
		System.out.println("CardServiceImpl addEntity: ");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String sqlStr = "select ifnull(max(id),0)+1 from cardInf";
		try{
			Integer id = jdbcTemplate.queryForObject(sqlStr, Integer.class);
			sqlStr = insertStr;
			String nowDate = DateUtil.getFormatDateStr(formatStr, new Date());
			Object[] param = new Object[]{id,t.getAppUserId(),t.getCardNo(),t.getBankName()
					,t.getRealName(),nowDate,nowDate,t.getRemark()};
			jdbcTemplate.update(sqlStr, param);
			resultMap.put("RSPCOD", "00000");
	    	resultMap.put("RSPMSG", "保存成功！");
	    	return resultMap;
		}catch(Exception e){
			System.out.println("CardServiceImpl addEntity 数据库操作失败！");
			e.printStackTrace();
			resultMap.put("RSPCOD", "00001");
			resultMap.put("RSPMSG", "保存失败！");
			return resultMap;
		}
	}

	@Override
	public Map<String, Object> editEntity(CardInf t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> delEntity(CardInf t) {
		System.out.println("CardServiceImpl delEntity id="+t.getId());
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(t.getId()==null || t.getId()==0){
			resultMap.put("RSPCOD", "00001");
			resultMap.put("RSPMSG", "请选择要删除的银行卡！");
			return resultMap;
		}
		String sql = "delete from cardInf where id = " + t.getId();
		try{
			jdbcTemplate.execute(sql);
			resultMap.put("RSPCOD", "00000");
			resultMap.put("RSPMSG", "删除成功！");
			return resultMap;
		} catch (Exception e){
			e.printStackTrace();
			resultMap.put("RSPCOD", "00002");
			resultMap.put("RSPMSG", "删除失败！");
			return resultMap;
		}
	}

}
