package com.ssafy.happyhouse.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.ssafy.happyhouse.dto.User;
import com.ssafy.happyhouse.service.JwtService;
import com.ssafy.happyhouse.service.QnAService;
import com.ssafy.happyhouse.service.UserService;

import io.swagger.annotations.ApiOperation;

//http://localhost:9999/vue/swagger-ui.html
//@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/api/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private static final String LINE = " --------------------------------- ";

	@Autowired
	private UserService service;
	
	@Autowired
	private JwtService jwtService;

	@ApiOperation(value = "아이디 중복체크를 한다.", response = String.class)
	@GetMapping(value = "/check/{userId}")
	private ResponseEntity<String> check(@PathVariable String userId) throws Exception{
		logger.debug("CALL CHECK METHOD" + LINE);
		if(service.check(userId)) {
			return new ResponseEntity<String>("duplicate", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
	
    @ApiOperation(value = "회원 가입을 한다.", response = String.class)
    @PostMapping(value = "/signup")
    private ResponseEntity<String> signUp(@RequestBody User user) throws Exception{
    	logger.debug("CALL SIGN_UP METHOD" + LINE + user);
    	if(service.addUser(user)) {
    		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    	}
    	return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "로그인을 한다.", response = List.class)
    @PostMapping(value = "/signin")
    private ResponseEntity<HashMap<String, Object>> signin(@RequestBody User user) throws Exception{
    	logger.debug("CALL SIGN_IN METHOD" + LINE + user);
    	User loginUser = service.login(user);
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	if(loginUser == null) {
    		map.put("message", FAIL);
    		return new ResponseEntity<HashMap<String,Object>>(map, HttpStatus.OK);
    	}
    	String token = jwtService.create("user", loginUser, "user");
    	map.put("Token", token);
    	map.put("user", loginUser);
    	return new ResponseEntity<HashMap<String,Object>>(map, HttpStatus.OK);
    }
    
    @ApiOperation(value = "유저 아이디에 맞는 회원정보를 가져온다.", response = User.class)
    @GetMapping(value = "/{userId}")
    private ResponseEntity<User> retrieveUserByUserId(@PathVariable String userId) throws Exception{
    	logger.debug("CALL RETRIEVE_USER_BY_USERID METHOD" + LINE + userId);
    	User user = service.retrieveUser(userId);
    	if(user != null) {
    		return new ResponseEntity<User>(user, HttpStatus.OK);
    	}
    	return new ResponseEntity<User>(user, HttpStatus.BAD_REQUEST);
    }
    
    @ApiOperation(value = "유저 정보를 수정한다.", response = String.class)
    @PutMapping(value = "/modify")
    private ResponseEntity<String> modifyUser(@RequestBody User user) throws Exception{
    	logger.debug("CALL MODIFY_USER METHOD" + LINE + user);
    	if(service.modifyUser(user)) {
    		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    	}
    	return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
    }
    
    @ApiOperation(value = "유저 비밀번호를 수정한다.", response = String.class)
    @PutMapping(value = "/modifyPwd")
    private ResponseEntity<String> modifyPwd(@RequestBody User user) throws Exception{
    	logger.debug("CALL MODIFY_PWD METHOD" + LINE + user);
    	if(service.modifyUserPwd(user)) {
    		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    	}
    	return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
    }
    
    @ApiOperation(value = "회원탈퇴를 한다.", response = String.class)
    @DeleteMapping(value = "/remove/{userId}")
    private ResponseEntity<String> removeUser(@PathVariable String userId) throws Exception{
    	logger.debug("CALL REMOVE_USER METHOD" + LINE + userId);
    	if(service.removeUser(userId)) {
    		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    	}
    	return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
    }
    
}