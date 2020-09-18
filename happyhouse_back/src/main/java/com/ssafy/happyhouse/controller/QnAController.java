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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.dto.QnA;
import com.ssafy.happyhouse.service.QnAService;

import io.swagger.annotations.ApiOperation;

//@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/api/qna")
public class QnAController {

	private static final Logger logger = LoggerFactory.getLogger(QnAController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private static final String LINE = " --------------------------------- ";

	@Autowired
	private QnAService service;

	@ApiOperation(value = "모든 질문 게시판 정보를 반환한다.", response = List.class)
	@GetMapping("/all")
	public ResponseEntity<List<QnA>> retrieveQnA() throws Exception {
		logger.debug("CALL RETRIEVE_QNA METHOD");
		return new ResponseEntity<List<QnA>>(service.retrieveQnA(), HttpStatus.OK);
	}

	@ApiOperation(value = "질문 게시판의 상세 정보를 반환한다.", response = QnA.class)
	@GetMapping("/{qnaNo}")
	public ResponseEntity<QnA> retrieveQnAByNo(@PathVariable int qnaNo) throws Exception {
		logger.debug("CALL RETRIEVE_QNA_BY_NO METHOD" + LINE + qnaNo);
		return new ResponseEntity<QnA>(service.retrieveQnAByNo(qnaNo), HttpStatus.OK);
	}

	@ApiOperation(value = "질문 게시판에 질문을 추가한다.", response = String.class)
	@PostMapping("/add")
	public ResponseEntity<String> addQnA(@RequestBody QnA qna) throws Exception {
		logger.debug("CALL ADD_QNA METHOD" + LINE + qna);
		if (service.check(qna.getQnaNo()) > 0) {
			return new ResponseEntity<String>("Duplicate", HttpStatus.OK);
		}
		if (service.addQnA(qna)) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "질문 게시판의 질문을 수정한다.", response = String.class)
	@PutMapping("/modify")
	public ResponseEntity<String> modifyQnA(@RequestBody QnA qna) throws Exception {
		logger.debug("CALL MODIFY_QNA METHOD" + LINE + qna);
		if (service.modifyQnA(qna))
			return new ResponseEntity<String>("success", HttpStatus.OK);
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "질문 게시판의 질문을 삭제한다.", response = String.class)
	@DeleteMapping("/remove/{qnaNo}")
	public ResponseEntity<String> removeQnA(@PathVariable int qnaNo) throws Exception {
		logger.debug("CALL REMOVE_QNA METHOD" + LINE + qnaNo);
		if (service.removeQnA(qnaNo))
			return new ResponseEntity<String>("success", HttpStatus.OK);
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}

}