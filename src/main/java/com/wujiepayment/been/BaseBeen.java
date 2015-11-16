package com.wujiepayment.been;

public class BaseBeen {
	private String createTime;
	private Integer createUserId;
	private String updateTime;
	private Integer updateUserId;
	/**
	 * 创建时间
	 * @return
	 */
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	/**
	 * 创建人ID
	 * @return
	 */
	public Integer getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}
	/**
	 * 更新时间
	 * @return
	 */
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 更新人ID
	 * @return
	 */
	public Integer getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(Integer updateUserId) {
		this.updateUserId = updateUserId;
	}
	
}
