package com.ssafy.happyhouse.dao;

import java.util.List;

import com.ssafy.happyhouse.dto.QnA;

public interface QnADAO {
	int existQnA(int qnaNo);
	
	List<QnA> selectQnA();
	QnA selectQnAByNo(int qnaNo);
	int insertQnA(QnA qna);
	int updateQnA(QnA qna);
	int deleteQnA(int qnaNo);
}
