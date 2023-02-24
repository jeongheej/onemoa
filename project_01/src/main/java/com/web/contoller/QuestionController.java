package com.web.contoller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.web.dto.MemberDTO;
import com.web.dto.NoticeDTO;
import com.web.dto.PageRequestDTO;
import com.web.dto.QuestionDTO;
import com.web.dto.QuestionReplyDTO;
import com.web.dto.ReviewDTO;
import com.web.entity.QuestionReply;
import com.web.service.MemberService;
import com.web.service.NoticeService;
import com.web.service.QuestionReplyService;
import com.web.service.QuestionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/comunitypage/question")
@RequiredArgsConstructor
@Log4j2
public class QuestionController {
	
	private final MemberService memberService;
	private final QuestionService questionService;
	private final QuestionReplyService replyService;
	
	
	@GetMapping("/questionwrite")
	public String questionwrite(Principal principal,Model model) {
		
		MemberDTO memberDto = memberService.getName(principal);
		
		model.addAttribute("memberDto", memberDto);
	
		return "comunitypage/question/questionwrite";
	}
	
	@PostMapping("/questionwrite")
	public String questionregister(Principal principal,
			QuestionDTO dto, RedirectAttributes redirectAttributes, Model model) throws Exception {
		//템플릿에 reigster에 name과 dto 변수명이 같으면 자동으로 들어옴
		
		
		log.info("등록 >>>"+dto);
		Long qno = questionService.register(dto);
		log.info("등록 게시번호 >>>"+qno);
		MemberDTO memberDto = memberService.getName(principal);
		
		model.addAttribute("memberDto", memberDto);
		redirectAttributes.addFlashAttribute("msg",qno);
		return "redirect:/comunitypage/question/questionlist";
	}
	
	@GetMapping("/questionlist")
	public String questionList(Principal principal, Model model,PageRequestDTO pageRequestDTO) {
		
		MemberDTO memberDto = memberService.getName(principal);
		
		model.addAttribute("memberDto", memberDto);
			
		
		model.addAttribute("result", questionService.getList(pageRequestDTO));
	
		return "comunitypage/question/questionlist";
	}
	
	@GetMapping("/questionlistdtl")
	public void questionlistdtl(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO,
			Long qno, Model model,Principal principal) throws Exception {
		
		
		MemberDTO memberDto = memberService.getName(principal);
		
		List<QuestionReplyDTO> questionReplyDTO  = replyService.getList(qno);
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>" + questionReplyDTO);
		
		model.addAttribute("memberDto", memberDto);
			
		
		QuestionDTO questionDTO = questionService.getread(qno);
		questionService.updateVisitCount(qno);
		model.addAttribute("dto",questionDTO);
		model.addAttribute("questionReplyDTO",questionReplyDTO);
		
	}
	
	@PostMapping("/questionlistdtl")
	public void questionlistdtlpost(@ModelAttribute("requestDTO") PageRequestDTO requestDTO,
			Long qno, Model model,Principal principal,RedirectAttributes redirectAttributes) throws Exception {
		
		
		MemberDTO memberDto = memberService.getName(principal);
		
		model.addAttribute("memberDto", memberDto);
			
		List<QuestionReplyDTO> questionReplyDTO  = replyService.getList(qno);
		model.addAttribute("questionReplyDTO",questionReplyDTO);
		
		
		QuestionDTO questionDTO = questionService.getread(qno);
		questionService.updateVisitCount(qno);
		model.addAttribute("dto",questionDTO);
		redirectAttributes.addAttribute("qno", questionDTO.getQno());
		redirectAttributes.addAttribute("page", requestDTO.getPage());
		redirectAttributes.addAttribute("type", requestDTO.getType());
		redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());
		
	}
	

	
	@GetMapping("/questionlistpw")
	public void questionlistpw(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO,
			Long qno, Model model,Principal principal) throws Exception {
		
		
		MemberDTO memberDto = memberService.getName(principal);
		
		model.addAttribute("memberDto", memberDto);
			
		
		QuestionDTO questionDTO = questionService.getread(qno);
		questionService.updateVisitCount(qno);
		model.addAttribute("dto",questionDTO);
		
	}
	
	 @PostMapping("/questionlistpw")
	 public @ResponseBody Map<String, Boolean> pw_find(Long qno, String qpassword){
	    Map<String,Boolean> qpasswordcheck = new HashMap<>();
	    boolean qpwCheck = questionService.qpasswordcheck(qno,qpassword);

	    System.out.println(qpwCheck);
	    qpasswordcheck.put("check", qpwCheck);
	   
	    return qpasswordcheck;
	 }
	 
	 @PostMapping("/remove")
		public String remove(Long qno, RedirectAttributes redirectAttributes) {
			questionService.removeWithReplies(qno);
			redirectAttributes.addFlashAttribute("msg", qno); //유지시간 짧음
			
			return "redirect:/comunitypage/question/questionlist";
		}
	 
	 @GetMapping("/questionedit")
		public void reviewedit(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO,
				Long qno, Model model,Principal principal) throws Exception {
			
			
			MemberDTO memberDto = memberService.getName(principal);
			
			model.addAttribute("memberDto", memberDto);
			
			QuestionDTO questionDTO = questionService.getread(qno);
			
			model.addAttribute("dto",questionDTO);
			
		}
		
		
		
		@PostMapping("/questionedit")
		public String modify(QuestionDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO
				, RedirectAttributes redirectAttributes) throws Exception {
			questionService.modify(dto);
			redirectAttributes.addAttribute("qno", dto.getQno());
			redirectAttributes.addAttribute("page", requestDTO.getPage());
			redirectAttributes.addAttribute("type", requestDTO.getType());
			redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());
			
			
			return "redirect:/comunitypage/question/questionlistdtl";
		}

}
