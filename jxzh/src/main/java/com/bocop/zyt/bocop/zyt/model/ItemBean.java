package com.bocop.zyt.bocop.zyt.model;

public class ItemBean {
	private String url;
	private String title;
	private int iconResouceId;
	private int parentId;
	
	public ItemBean(String title) {
		this.title = title;
	}
	
	public ItemBean(String title, int iconResouceId) {
		this.title = title;
		this.iconResouceId = iconResouceId;
	}
	
	public ItemBean(String url, String title, int iconResouceId) {
		this.url = url;
		this.title = title;
		this.iconResouceId = iconResouceId;
	}
	
	public ItemBean(String url, String title, int iconResouceId,int parentId) {
		this.url = url;
		this.title = title;
		this.iconResouceId = iconResouceId;
		this.parentId = parentId;
	}
	
	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getIconResouceId() {
		return iconResouceId;
	}
	public void setIconResouceId(int iconResouceId) {
		this.iconResouceId = iconResouceId;
	}
	
	
}
