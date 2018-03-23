package com.dgit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgit.domain.BoardVO;
import com.dgit.domain.Criteria;
import com.dgit.domain.SearchCriteria;
import com.dgit.persistence.BoardDao;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardDao dao;

	@Override
	@Transactional
	public void regist(BoardVO board) throws Exception {
		// TODO Auto-generated method stub
		dao.create(board);
		
		String[] files = board.getFiles();
		
		if(files == null){
			return;
		}
		
		for(String fileName: files){		
			dao.addAttach(fileName, board.getBno());
		}
	}

	@Override
	@Transactional
	public BoardVO read(Integer bno) throws Exception {
		// TODO Auto-generated method stub
			dao.viewcnt(bno);
		BoardVO vo = dao.read(bno);		
		List<String> list = dao.getAttach(bno);
		vo.setFiles(list.toArray(new String[list.size()]));
		
		return vo;
	}

	@Override
	public void modify(BoardVO board) throws Exception {
		// TODO Auto-generated method stub
		dao.update(board);
	}

	@Override
	public void remove(int bno) throws Exception {
		// TODO Auto-generated method stub
		dao.delete(bno);
	}

	@Override
	public List<BoardVO> listAll() throws Exception {
		// TODO Auto-generated method stub
		return dao.listAll();
	}

	@Override
	public List<BoardVO> listCriteria(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return dao.listCriteria(cri);
	}

	@Override
	public int listCountCriteria() throws Exception {
		// TODO Auto-generated method stub
		return dao.countPaging();
	}

	@Override
	public List<BoardVO> listSearchCriteria(SearchCriteria cri) throws Exception {
		// TODO Auto-generated method stub
		return dao.listSearch(cri);
	}

	@Override
	public int listSearchCount(SearchCriteria cri) throws Exception {
		// TODO Auto-generated method stub
		return dao.listSearchCount(cri);
	}

	@Override
	public void viewCount(int bno) throws Exception {
		dao.viewcnt(bno);
	}
	
}







