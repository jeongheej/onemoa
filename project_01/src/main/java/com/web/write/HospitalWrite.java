package com.web.write;

import java.util.List;

import org.springframework.stereotype.Component;

import com.web.entity.Hospital;
import com.web.service.HospitalDBService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class HospitalWrite {
	
	private final HospitalDBService hospitalService;
	
	public void writer(List<Hospital> hospitalDtoList) {
		hospitalService.saveHospitalAll(hospitalDtoList);
	}
	
}
