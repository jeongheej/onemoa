package com.web.contoller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.web.dto.MemberDTO;

import com.web.service.MemberService;


import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ComunityController {
	
	private final MemberService memberService;
	
	
	
	
	@GetMapping("/personalguide")
	public String personalguide(Principal principal, Model model) {
		MemberDTO memberDto = memberService.getName(principal);
		
		model.addAttribute("memberDto", memberDto);
		return "comunitypage/guide/personalguide";
	}
	
	@GetMapping("/hospitalguide")
	public String hospitalguide(Principal principal, Model model) {
		
		MemberDTO memberDto = memberService.getName(principal);
		
		model.addAttribute("memberDto", memberDto);
		
		return "comunitypage/guide/hospitalguide";
	}
	
}
