package com.web.service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.web.dto.HospitalDTO;
import com.web.dto.NoticeDTO;
import com.web.dto.PageRequestDTO;
import com.web.dto.PageResultDTO;
import com.web.dto.ReviewDTO;
import com.web.entity.Hospital;
import com.web.entity.Member;
import com.web.entity.Notice;
import com.web.entity.Review;
import com.web.repository.HospitalRepository;
import com.web.repository.NoticeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HospitalService {
	
	private final HospitalRepository hospitalRepository;
	

	
	public PageResultDTO<HospitalDTO, Object[]> getList(PageRequestDTO pageRequestDTO) throws Exception{
		
		
		Page<Object[]> results = hospitalRepository.totalSearchPage(pageRequestDTO.getDutytype()
				, pageRequestDTO.getAddresstype(), pageRequestDTO.getKeyword(), pageRequestDTO.getPageable(Sort.by("hpid")));
		
		Function<Object[], HospitalDTO> fn = (h-> 
			{
				String hpid = String.valueOf(h[0]);
				String dutyName = String.valueOf(h[1]);
				String dutyAddr = String.valueOf(h[2]);
				String dutyDivNam = String.valueOf(h[3]);
				String dutyTel1 = String.valueOf(h[4]);
				String email = String.valueOf(h[5]);
				
				HospitalDTO dto = HospitalDTO.builder()
						.hpid(hpid).dutyName(dutyName).dutyAddr(dutyAddr)
						.dutyDivNam(dutyDivNam).dutyTel1(dutyTel1).email(email).build();
				return dto;
			});
			return new PageResultDTO<>(results, fn);
	}
	

	
	public Hospital read(String hpid) {
		
	  return hospitalRepository.findByHpid(hpid);
	}
	
	public PageResultDTO<HospitalDTO, Object[]> getreservationList(PageRequestDTO pageRequestDTO) throws Exception{
		
		Function<Object[], HospitalDTO> fn = (e-> entityToDTO((Hospital)e[0], (Member)e[1]));
		Page<Object[]> results = hospitalRepository.searchPage(pageRequestDTO.getDutytype()
				, pageRequestDTO.getAddresstype(), pageRequestDTO.getKeyword(), pageRequestDTO.getPageable(Sort.by("hpid")));
		return new PageResultDTO<>(results, fn);
	}
	
	public List<HospitalDTO> likeHospitallist() {
		
		List<Object[]> results = hospitalRepository.findBylikeHospital(); //entity상태
		Function<Object[], HospitalDTO> fn = (h-> 
		{
			String hpid = String.valueOf(h[0]);
			String dutyName = String.valueOf(h[1]);
			String dutyAddr = String.valueOf(h[2]);
			String dutyDivNam = String.valueOf(h[3]);
			String dutyTel1 = String.valueOf(h[4]);
			String email = String.valueOf(h[5]);
			
			HospitalDTO dto = HospitalDTO.builder()
					.hpid(hpid).dutyName(dutyName).dutyAddr(dutyAddr)
					.dutyDivNam(dutyDivNam).dutyTel1(dutyTel1).email(email).build();
			return dto;
		});
		
		List<HospitalDTO> hospitalDTOList = results.stream().map(fn).collect(Collectors.toList()); //result를 fn으로 바꿔줌
		return hospitalDTOList;
	}
	
	public HospitalDTO entityToDTO(Hospital hospital, Member member) {
		
		HospitalDTO hospitalDTO = HospitalDTO.builder()
				.hpid(hospital.getHpid())
				.dutyName(hospital.getDutyName())
				.dutyAddr(hospital.getDutyAddr())
				.dutyDivNam(hospital.getDutyDivNam())
				.dutyTel1(hospital.getDutyTel1())
				.email(member.getEmail())
				.build();
		
		return hospitalDTO;
		
	}
	

}
