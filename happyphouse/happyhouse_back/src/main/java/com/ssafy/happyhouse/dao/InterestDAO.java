package com.ssafy.happyhouse.dao;

import java.util.List;

import com.ssafy.happyhouse.dto.Interest;

public interface InterestDAO {
	public int selectInterestCnt(String userId);
	public List<Interest> selectInterestTop();
	public List<Interest> selectInterestByUserId(String userId);
	public int deleteInterest(int no);
	public int insertInterest(Interest interest);
	public int insertInterestTop(Interest interest);
	public int existsInterestTop(int no);
	public void updateInterestTop(int no);
}
