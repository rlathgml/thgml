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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.dto.Notice;
import com.ssafy.happyhouse.dto.QnA;
import com.ssafy.happyhouse.service.NoticeService;
import com.ssafy.happyhouse.service.QnAService;

import io.swagger.annotations.ApiOperation;

//@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/api/notice")
public class NoticeController {

	private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private static final String LINE = " --------------------------------- ";

	@Autowired
	private NoticeService service;

    @ApiOperation(value = "모든 공지사항을 보여준다.", response = List.class)
    @GetMapping("/all")
    private ResponseEntity<List<Notice>> retrieveAll() throws Exception{
    	logger.debug("CALL RETRIEVE_ALL METHOD" + LINE);
    	return new ResponseEntity<List<Notice>>(service.retrieveAll(), HttpStatus.OK);
    }
    
    @ApiOperation(value = "키워드에 맞는 공지사항을 보여준다.", response = List.class)
    @GetMapping("/search/{searchKeyword}")
    private ResponseEntity<List<Notice>> retrieveByKeyword(@PathVariable String searchKeyword) throws Exception{
    	logger.debug("CALL RETRIEVE_BY_KEYWORD METHOD" + LINE + searchKeyword);
    	return new ResponseEntity<List<Notice>>(service.retrieveByKeyword(searchKeyword), HttpStatus.OK);
    }
    
    @ApiOperation(value = "번호에 맞는 공지사항을 보여준다.", response = Notice.class)
    @GetMapping("/detail/{no}")
    private ResponseEntity<Notice> retrieveByNo(@PathVariable int no) throws Exception{
    	logger.debug("CALL RETRIEVE_BY_NO METHOD" + LINE + no);
    	return new ResponseEntity<Notice>(service.retrieveByNo(no), HttpStatus.OK);
    }

    @ApiOperation(value = "공지사항을 추가한다.", response = String.class)
    @PostMapping("/add")
    private ResponseEntity<String> addNotice(@RequestBody Notice notice) throws Exception{
    	logger.debug("CALL ADD_NOTICE METHOD" + LINE + notice);
    	if(service.addNotice(notice)) {
    		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    	}
    	return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
    }
    
    @ApiOperation(value = "공지사항을 수정한다.", response = String.class)
    @PutMapping("/modify")
    private ResponseEntity<String> modifyNotice(@RequestBody Notice notice) throws Exception{
    	logger.debug("CALL MODIFY_NOTICE METHOD" + LINE + notice);
    	if(service.modifyNotice(notice)) {
    		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    	}
    	return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
    }
    
    @ApiOperation(value = "번호에 맞는 공지사항을 삭제한다.", response = String.class)
    @DeleteMapping("/remove/{no}")
    private ResponseEntity<String> removeNotice(@PathVariable int no) throws Exception{
    	logger.debug("CALL REMOVE_NOTICE METHOD" + LINE + no);
    	if(service.removeNotice(no)) {
    		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    	}
    	return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
    }
}