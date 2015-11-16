package com.wujiepayment.been;
/** 
 * @author 王凯: 
 * @version 创建时间：2015年6月20日 上午11:10:37 
 * 类说明 APP滚动广告
 */
public class AppAdvertInf extends BaseBeen {

	private Integer id;
	private String name;
	private String descStr;
	private String imageUrl;
	private String actionUrl;
	private Integer status;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescStr() {
		return descStr;
	}
	public void setDescStr(String descStr) {
		this.descStr = descStr;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getActionUrl() {
		return actionUrl;
	}
	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
