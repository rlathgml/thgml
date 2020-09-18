package com.ssafy.happyhouse.service;

import java.util.List;

import com.ssafy.happyhouse.dto.QnA;

public interface QnAService {
	int check(int qnaNo);
	
	List<QnA> retrieveQnA();
	QnA retrieveQnAByNo(int qnaNo);
	boolean addQnA(QnA qna);
	boolean modifyQnA(QnA qna);
	boolean removeQnA(int qnaNo);
}