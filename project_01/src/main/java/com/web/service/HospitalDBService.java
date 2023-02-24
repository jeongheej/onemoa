package com.web.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.web.entity.Hospital;

@Service
public interface HospitalDBService {
	
	public void saveHospitalAll(List<Hospital> hospitals);
	public void saveHospital(Hospital hospital);
	public void saveTiDBWithApi();

}
