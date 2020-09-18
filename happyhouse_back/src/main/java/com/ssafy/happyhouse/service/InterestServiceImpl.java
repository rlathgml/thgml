package com.ssafy.happyhouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.dao.InterestDAO;
import com.ssafy.happyhouse.dto.Interest;

@Service
public class InterestServiceImpl implements InterestService {
	@Autowired
	private InterestDAO dao;

	@Override
	public List<Interest> retrieveInterestTop() {
		// TODO Auto-generated method stub
		return dao.selectInterestTop();
	}
	
	@Override
	public List<Interest> retrieveInterestByUserId(String userId) {
		// TODO Auto-generated method stub
		return dao.selectInterestByUserId(userId);
	}
	
	@Override
	public int retrieveInterestCnt(String userId) {
		// TODO Auto-generated method stub
		return dao.selectInterestCnt(userId);
	}
	
	@Override
	public boolean addInterest(Interest interest) {
		// TODO Auto-generated method stub
		if(dao.existsInterestTop(interest.getNo()) >= 1) {
			dao.updateInterestTop(interest.getNo());
		} else {
			dao.insertInterestTop(interest);
		}
		return dao.insertInterest(interest) == 1;
	}
	
	
	
	@Override
	public boolean removeInterest(int no) {
		// TODO Auto-generated method stub
		return dao.deleteInterest(no) == 1;
	}
	
}
