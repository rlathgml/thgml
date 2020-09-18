package com.ssafy.happyhouse.service;

import java.util.List;

import com.ssafy.happyhouse.dto.Shop;
import com.ssafy.happyhouse.util.Search;

public interface ShopService {
	public List<Shop> retrieveShop(Search search);
	public Shop retrieveShopByNo(int no);
	public int getShopCnt(Search search);
}
