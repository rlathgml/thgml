package com.ssafy.happyhouse.dao;

import java.util.List;

import com.ssafy.happyhouse.dto.Shop;
import com.ssafy.happyhouse.util.Search;

public interface ShopDAO {
	public List<Shop> selectShop(Search search);
	public Shop selectShopByNo(int no);
	public int selectShopCnt(Search search);
}
