package com.web;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.web.dto.MemberDTO;
import com.web.dto.QuestionReplyDTO;
import com.web.entity.Member;
import com.web.service.MemberService;
import com.web.service.QuestionReplyService;

@SpringBootTest
public class MemverServiceTesst {
	
	@Autowired MemberService memberService;
	@Autowired QuestionReplyService questionReplyService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
//	public Member createPersonalMember() {
//		MemberDTO memberDTO = new MemberDTO();
//		memberDTO.setEmail("test1@email.com");
//		memberDTO.setName("홍길동");
//		memberDTO.setAddress("서울시 구로구 가산동");
//		memberDTO.setPassword("1234");
//		memberDTO.setPhone("010-0000-0000");
//		return Member.createPersonalMember(memberDTO, passwordEncoder);
//	}
//	
//	public Member createHospitalMember() {
//		MemberDTO memberDTO = new MemberDTO();
//		memberDTO.setEmail("test3@email.com");
//		memberDTO.setName("후생한의원");
//		memberDTO.setAddress("전라남도 강진군");
//		memberDTO.setPassword("1234");
//		memberDTO.setHpid("B2600322");
//		memberDTO.setPhone("010-0000-0000");
//		return Member.createHospitalMember(memberDTO, passwordEncoder);
//	}
//	
//	
//	public Member createAdminMember() {
//		MemberDTO memberDTO = new MemberDTO();
//		memberDTO.setEmail("admin2@email.com");
//		memberDTO.setName("관리자");
//		memberDTO.setAddress("서울시 구로구 가산동");
//		memberDTO.setPassword("1234");
//		memberDTO.setPhone("010-0000-0000");
//		return Member.createAdminMember(memberDTO, passwordEncoder);
//	}
	
	//@Test
//	public void membertest() {
//		Member member = this.createHospitalMember();
//	
//		
//		memberService.saveMember(member);
//		
//	}
	
	@Test
	public void 댓글테스트() {
		List<QuestionReplyDTO> questionReplyDTO = questionReplyService.getList(11L);
		System.out.println(questionReplyDTO);
		System.out.println(questionReplyDTO.size());
	
	}
}
