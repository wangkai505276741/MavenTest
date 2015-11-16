package com.wujiepayment.been;
/** 
 * @author 王凯: 
 * @version 创建时间：2015年6月18日 上午11:10:00 
 * 类说明 调额助手小组实体类
 */
public class CreditAdjustmentGroup extends BaseBeen {
	private Integer id;
	private String name;
	private String imageUrl;
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
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
