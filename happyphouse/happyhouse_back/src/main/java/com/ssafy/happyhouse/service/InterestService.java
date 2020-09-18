package com.ssafy.happyhouse.service;

import java.util.List;

import com.ssafy.happyhouse.dto.Interest;

public interface InterestService {
	public List<Interest> retrieveInterestTop();
	public List<Interest> retrieveInterestByUserId(String userId);
	public int retrieveInterestCnt(String userId);
	public boolean removeInterest(int no);
	public boolean addInterest(Interest interest);
}
