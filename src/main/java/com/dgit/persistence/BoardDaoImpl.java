package com.dgit.persistence;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dgit.domain.BoardVO;
import com.dgit.domain.Criteria;
import com.dgit.domain.SearchCriteria;

@Repository
public class BoardDaoImpl implements BoardDao {

	private static final String namespace = "com.dgit.mapper.BoardMapper";
	
	@Autowired
	private SqlSession session; 
	
	@Override
	public void create(BoardVO vo) throws Exception {
		// TODO Auto-generated method stub
		session.insert(namespace + ".create", vo);
	}

	@Override
	public BoardVO read(Integer bno) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace + ".read", bno);
	}

	@Override
	public void update(BoardVO vo) throws Exception {
		// TODO Auto-generated method stub
		session.update(namespace + ".update", vo);
	}

	@Override
	public void delete(Integer bno) throws Exception {
		// TODO Auto-generated method stub
		session.delete(namespace + ".delete", bno);
	}

	@Override
	public List<BoardVO> listAll() throws Exception {
		// TODO Auto-generated method stub
		return session.selectList(namespace + ".listAll");
	}

	@Override
	public List<BoardVO> listPage(int page) throws Exception {
		// TODO Auto-generated method stub
		if(page <= 0){
			page = 1;
		}
		page = (page - 1) * 10;
		
		return session.selectList(namespace + ".listPage", page);
	}

	@Override
	public List<BoardVO> listCriteria(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return session.selectList(namespace + ".listCriteria", cri);
	}

	@Override
	public int countPaging() throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace +".countPaging");
	}

	@Override
	public List<BoardVO> listSearch(SearchCriteria cri) throws Exception {
		// TODO Auto-generated method stub
		return session.selectList(namespace +".listSearch", cri);
	}

	@Override
	public int listSearchCount(SearchCriteria cri) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace +".listSearchcount", cri);
	}

	@Override
	public void viewcnt(int bno) throws Exception {
		// TODO Auto-generated method stub
		session.update(namespace + ".viewcnt", bno);
	}

	@Override
	public void updateReplyCnt(int bno, int amount) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String, Object> map = new HashMap<>();
		map.put("bno", bno);
		map.put("amount", amount);
		session.update(namespace + ".updateReplyCnt", map);
	}

	@Override
	public void addAttach(String fullName, int bno) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String, Object> map = new HashMap<>();
		map.put("fullName", fullName);
		map.put("bno", bno);
		
		session.insert(namespace + ".addAttach", map);
	}

	@Override
	public List<String> getAttach(int bno) throws Exception {
		// TODO Auto-generated method stub
		return session.selectList(namespace + ".getAttach", bno);
	}
	
	

}





