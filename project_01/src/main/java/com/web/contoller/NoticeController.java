package com.web.contoller;


import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.web.dto.MemberDTO;
import com.web.dto.NoticeDTO;
import com.web.dto.PageRequestDTO;
import com.web.service.MemberService;
import com.web.service.NoticeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/comunitypage/notice")
@RequiredArgsConstructor
@Log4j2
public class NoticeController {
	
	private final NoticeService noticeService;
	private final MemberService memberService;
	
	
	
	@GetMapping("/noticelist")
	public String noticelist(Principal principal,PageRequestDTO pageRequestDTO, Model model) throws Exception {
		log.info("리스트보기");
		
		 
		
		
			MemberDTO memberDto = memberService.getName(principal);
			
			model.addAttribute("memberDto", memberDto);
			model.addAttribute("result", noticeService.getList(pageRequestDTO));
		 
		
		
		return "comunitypage/notice/noticelist";
	}
	
	@GetMapping("/noticewrite")
	public String noticewrite(Principal principal,Model model) {
		MemberDTO memberDto = memberService.getName(principal);
		
		model.addAttribute("memberDto", memberDto);
		return "comunitypage/notice/noticewrite";
	}
	
	@PostMapping("/noticewrite")
	public String postregister(Principal principal,
			NoticeDTO dto, RedirectAttributes redirectAttributes, Model model) throws Exception {
		//템플릿에 reigster에 name과 dto 변수명이 같으면 자동으로 들어옴
		
		
		log.info("등록 >>>"+dto);
		Long nno = noticeService.register(dto);
		log.info("등록 게시번호 >>>"+nno);
		MemberDTO memberDto = memberService.getName(principal);
		
		model.addAttribute("memberDto", memberDto);
		redirectAttributes.addFlashAttribute("msg",nno);
		return "redirect:/comunitypage/notice/noticelist";
	}
	
	@GetMapping("/noticelistdtl")
	public void noticelistdtl(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO,
			Long nno, Model model, Principal principal) throws Exception {
		
		MemberDTO memberDto = memberService.getName(principal);
		
		NoticeDTO noticeDTO = noticeService.getread(nno);
		noticeService.updateVisitCount(nno);
		model.addAttribute("memberDto", memberDto);
		model.addAttribute("dto",noticeDTO);
		

		
	}
	
	@GetMapping("/noticeedit")
	public void noticeedit(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO,
			Long nno, Model model, Principal principal) throws Exception {
		
		MemberDTO memberDto = memberService.getName(principal);
		
		NoticeDTO noticeDTO = noticeService.getread(nno);
		model.addAttribute("memberDto", memberDto);
		model.addAttribute("dto",noticeDTO);
		
	}
	
	
	
	@PostMapping("/noticeedit")
	public String modify(NoticeDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO
			, RedirectAttributes redirectAttributes) throws Exception {
		noticeService.modify(dto);
		redirectAttributes.addAttribute("nno", dto.getNno());
		redirectAttributes.addAttribute("page", requestDTO.getPage());
		redirectAttributes.addAttribute("type", requestDTO.getType());
		redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());
		
		
		return "redirect:/comunitypage/notice/noticelistdtl";
	}
	
	@PostMapping("/remove")
	public String remove(Long nno, RedirectAttributes redirectAttributes) throws Exception {
		noticeService.remove(nno);
		redirectAttributes.addFlashAttribute("msg", nno); //유지시간 짧음
		
		return "redirect:/comunitypage/notice/noticelist";
	}
	
	
	
}
