package com.wujiepayment.been;
/** 
 * @author 王凯: 
 * @version 创建时间：2015年6月18日 上午10:22:29 
 * 类说明 
 */
public class EasyLoanInf extends BaseBeen {

	private Integer id;
	private String name;
	private String descStr;
	private String imageUrl;
	private String urlStr;
	/**
	 * 唯一标识ID
	 * @return
	 */
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 标题
	 * @return
	 */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 描述
	 * @return
	 */
	public String getDescStr() {
		return descStr;
	}
	public void setDescStr(String descStr) {
		this.descStr = descStr;
	}
	/**
	 * 图片链接
	 * @return
	 */
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	/**
	 * 页面链接
	 * @return
	 */
	public String getUrlStr() {
		return urlStr;
	}
	public void setUrlStr(String urlStr) {
		this.urlStr = urlStr;
	}

}
