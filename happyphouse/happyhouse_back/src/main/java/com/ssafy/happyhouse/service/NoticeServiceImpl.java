package com.ssafy.happyhouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.dao.NoticeDAO;
import com.ssafy.happyhouse.dto.Notice;

@Service
public class NoticeServiceImpl implements NoticeService {
	
	@Autowired
	private NoticeDAO dao;


	@Override
	public List<Notice> retrieveAll() {
		return dao.selectAllNotices();
	}

	@Override
	public List<Notice> retrieveByKeyword(String searchKeyword) {
		return dao.selectAllNoticesByWord(searchKeyword);
	}

	@Override
	public Notice retrieveByNo(int no) {
		return dao.selectNoticeByNo(no);
	}

	@Override
	public boolean addNotice(Notice notice) {
		return dao.insertNotice(notice) == 1;
	}
	@Override
	public boolean removeNotice(int no) {
		return dao.deleteNotice(no) == 1;
	}

	@Override
	public boolean modifyNotice(Notice notice) {
		return dao.updateNotice(notice) == 1;
	}

}
