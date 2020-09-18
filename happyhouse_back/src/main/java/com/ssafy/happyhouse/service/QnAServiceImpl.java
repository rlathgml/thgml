package com.ssafy.happyhouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.dao.QnADAO;
import com.ssafy.happyhouse.dto.QnA;

@Service
public class QnAServiceImpl implements QnAService {
	@Autowired
	private QnADAO dao;
	
	@Override
	public int check(int qnaNo) {
		// TODO Auto-generated method stub
		return dao.existQnA(qnaNo);
	}
	
	@Override
	public List<QnA> retrieveQnA() {
		return dao.selectQnA();
	}
	
	@Override
	public QnA retrieveQnAByNo(int qnaNo) {
		// TODO Auto-generated method stub
		return dao.selectQnAByNo(qnaNo);
	}

	@Override
	public boolean addQnA(QnA qna) {
		// TODO Auto-generated method stub
		return dao.insertQnA(qna) == 1;
	}
	
	@Override
	public boolean modifyQnA(QnA qna) {
		// TODO Auto-generated method stub
		return dao.updateQnA(qna) == 1;
	}
	
	@Override
	public boolean removeQnA(int qnaNo) {
		// TODO Auto-generated method stub
		return dao.deleteQnA(qnaNo) == 1;
	}
	
}
