package com.dgit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgit.domain.Criteria;
import com.dgit.domain.ReplyVO;
import com.dgit.persistence.BoardDao;
import com.dgit.persistence.ReplyDAO;

@Service
public class ReplyServiceImpl implements ReplyService {
	
	
	@Autowired
	private ReplyDAO dao;
	
	@Autowired
	private BoardDao boardDao;
	
	@Override
	public List<ReplyVO> listReply(int bno) throws Exception {
		return dao.list(bno);
	}

	@Override
	@Transactional
	public void addReply(ReplyVO vo) throws Exception {
		dao.create(vo);
		boardDao.updateReplyCnt(vo.getBno(), 1);
	}

	@Override
	public void modifyReply(ReplyVO vo) throws Exception {
		dao.update(vo);
	}

	@Override
	@Transactional
	public void removeReply(int rno) throws Exception {
		int bno = dao.getBno(rno);
		dao.delete(rno);
		boardDao.updateReplyCnt(bno, -1);
	}

	@Override
	public int count(int bno) throws Exception {
		return dao.count(bno);
	}

	@Override
	public List<ReplyVO> listPage(int bno, Criteria cri) throws Exception {
		return dao.listPage(bno, cri);
	}

}
