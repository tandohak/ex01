package com.dgit.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dgit.domain.BoardVO;
import com.dgit.domain.Criteria;
import com.dgit.domain.PageMaker;
import com.dgit.service.BoardService;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private BoardService service;
	
	@RequestMapping("/listAll")
	private void listAll(Model model) throws Exception{
		logger.info("listAll");
		
		List<BoardVO>  list= service.listAll();
		model.addAttribute("list",list);
	}
	
	@RequestMapping("/listCri")
	private void listCri(Model model,Criteria cri) throws Exception{
		logger.info("listCri");
		
		List<BoardVO>  list= service.listCriteria(cri);
		model.addAttribute("list",list);
	}
	
	@RequestMapping("/listPage")
	private void listPage(Model model,Criteria cri) throws Exception{
		logger.info("listPage");
		
		List<BoardVO>  list= service.listCriteria(cri);
		int pageCount = service.listCountCriteria();
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(pageCount);
		
		model.addAttribute("list",list); 
		model.addAttribute("pageMaker",pageMaker);  
	}
	
	
	@RequestMapping(value="/register", method=RequestMethod.GET)
	private void registerGet() throws Exception{
		logger.info("registerGet");  
	}  
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	private String registerPost(BoardVO vo) throws Exception{
		logger.info("registerPost");
		service.regist(vo);
		
		return "redirect:/board/listAll";
	}
	
	@RequestMapping(value="/read",method=RequestMethod.GET)
	private void read(int bno,Model model) throws Exception{
		logger.info("read");
		BoardVO vo = service.read(bno);
		model.addAttribute("boardVO",vo);
	}
	
	@RequestMapping(value="/readPage",method=RequestMethod.GET)
	private void readPage(int bno,@ModelAttribute("cri") Criteria cri,String mode,Model model) throws Exception{
		logger.info("readPage");
		BoardVO vo = service.read(bno);
		if(mode == null){
			service.viewCount(bno);
		}
		
		model.addAttribute("boardVO",vo);
	}
	
	@RequestMapping(value="/remove",method=RequestMethod.POST)
	private String remove(int bno) throws Exception{
		logger.info("remove");
		service.remove(bno);  
		return "redirect:/board/listAll";
	}
	
	@RequestMapping(value="/removePage",method=RequestMethod.POST)
	private String removePage(int bno,Criteria cri,RedirectAttributes rttr) throws Exception{
		logger.info("removePage");
		rttr.addAttribute("page",cri.getPage());
		rttr.addAttribute("pagePagetNum",cri.getPerPageNum());
		service.remove(bno);
		return "redirect:/board/listPage";
	}
	  
	@RequestMapping(value="/modify", method=RequestMethod.GET)
	private void modifyGet(int bno,Model model) throws Exception{
		logger.info("modifyGet");
		BoardVO vo = service.read(bno);
		model.addAttribute("boardVO",vo);
	}
	
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	private String modifyPost(BoardVO vo) throws Exception{
		logger.info("modifyPost");
		service.modify(vo);
		
		return "redirect:/board/listAll";
	}
	
	@RequestMapping(value="/modifyPage", method=RequestMethod.GET)
	private void modifyPageGet(int bno,Model model,@ModelAttribute("cri") Criteria cri) throws Exception{
		logger.info("modifyGet");
		BoardVO vo = service.read(bno);
		model.addAttribute("boardVO",vo);
	}
	
	@RequestMapping(value="/modifyPage", method=RequestMethod.POST)
	private String modifyPagePost(BoardVO vo,Criteria cri,RedirectAttributes rttr) throws Exception{
		logger.info("modifyPagePost");
		rttr.addAttribute("bno",vo.getBno());
		rttr.addAttribute("page",cri.getPage());
		rttr.addAttribute("pagePagetNum",cri.getPerPageNum());
		rttr.addAttribute("mode","modify");
		service.modify(vo);
		return "redirect:/board/readPage";
	}
}
