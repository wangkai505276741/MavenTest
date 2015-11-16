package com.wujiepayment.been;
/** 
 * @author 王凯: 
 * @version 创建时间：2015年6月18日 上午10:42:26 
 * 类说明 在线办卡实体类
 */
public class OnlineCard extends BaseBeen {
	
	private Integer id;
	private String name;
	private String descStr;
	private String imageUrl;
	private String urlStr;
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
	public String getUrlStr() {
		return urlStr;
	}
	public void setUrlStr(String urlStr) {
		this.urlStr = urlStr;
	}

}
