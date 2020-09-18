package com.ssafy.happyhouse.dto;

public class User {
    // 아이디 
    private String userId;
    // 비밀번호 
    private String password;
    // 사용자이름 
    private String userName;
    // 기본주소 
    private String address;
    // 휴대폰번호 
    private String phoneNumber;
    
    public User() {}
    
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", password=" + password + ", userName=" + userName + ", address=" + address
				+ ", phoneNumber=" + phoneNumber + "]";
	}
	
	

}
