package com.web.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MypageController {
	
	@GetMapping("/checkreservation")
	public String checkreservation() {
		return "mypage/personal/checkreservation";
	}
	
	@GetMapping("/checkreservationdtl")
	public String checkreservationdtl() {
		return "mypage/personal/checkreservationdtl";
	}
	
	@GetMapping("/changereservation")
	public String changereservation() {
		return "mypage/personal/changereservation";
	}
	
	@GetMapping("/likelist")
	public String likelist() {
		return "mypage/personal/likelist";
	}

	
	@GetMapping("/personalinfo")
	public String personalinfo() {
		return "mypage/personal/personalinfo";
	}
	
	@GetMapping("/searchlist")
	public String searchlist() {
		return "mypage/personal/searchlist";
	}
	
	
	@GetMapping("/sitemap")
	public String sitemap() {
		return "mypage/sitemap";
	}
	
	@GetMapping("/doreservation")
	public String doreservation() {
		return "mypage/doreservation";
	}
	
	@GetMapping("/checkpatient")
	public String checkpatient() {
		return "mypage/hospital/checkpatient";
	}
	
	@GetMapping("/checkpatientdtl")
	public String checkpatientdtl() {
		return "mypage/hospital/checkpatientdtl";
	}
	
	@GetMapping("/hospitalinfo")
	public String hospitalinfo() {
		return "mypage/hospital/hospitalinfo";
	}
	
	
	
}