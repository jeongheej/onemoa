package com.web.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.web.dto.PageRequestDTO;
import com.web.service.HospitalService;
import com.web.service.NoticeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	
	private final NoticeService noticeService;
	private final HospitalService hospitalService;
	
	@GetMapping("/")
	public String main(PageRequestDTO pageRequestDTO, Model model) throws Exception {
		
		model.addAttribute("result", hospitalService.getreservationList(pageRequestDTO));
		model.addAttribute("result", noticeService.getList(pageRequestDTO));
		model.addAttribute("hospital", hospitalService.likeHospitallist());
		
		return "index";
	}
	
	
	
	
	
}
