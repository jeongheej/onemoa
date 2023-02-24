package com.web.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Id;

import org.springframework.stereotype.Service;

import com.web.dto.HospitalDBDTO;
import com.web.entity.Hospital;
import com.web.reader.HospitalReader;
import com.web.repository.HospitalRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HospitalDBServiceImpl implements HospitalDBService{
	
	private final HospitalRepository hospitalRepository;
	private final HospitalReader hospitalReader;
	
	@Override
	public void saveHospitalAll(List<Hospital> hospitals) {
		hospitalRepository.saveAll(hospitals);
		
	}

	@Override
	public void saveHospital(Hospital hospital) {
		hospitalRepository.save(hospital);
		
	}

	@Override
	public void saveTiDBWithApi() {
		try {
			
			poestDBToApi("3000", "1");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public int poestDBToApi(String numOfRows, String pageNo) throws IOException, InterruptedException {
		
		List<HospitalDBDTO> hospitalDtos = hospitalReader.fetchHospitalDtoFromAPI(numOfRows, pageNo);
		List<Hospital> hospitals = hospitalDtos.stream().map(dto-> 
		new Hospital(
				dto.getHpid(),
				dto.getDutyAddr(),
				dto.getDutyDiv(),
				dto.getDutyDivNam(),
				dto.getDutyEmcls(),
				dto.getDutyEmclsName(),
				dto.getDutyEryn(),
				dto.getDutyEtc(),
				dto.getDutyMapimg(),
				dto.getDutyName(),
				dto.getDutyTel1(),
				dto.getDutyTel3(),
				dto.getDutyTime1c(),
				dto.getDutyTime2c(),
				dto.getDutyTime3c(),
				dto.getDutyTime4c(),
				dto.getDutyTime5c(),
				dto.getDutyTime6c(),
				dto.getDutyTime7c(),
				dto.getDutyTime8c(),
				dto.getDutyTime1s(),
				dto.getDutyTime2s(),
				dto.getDutyTime3s(),
				dto.getDutyTime4s(),
				dto.getDutyTime5s(),
				dto.getDutyTime6s(),
				dto.getDutyTime7s(),
				dto.getDutyTime8s(),
				dto.getPostCdn1(),
				dto.getPostCdn2(),
				dto.getWgs84Lon(),
				dto.getWgs84Lat(),
				dto.getDutyInf()
				
		)).collect(Collectors.toList());
		hospitalRepository.saveAll(hospitals);
		return hospitals.size();

		
	}

}
