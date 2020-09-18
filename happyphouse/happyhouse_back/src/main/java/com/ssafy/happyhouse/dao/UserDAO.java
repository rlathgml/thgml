package com.ssafy.happyhouse.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.dto.User;


public interface UserDAO {
	public int check(String userId);
	public User selectUser(String userId);
	public User selectUserLogin(User user);
	public int insertUser(User user);
	public int updateUser(User user);
	public int updateUserPwd(User user);
	public int deleteUser(String userId);
}
