package com.ssafy.happyhouse.dto;

public class Interest {

    // 번호 
    private Integer no;

    // 도/광역시 
    private String city;

    // 시/구/군 
    private String gu;

    // 동 
    private String dong;

    // 사용자아이디 
    private String userId;
    
    private String aptName;

    private int count;
    
    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }    
    
	public String getAptName() {
		return aptName;
	}

	public void setAptName(String aptName) {
		this.aptName = aptName;
	}

	
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "RegionOfInterest [no=" + no + ", city=" + city + ", gu=" + gu + ", dong=" + dong + ", userid=" + userId
				+ "]";
	}
}