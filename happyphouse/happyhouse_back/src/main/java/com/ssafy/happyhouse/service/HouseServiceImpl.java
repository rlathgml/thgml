package com.ssafy.happyhouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.dao.HouseDAO;
import com.ssafy.happyhouse.dto.HouseDeal;
import com.ssafy.happyhouse.util.Search;

@Service
public class HouseServiceImpl implements HouseService{
	
	@Autowired
	private HouseDAO dao;

	@Override
	public List<HouseDeal> retrieveHouseDeal(Search search){
		return dao.selectHouseDeal(search);
	}	
	
	@Override
	public List<HouseDeal> retrieveHouseDealTop() {
		// TODO Auto-generated method stub
		return dao.selectHouseDealTop();
	}
	
	/**
	 * 아파트 식별 번호에 해당하는 아파트 거래 정보를 검색해서 반환. 
	 * @param no	검색할 아파트 식별 번호
	 * @return		아파트 식별 번호에 해당하는 아파트 거래 정보를 찾아서 리턴한다, 없으면 null이 리턴됨
	 */
	@Override
	public HouseDeal retrieveHouseDealByNo(int no) {
		return dao.selectHouseDealByNo(no);
	}
	
	@Override
	public int getHouseDealCnt(Search search) {
		// TODO Auto-generated method stub
		return dao.selectHouseDealCnt(search);
	}
	
}




