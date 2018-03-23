package com.dgit.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dgit.domain.LoginDTO;
import com.dgit.domain.MemberVO;
import com.dgit.service.MemeberService;

@Controller
@RequestMapping("/user")
public class UserController {       

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private MemeberService service;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public void loginGet() {
	}

	@RequestMapping(value = "/loginPost", method = RequestMethod.POST)
	public void loginPost(LoginDTO dto,Model model) throws Exception {
		logger.info("[loginPOST] ---------------- ");
		logger.info(dto.toString());

		MemberVO vo = service.readWithPw(dto.getUserid(), dto.getUserpw());
			
		if(vo == null){
			logger.info("user 없음........");
			logger.info("loginPOST return........");
			return;
		}
		
		dto.setUsername(vo.getUsername());
		dto.setUserpw("");
		
		model.addAttribute("loginDto",dto);
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET) 
	public String logoutGet(String logout,HttpSession session) throws Exception {
		logger.info("[logoutGet] ---------------- ");
		
		if(logout == null){
			logger.info("user 없음........");
			logger.info("loginPOST return........");
			session.invalidate();
		} 
		
		return "redirect:/";
	}

}
