package com.dgit.ex01;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dgit.domain.BoardVO;
import com.dgit.domain.Criteria;
import com.dgit.domain.SearchCriteria;
import com.dgit.persistence.BoardDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class BoardDAOTest {
	
	@Autowired
	private BoardDao dao;
	
//	@Test
	public void testCreate() throws Exception{
		BoardVO vo = new BoardVO("제목", "내용", "작성자");
		dao.create(vo);
	}
	
//	@Test
	public void testRead() throws Exception{
		dao.read(1);
	}
	
//	@Test
	public void testUpdate() throws Exception{
		BoardVO vo = new BoardVO(1,"제목2", "내용2");
		dao.update(vo);
	}
	
	
	@Test
	public void testListAll() throws Exception{
		SearchCriteria criteria = new SearchCriteria();
		criteria.setKeyword("제목");
		criteria.setSearchType("t");
		dao.listSearchCount(criteria);
//		dao.listSearch(criteria);
	}
	
//	@Test
	public void testDelete() throws Exception{
		dao.delete(1);
	}
	 
}
