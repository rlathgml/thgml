package com.ssafy.happyhouse.service;

import java.util.List;

import com.ssafy.happyhouse.dto.HouseDeal;
import com.ssafy.happyhouse.util.Search;
public interface HouseService {
	public List<HouseDeal> retrieveHouseDeal(Search search);
	public List<HouseDeal> retrieveHouseDealTop();
	public HouseDeal retrieveHouseDealByNo(int no);
	public int getHouseDealCnt(Search search);
	
}
