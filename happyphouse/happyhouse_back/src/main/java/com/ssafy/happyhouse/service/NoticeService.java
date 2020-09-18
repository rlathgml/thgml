package com.ssafy.happyhouse.service;

import java.util.List;

import com.ssafy.happyhouse.dto.Notice;

public interface NoticeService {

	public List<Notice> retrieveAll() ;
	public List<Notice> retrieveByKeyword(String searchKeyword) ;
	public Notice retrieveByNo(int no) ;

	public boolean addNotice(Notice notice) ;

	public boolean removeNotice(int no) ;

	public boolean modifyNotice(Notice notice) ;

}
