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
import com.web.dto.ReviewDTO;
import com.web.service.MemberService;
import com.web.service.NoticeService;
import com.web.service.ReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/comunitypage/review")
@RequiredArgsConstructor
@Log4j2
public class ReviewController {
	
	
	private final ReviewService reviewService;
	private final MemberService memberService;
	
	
	@GetMapping("/reviewlist")
	public String reviewlist(Principal principal, PageRequestDTO pageRequestDTO, Model model) throws Exception {
		log.info("리스트보기");
		
		MemberDTO memberDto = memberService.getName(principal);
		
		model.addAttribute("memberDto", memberDto);
			
		
		model.addAttribute("result", reviewService.getList(pageRequestDTO));
		
		return "comunitypage/review/reviewlist";
	}
	
	@GetMapping("/reviewwrite")
	public String reviewwrite(Principal principal, Model model) {
		
		MemberDTO memberDto = memberService.getName(principal);
		
		model.addAttribute("memberDto", memberDto);

		
		return "comunitypage/review/reviewwrite";
	}
	
	@PostMapping("/reviewwrite")
	public String reviewregister(ReviewDTO dto, 
			RedirectAttributes redirectAttributes, Model model) throws Exception {
	
		
			
		
		log.info("등록 >>>"+dto);
		Long rno = reviewService.register(dto);
		log.info("등록 게시번호 >>>"+rno);
		redirectAttributes.addFlashAttribute("msg",rno);
		
		
		return "redirect:/comunitypage/review/reviewlist";
	}
	
	@GetMapping("/reviewlistdtl")
	public void reviewlistdtl(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO,
			Long rno, Model model,Principal principal) throws Exception {
		
		
		MemberDTO memberDto = memberService.getName(principal);
		
		model.addAttribute("memberDto", memberDto);
			
		
		ReviewDTO reviewDTO = reviewService.getread(rno);
		reviewService.updateVisitCount(rno);
		model.addAttribute("dto",reviewDTO);
		

		
	}
	
	@GetMapping("/reviewedit")
	public void reviewedit(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO,
			Long rno, Model model,Principal principal) throws Exception {
		
		
		MemberDTO memberDto = memberService.getName(principal);
		
		model.addAttribute("memberDto", memberDto);
		
		ReviewDTO reviewDTO = reviewService.getread(rno);
		
		model.addAttribute("dto",reviewDTO);
		
	}
	
	
	
	@PostMapping("/reviewedit")
	public String modify(ReviewDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO
			, RedirectAttributes redirectAttributes) throws Exception {
		reviewService.modify(dto);
		redirectAttributes.addAttribute("rno", dto.getRno());
		redirectAttributes.addAttribute("page", requestDTO.getPage());
		redirectAttributes.addAttribute("type", requestDTO.getType());
		redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());
		
		
		return "redirect:/comunitypage/review/reviewlistdtl";
	}
	
	@PostMapping("/remove")
	public String remove(Long rno, RedirectAttributes redirectAttributes) throws Exception {
		reviewService.remove(rno);
		redirectAttributes.addFlashAttribute("msg", rno);
		
		return "redirect:/comunitypage/review/reviewlist";
	}
}
