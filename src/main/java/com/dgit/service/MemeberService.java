package com.dgit.service;

import java.util.List;

import com.dgit.domain.MemberVO;
import com.dgit.domain.ReplyVO;

public interface MemeberService {
	public String getTime() throws Exception;
	public void insertMember(MemberVO vo)  throws Exception;
	public MemberVO readMember(String userid) throws Exception;
	public MemberVO readWithPw(String userid, String userpw) throws Exception;
	public List<MemberVO> listAll() throws Exception;
	public int update(MemberVO vo) throws Exception;
	public int delete(String id) throws Exception;
}
