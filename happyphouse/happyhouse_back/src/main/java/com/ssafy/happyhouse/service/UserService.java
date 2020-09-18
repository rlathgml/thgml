package com.ssafy.happyhouse.service;

import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.dto.User;

@Service
public interface UserService {
	public boolean check(String user);
	public User retrieveUser(String userId);
	public User login(User user);
	public boolean addUser(User user);
	public boolean modifyUser(User user);
	public boolean modifyUserPwd(User user);
	public boolean removeUser(String userId);
}
