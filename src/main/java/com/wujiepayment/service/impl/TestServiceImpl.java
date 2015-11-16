package com.wujiepayment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.wujiepayment.service.TestService;

@Service
public class TestServiceImpl implements TestService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public String getIndexInfo(String str) {
		
		return "Hello, " + str;
	}

}
