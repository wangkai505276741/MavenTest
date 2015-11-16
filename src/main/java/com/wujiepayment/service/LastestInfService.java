package com.wujiepayment.service;

import java.util.List;
import java.util.Map;

import com.wujiepayment.been.LastestInf;

public interface LastestInfService extends BaseService<LastestInf> {
	public List<Map<String, Object>> getLastestInfList(LastestInf t);
}
