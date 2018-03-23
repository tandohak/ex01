package com.dgit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.dgit.domain.MemberVO;
import com.dgit.persistence.MemberDao;

@Service
public class MemberServiceImpl implements MemeberService{
	
	@Autowired
	MemberDao dao;
	
	@Override
	public String getTime() throws Exception {
		return dao.getTime();
	}

	@Override
	public void insertMember(MemberVO vo) throws Exception {
		dao.insertMember(vo);		
	}

	@Override
	public MemberVO readMember(String userid) throws Exception {
		return dao.readMember(userid);
	}

	@Override
	public MemberVO readWithPw(String userid, String userpw) throws Exception {
		return dao.readWithPw(userid, userpw);
	}

	@Override
	public List<MemberVO> listAll() throws Exception {
		return dao.listAll();
	}

	@Override
	public int update(MemberVO vo) throws Exception {
		return dao.update(vo);
	}

	@Override
	public int delete(String id) throws Exception {
		return dao.delete(id);
	}

}
