package com.web.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterController {
	
	@GetMapping("/register")
	public String register() {
		return "registerpage/personalregister";
	}
	
	@GetMapping("/registertype")
	public String registertype() {
		return "registerpage/registertype";
	}
	
	@GetMapping("/hospitalregister")
	public String hospitalregister() {
		return "registerpage/hospitalregister";
	}
}
