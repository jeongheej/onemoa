package com.web.contoller;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.web.dto.MailDTO;
import com.web.service.MemberService;
import com.web.service.SendEmailService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class Membercontroller {
	
	private final MemberService memberService;
	private final SendEmailService sendEmailService;
	
	@GetMapping(value = "/login")
	public String login() {
		
		return "loginpage/login";
	}
	
	@GetMapping(value = "/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "loginpage/login";
    }
	
	@GetMapping("/searchidpw")
	public String searchidpw() {
		return "loginpage/searchIdPw";
	}
	
	@PostMapping(value = "/searchid")
	public @ResponseBody String find_id(@RequestParam("name") String name,@RequestParam("phone") String phone) {
		
		String result = memberService.find_id(name, phone);
			
		return result;
	}
	
	//Email과 name의 일치여부를 check하는 컨트롤러
	 @PostMapping("/searchpw")
	 public @ResponseBody Map<String, Boolean> pw_find(String email, String name,String phone){
	    Map<String,Boolean> json = new HashMap<>();
	    boolean pwFindCheck = memberService.emailCheck(email, name, phone);

	    System.out.println(pwFindCheck);
	    json.put("check", pwFindCheck);
	    return json;
	 }

	//등록된 이메일로 임시비밀번호를 발송하고 발송된 임시비밀번호로 사용자의 pw를 변경하는 컨트롤러
	@Transactional
	@PostMapping("/searchpw/sendEmail")
	public String sendEmail(@RequestParam("email") String email,@RequestParam("name") String name) throws Exception{
		
		MailDTO dto = sendEmailService.createMailAndChangePassword(email, name);
	    sendEmailService.mailSend(dto);
	    
	    return "loginpage/login";

	}
	

   
	
	
}
