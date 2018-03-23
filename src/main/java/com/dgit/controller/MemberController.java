package com.dgit.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dgit.domain.MemberVO;
import com.dgit.service.MemeberService;

@RestController
@RequestMapping("/member/*")
public class MemberController {

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	private MemeberService serivce;

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<String> register(@RequestBody MemberVO vo) {
		ResponseEntity<String> entity = null;

		try {
			serivce.insertMember(vo);
			entity = new ResponseEntity<>("success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
		}

		return entity;
	}

	@RequestMapping(value="/", method = RequestMethod.GET)
	public ResponseEntity<List<MemberVO>> list() {
		ResponseEntity<List<MemberVO>> entity = null;

		try {
			List<MemberVO> list = serivce.listAll();
			entity = new ResponseEntity<>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return entity;
	}
	
	@RequestMapping(value="/{userid}", method={RequestMethod.PATCH,RequestMethod.PUT})
	public ResponseEntity<String> modify(@PathVariable("userid") String userid, @RequestBody MemberVO vo){
		ResponseEntity<String> entity = null;

		try {
			vo.setUserid(userid);
			serivce.update(vo);
			entity = new ResponseEntity<>("success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
		}

		return entity;	
	}
	 
	@RequestMapping(value="/{userid}", method=RequestMethod.DELETE)
	public ResponseEntity<String> remove(@PathVariable("userid") String userid){
		ResponseEntity<String> entity = null;

		try {
			serivce.delete(userid);
			entity = new ResponseEntity<>("success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
		}

		return entity;	
	}
}
