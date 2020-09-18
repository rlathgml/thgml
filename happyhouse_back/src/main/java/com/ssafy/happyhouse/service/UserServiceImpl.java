package com.ssafy.happyhouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.dao.UserDAO;
import com.ssafy.happyhouse.dto.User;


@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDAO dao;
	
	@Override
	public boolean check(String user) {
		// TODO Auto-generated method stub
		return dao.check(user) == 1;
	}
	
	@Override
	public User retrieveUser(String userId) {
		// TODO Auto-generated method stub
		return dao.selectUser(userId);
	}
	
	@Override
	public User login(User user) {
		// TODO Auto-generated method stub
		return dao.selectUserLogin(user);
	}
	
	@Override
	public boolean addUser(User user) {
		// TODO Auto-generated method stub
		return dao.insertUser(user) == 1;
	}
	
	@Override
	public boolean modifyUser(User user) {
		// TODO Auto-generated method stub
		return dao.updateUser(user) == 1;
	}
	
	@Override
	public boolean modifyUserPwd(User user) {
		// TODO Auto-generated method stub
		return dao.updateUserPwd(user) == 1;
	}
	
	@Override
	public boolean removeUser(String userId) {
		// TODO Auto-generated method stub
		return dao.deleteUser(userId) == 1;
	}
}
