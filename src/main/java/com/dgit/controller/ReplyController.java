package com.dgit.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dgit.domain.Criteria;
import com.dgit.domain.PageMaker;
import com.dgit.domain.ReplyVO;
import com.dgit.service.ReplyService;

@RestController
@RequestMapping("/replies")
public class ReplyController {
	
	@Autowired
	private ReplyService service;
	
	@RequestMapping(value="/",method=RequestMethod.POST)
	public ResponseEntity<String> register(@RequestBody ReplyVO vo){
		ResponseEntity<String> entity = null;
		
		try{
			service.addReply(vo);
			entity = new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
		}catch(Exception e){
			e.printStackTrace();
			entity = new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);//400 error
		}
		
		return entity;
	}
	
	@RequestMapping(value="/all/{bno}",method=RequestMethod.GET)
	public ResponseEntity<List<ReplyVO>> list(@PathVariable("bno") int bno){
		ResponseEntity<List<ReplyVO>> entity = null;
		
		try{
			List<ReplyVO> list = service.listReply(bno);
			entity = new ResponseEntity<List<ReplyVO>>(list,HttpStatus.OK);
		}catch(Exception e){
			e.printStackTrace();
			entity = new ResponseEntity<List<ReplyVO>>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	@RequestMapping(value="/{rno}",method={RequestMethod.PUT,RequestMethod.PATCH})
	public ResponseEntity<String> update(@PathVariable("rno")int rno,@RequestBody ReplyVO vo){
		ResponseEntity<String> entity = null;
		 
			try {   
				vo.setRno(rno); 
				service.modifyReply(vo);
				entity = new ResponseEntity<String>("success",HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				entity = new ResponseEntity<String>("fail",HttpStatus.BAD_REQUEST);
			}
			 
			return entity;
	}
	
	@RequestMapping(value="/{bno}/{page}",method={RequestMethod.GET})
	public ResponseEntity<Map<String,Object>> listPage(@PathVariable("bno")int bno, @PathVariable("page") int page){
		
		//List<ReplyVO>, pageMaker
	
		ResponseEntity<Map<String,Object>> entity = null;
		try {
			
			HashMap<String,Object> map = new HashMap<>();
			
			Criteria cri = new Criteria();
			cri.setPage(page); 
			List<ReplyVO> list = service.listPage(bno, cri);
			map.put("list",list);
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCri(cri);
			pageMaker.setTotalCount(service.count(bno));
			map.put("pageMaker",pageMaker);
			
			entity = new ResponseEntity<>(map,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	@RequestMapping(value="/{rno}",method=RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable("rno")int rno){
		ResponseEntity<String> entity = null;
		
		try {
			service.removeReply(rno);
			entity = new ResponseEntity<String>("success",HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>("fail",HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
}
