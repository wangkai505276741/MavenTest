package com.wujiepayment.been;
/** 
 * @author 王凯: 
 * @version 创建时间：2015年6月18日 上午11:11:12 
 * 类说明 调额助手详情实体类
 */
public class CreditAdjustmentInf extends BaseBeen {
	
	private Integer id;
	private Integer groupId;
	private String name;
	private String descStr;
	private String imageUrl;
	private String urlStr;
	private String contentStr;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
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
	public String getUrlStr() {
		return urlStr;
	}
	public void setUrlStr(String urlStr) {
		this.urlStr = urlStr;
	}
	public String getContentStr() {
		return contentStr;
	}
	public void setContentStr(String contentStr) {
		this.contentStr = contentStr;
	}

}
