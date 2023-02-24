package com.web.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.service.HospitalDBService;

@RestController
@RequestMapping("/db")
public class HospitalDBController {
	
	@Autowired HospitalDBService hospitalService;
	
	@GetMapping("/save")
	public String hospitalToDB() {
		
		hospitalService.saveTiDBWithApi();
		
		return "Success";
		
	}
	
}
