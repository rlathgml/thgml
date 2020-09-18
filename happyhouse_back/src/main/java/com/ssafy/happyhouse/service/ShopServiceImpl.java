package com.ssafy.happyhouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.dao.ShopDAO;
import com.ssafy.happyhouse.dto.Shop;
import com.ssafy.happyhouse.util.Search;

@Service
public class ShopServiceImpl implements ShopService {
	
	@Autowired
	private ShopDAO dao;
	
	@Override
	public List<Shop> retrieveShop(Search search) {
		// TODO Auto-generated method stub
		return dao.selectShop(search);
	}
	
	@Override
	public Shop retrieveShopByNo(int no) {
		// TODO Auto-generated method stub
		return dao.selectShopByNo(no);
	}
	
	@Override
	public int getShopCnt(Search search) {
		// TODO Auto-generated method stub
		return dao.selectShopCnt(search);
	}
}
