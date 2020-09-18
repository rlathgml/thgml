package com.ssafy.happyhouse.controller;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.dto.HouseDeal;
import com.ssafy.happyhouse.dto.Shop;
import com.ssafy.happyhouse.service.ShopService;
import com.ssafy.happyhouse.util.Search;

import io.swagger.annotations.ApiOperation;

//http://localhost:9999/vue/swagger-ui.html
//@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/api/shop")
public class ShopController {

	private static final Logger logger = LoggerFactory.getLogger(ShopController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private static final String LINE = " --------------------------------- ";

	@Autowired
	private ShopService service;

    @ApiOperation(value = "모든 상가의 정보와 페이징을 반환한다.", response = List.class)
	@GetMapping("/all")
	private ResponseEntity<HashMap<String, Object>> retrieveShop(
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "1") int range,
			@RequestParam(required = false, defaultValue = "shop") String keyword,
			@RequestParam(required = false) String word) throws Exception {
    	Search search = new Search();
		search.setKeyword(keyword);
		search.setWord(word);
		
		int listCnt = service.getShopCnt(search);
		
		search.pageInfo(page, range, listCnt);
		List<Shop> list = service.retrieveShop(search);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("pagination", search);
		map.put("list", list);
		
		return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
	}
    
    @ApiOperation(value = "상가 번호에 맞는 정보를 반환한다.", response = HouseDeal.class)
    @GetMapping("/{no}")
    private ResponseEntity<Shop> retrieveShopByNo(@PathVariable int no) throws Exception{
    	return new ResponseEntity<Shop>(service.retrieveShopByNo(no), HttpStatus.OK);
    }

}