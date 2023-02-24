package com.web.contoller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.web.dto.HospitalDTO;
import com.web.dto.MemberDTO;
import com.web.dto.NoticeDTO;
import com.web.dto.PageRequestDTO;
import com.web.entity.Hospital;
import com.web.service.HospitalService;
import com.web.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.ToString.Include;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/reservationpage")
@RequiredArgsConstructor
@Log4j2
public class HospitalContoller {
	
	private final HospitalService hospitalService;
	private final MemberService memberService;
	
	@GetMapping("/reservation")
	public String reservation(PageRequestDTO pageRequestDTO, Model model,Principal principal) throws Exception {
		
		log.info("리스트보기");
		
		MemberDTO memberDto = memberService.getName(principal);
		
		model.addAttribute("memberDto", memberDto);
		model.addAttribute("result", hospitalService.getreservationList(pageRequestDTO));
		
		
		return "reservationpage/reservation";
	}
	
//	@GetMapping("/reservationdtl")
//	public void reservationdtl(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO,
//			String hpid, Model model,Principal principal) throws Exception {
//		
//		MemberDTO memberDto = memberService.getName(principal);
//		HospitalDTO hospitalDTO = hospitalService.getread(hpid);
//		
//		model.addAttribute("memberDto", memberDto);
//		model.addAttribute("dto",hospitalDTO);
//		
//
//		
//	}
	
	@GetMapping("/reservationdtl")
	public void reservationdtl(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO,
			String hpid, Model model,Principal principal) throws Exception {
		
		MemberDTO memberDto = memberService.getName(principal);
		Hospital hospital = hospitalService.read(hpid);
		
		model.addAttribute("memberDto", memberDto);
		model.addAttribute("hospital",hospital);
		

		
	}
	

	
	@GetMapping(value = "/totalhospital")
	public String totalhospitalList(PageRequestDTO pageRequestDTO, Model model,Principal principal) throws Exception {
		
		log.info("리스트보기");
		
		MemberDTO memberDto = memberService.getName(principal);
		
		model.addAttribute("memberDto", memberDto);
		model.addAttribute("result", hospitalService.getList(pageRequestDTO));
		 
		return "reservationpage/totalhospital";
	}
	
}
