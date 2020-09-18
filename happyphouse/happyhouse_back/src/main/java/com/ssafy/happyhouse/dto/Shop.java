package com.ssafy.happyhouse.dto;

import java.io.Serializable;

public class Shop  {
	private String no;
	private String shopName;
	private String codeName3;
	private String cityCode;
	private String city;
	private String guCode;
	private String gu;
	private String dong;
	private String dongCode;
	private String jibuAddress;
	private String address;
	private String oldPostCode;
	private String postCode;
	private String lng;
	private String lat;
	public Shop() {
		super();
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getCodeName3() {
		return codeName3;
	}
	public void setCodeName3(String codeName3) {
		this.codeName3 = codeName3;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getGuCode() {
		return guCode;
	}
	public void setGuCode(String guCode) {
		this.guCode = guCode;
	}
	public String getGu() {
		return gu;
	}
	public void setGu(String gu) {
		this.gu = gu;
	}
	public String getDong() {
		return dong;
	}
	public void setDong(String dong) {
		this.dong = dong;
	}
	public String getDongCode() {
		return dongCode;
	}
	public void setDongCode(String dongCode) {
		this.dongCode = dongCode;
	}
	public String getJibuAddress() {
		return jibuAddress;
	}
	public void setJibuAddress(String jibuAddress) {
		this.jibuAddress = jibuAddress;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getOldPostCode() {
		return oldPostCode;
	}
	public void setOldPostCode(String oldPostCode) {
		this.oldPostCode = oldPostCode;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	@Override
	public String toString() {
		return "Shop [no=" + no + ", shopName=" + shopName + ", codeName3=" + codeName3 + ", cityCode=" + cityCode
				+ ", city=" + city + ", guCode=" + guCode + ", gu=" + gu + ", dong=" + dong + ", dongCode=" + dongCode
				+ ", jibuAddress=" + jibuAddress + ", address=" + address + ", oldPostCode=" + oldPostCode
				+ ", postCode=" + postCode + ", lng=" + lng + ", lat=" + lat + "]";
	}
	
	
	
}
