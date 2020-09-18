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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.dto.HouseDeal;
import com.ssafy.happyhouse.dto.SearchVO;
import com.ssafy.happyhouse.service.HouseService;
import com.ssafy.happyhouse.util.Search;

import io.swagger.annotations.ApiOperation;

//http://localhost:9999/vue/swagger-ui.html
//@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/api/house")
public class HouseController {

	private static final Logger logger = LoggerFactory.getLogger(HouseController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private static final String LINE = " --------------------------------- ";
	private static int listCnt = 0;
	private static int prevListCnt = 0;

	@Autowired
	private HouseService service;

    @ApiOperation(value = "모든 주택의 정보와 페이징을 반환한다.", response = List.class)
	@GetMapping("/all")
	private ResponseEntity<HashMap<String, Object>> retrieveHouseDeal(@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "1") int range,
			@RequestParam(required = false, defaultValue = "apt") String keyword,
			@RequestParam(required = false) String word) throws Exception {
    	logger.debug("CALL RETRIEVE_HOUSE_DEAL METHOD" + LINE);
    	
    	Search search = new Search();
		search.setKeyword(keyword);
		search.setWord(word);
		
		if(listCnt == 0 || listCnt != prevListCnt) {
			listCnt = service.getHouseDealCnt(search);
			prevListCnt = listCnt;
		}
		
		search.pageInfo(page, range, listCnt);
		List<HouseDeal> list = service.retrieveHouseDeal(search);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("pagination", search);
		map.put("list", list);
		
		return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
	}
    
    @ApiOperation(value = "주택 번호에 맞는 정보를 반환한다.", response = HouseDeal.class)
    @GetMapping("/{no}")
    private ResponseEntity<HouseDeal> retrieveHouseDealByNo(@PathVariable int no) throws Exception{
    	return new ResponseEntity<HouseDeal>(service.retrieveHouseDealByNo(no), HttpStatus.OK);
    }

    @ApiOperation(value = "관심도 상위 5개의 주택 정보를 반환한다.", response = List.class)
    @GetMapping("/top")
    private ResponseEntity<List<HouseDeal>> retrieveHouseDealTop() throws Exception{
    	return new ResponseEntity<List<HouseDeal>>(service.retrieveHouseDealTop(), HttpStatus.OK);
    }
    
}