package com.wujiepayment.service;

import java.util.List;
import java.util.Map;

import com.wujiepayment.been.CreditAdjustmentInf;

/** 
 * @author 王凯: 
 * @version 创建时间：2015年6月18日 上午11:13:09 
 * 类说明 
 */
public interface CreditAdjustmentService extends BaseService<CreditAdjustmentInf> {
	public List<Map<String, Object>> getCreditAdjustmentInfList(CreditAdjustmentInf t);
}
