package com.wujiepayment.service;

import java.util.List;
import java.util.Map;

public interface BaseService<T> {
	
	public T getEntity(T t);
	
	public List<T> getList(T t);
	
	public Map<String, Object> addEntity(T t);
	
	public Map<String, Object> editEntity(T t);
	
	public Map<String, Object> delEntity(T t);

}
