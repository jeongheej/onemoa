package com.web.dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HospitalDTO {
	
	private String hpid; //기관코드명
	private String dutyName; //병원이름
	private String dutyAddr; //병원주소
	private String dutyDivNam; //병원분류명
	private String dutyTel1; //병원전화번호
	private String email; //null 여부 확인위해 필요

}
