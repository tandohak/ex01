package com.dgit.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dgit.domain.MemberVO;

@Repository
public class MemberDaoImpl implements MemberDao {

	private static final String namespace = "com.dgit.mapper.MemberMapper.";
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public String getTime() throws Exception {
		return sqlSession.selectOne(namespace + "getTime");
	}

	@Override
	public void insertMember(MemberVO vo) throws Exception {
		sqlSession.insert(namespace + "insertMember", vo);
	}

	@Override
	public MemberVO readMember(String userid) throws Exception {
		return sqlSession.selectOne(namespace + "readMember", userid);
	}

	@Override
	public MemberVO readWithPw(String userid, String userpw) throws Exception {
		Map<String,Object> map = new HashMap<>();
		map.put("userid", userid);
		map.put("userpw", userpw);
	
		return sqlSession.selectOne(namespace + "readWithPw",map);
	}

	@Override
	public List<MemberVO> listAll() throws Exception {
		return sqlSession.selectList(namespace+"listAll");
	}

	@Override
	public int update(MemberVO vo) throws Exception {
		return sqlSession.update(namespace+"update",vo);
	}

	@Override
	public int delete(String id) throws Exception {
		return sqlSession.delete(namespace+"delete",id);
	}


}
