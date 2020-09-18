package com.ssafy.happyhouse.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.dto.Notice;

public interface NoticeDAO {
	public List<Notice> selectAllNotices();
	public List<Notice> selectAllNoticesByWord(String searchKeyword);
	public Notice selectNoticeByNo(int no);

	public int insertNotice(Notice notice);
	public int deleteNotice(int no);
	public int updateNotice(Notice notice);
}