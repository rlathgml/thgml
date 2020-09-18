package com.ssafy.happyhouse.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.dto.HouseDeal;
import com.ssafy.happyhouse.util.Pagination;
import com.ssafy.happyhouse.util.Search;

public interface HouseDAO {
	public List<HouseDeal> selectHouseDeal(Search search);
	public List<HouseDeal> selectHouseDealTop();
	public HouseDeal selectHouseDealByNo(int no);
	public int selectHouseDealCnt(Search search);
}
