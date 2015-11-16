package com.wujiepayment.service;

import java.util.Map;

import com.wujiepayment.been.AppUserInf;

public interface AppUserService extends BaseService<AppUserInf> {

	/**
	 * 获取验证码
	 * @return
	 */
	public Map<String, Object> getActivationCode();
}
