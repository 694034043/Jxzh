package com.framewidget.view;

public class Pois {
	String id = "";// POI唯一标识
	String title = "";// poi名称
	String address = "";// 地址
	String category = "";// POI分类
	String lat = "";// 纬度
	String lng = "";// 经度
	String _distance = "";// 该POI到逆地址解析传入的坐标的直线距离

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	String city = "";// 城市
	String district = "";// 城市

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String get_distance() {
		return _distance;
	}

	public void set_distance(String _distance) {
		this._distance = _distance;
	}

}
