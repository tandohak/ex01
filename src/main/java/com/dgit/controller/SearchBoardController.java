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
import com.dgit.domain.SearchCriteria;
import com.dgit.service.BoardService;

@Controller
@RequestMapping("/sboard/*")
public class SearchBoardController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private BoardService service;
	
	@RequestMapping("/listPage")
	private void listPage(Model model,@ModelAttribute("cri")SearchCriteria cri) throws Exception{
		logger.info("listPage");
		
		List<BoardVO>  list= service.listSearchCriteria(cri);
		int pageCount = service.listSearchCount(cri);
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(pageCount);
		
		model.addAttribute("list",list);
		model.addAttribute("pageMaker",pageMaker);
	}
	
	@RequestMapping(value="/readPage",method=RequestMethod.GET)
	private void readPage(int bno,@ModelAttribute("cri") SearchCriteria cri,String mode,Model model) throws Exception{
		logger.info("readPage");
		BoardVO vo = service.read(bno);
		
		if(mode == null){
			service.viewCount(bno); 
		}
		
		model.addAttribute("boardVO",vo);
	}
	
	@RequestMapping(value="/register", method=RequestMethod.GET)
	private void registerGet() throws Exception{
		logger.info("registerGet");
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	private String registerPost(BoardVO vo) throws Exception{
		logger.info("registerPost");
		service.regist(vo);
		
		return "redirect:/sboard/listPage";
	}
	
	@RequestMapping(value="/removePage",method=RequestMethod.POST)
	private String removePage(int bno,SearchCriteria cri,RedirectAttributes rttr) throws Exception{
		logger.info("removePage");
		rttr.addAttribute("page",cri.getPage());
		rttr.addAttribute("pagePagetNum",cri.getPerPageNum());
		rttr.addAttribute("keyword",((SearchCriteria)cri).getKeyword());
		rttr.addAttribute("searchType",((SearchCriteria)cri).getSearchType());
		service.remove(bno); 
		return "redirect:/sboard/listPage";
	}
	
	@RequestMapping(value="/modifyPage", method=RequestMethod.GET)
	private void modifyPageGet(int bno,Model model,@ModelAttribute("cri") SearchCriteria cri) throws Exception{
		logger.info("modifyGet");
		BoardVO vo = service.read(bno);
		model.addAttribute("boardVO",vo);
	}
	
	@RequestMapping(value="/modifyPage", method=RequestMethod.POST)
	private String modifyPagePost(BoardVO vo,SearchCriteria cri,RedirectAttributes rttr) throws Exception{
		logger.info("modifyPagePost");
		rttr.addAttribute("bno",vo.getBno());
		rttr.addAttribute("page",cri.getPage());
		rttr.addAttribute("pagePagetNum",cri.getPerPageNum());
		rttr.addAttribute("keyword",((SearchCriteria)cri).getKeyword());
		rttr.addAttribute("searchType",((SearchCriteria)cri).getSearchType());
		rttr.addAttribute("mode","modify");  
		service.modify(vo); 
		return "redirect:/sboard/readPage";
	} 
}
