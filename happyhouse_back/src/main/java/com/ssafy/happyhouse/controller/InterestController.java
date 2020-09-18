package com.ssafy.happyhouse.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.dto.Interest;
import com.ssafy.happyhouse.service.InterestService;

import io.swagger.annotations.ApiOperation;
import io.swagger.models.Response;

//@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/api/interest")
public class InterestController {
	private static final Logger logger = LoggerFactory.getLogger(InterestController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private static final String LINE = " --------------------------------- ";
	
	@Autowired
	private InterestService service;
	
	@ApiOperation(value = "유저 아이디에 맞는 관심정보를 반환한다.", response = List.class)
	@GetMapping("/{userId}")
	private ResponseEntity<List<Interest>> retrieveInterestByUserId(@PathVariable String userId) throws Exception{
		logger.debug("CALL RETRIEVE_INTEREST_BY_USER_ID METHOD" + LINE + userId);
		return new ResponseEntity<List<Interest>>(service.retrieveInterestByUserId(userId), HttpStatus.OK);
	}
	
	@ApiOperation(value = "전체 관심지역 TOP5를 반환한다.", response = List.class)
	@GetMapping("/top")
	private ResponseEntity<List<Interest>> retrieveInterestTop() throws Exception{
		logger.debug("CALL RETRIEVE_INTEREST_TOP METHOD" + LINE);
		return new ResponseEntity<List<Interest>>(service.retrieveInterestTop(), HttpStatus.OK);
	}
	
	@ApiOperation(value = "관심정보를 등록한다.", response = String.class)
	@PostMapping("/add")
	private ResponseEntity<String> addInterest(@RequestBody Interest interest) throws Exception{
		logger.debug("CALL ADD_INTEREST" + LINE + interest);
		if(service.retrieveInterestCnt(interest.getUserId()) >= 5) {
			return new ResponseEntity<String>("FULL", HttpStatus.OK);
		}
		
		if(service.addInterest(interest)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	}
	
	@ApiOperation(value = "번호에 맞는 관심정보를 삭제한다.", response = String.class)
	@DeleteMapping("/remove/{no}")
	private ResponseEntity<String> removeInterest(@PathVariable int no) throws Exception{
		logger.debug("CALL REMOVE_INTEREST METHOD" + LINE + no);
		if(service.removeInterest(no))
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
	}
}
