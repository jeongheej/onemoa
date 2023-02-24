package com.web.service;

import java.security.Principal;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.web.dto.MemberDTO;
import com.web.entity.Member;
import com.web.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
	
	private final MemberRepository memberRepository;
	
	
	 public Member saveMember(Member member){
	        
	        return memberRepository.save(member);
	    }
	
	 public MemberDTO getName(Principal principal) {
		 
		 if(principal != null) {
			 String email = principal.getName();
			 Member member = memberRepository.findByEmail(email);
		
		
		 MemberDTO memberDTO = MemberDTO.builder()
				 .email(member.getEmail())
				 .name(member.getName())
				 .phone(member.getPhone())
				 .address(member.getAddress())
				 .role(member.getRole().toString())
				 .build();
		 return memberDTO;}
		 
		 return null;
	 }
	 
	
	 
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Member member = memberRepository.findByEmail(email);

        if(member == null){
            throw new UsernameNotFoundException(email);
        }

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    
	}
	
	public String find_id(String name, String phone){
		
		String result = "";
	
		try {
		 result= memberRepository.searchid(name, phone);
		 
		} catch(Exception e) {
			
			e.printStackTrace();
		}
	
		return result ;
	}
	
	
	public boolean emailCheck(String email, String name, String phone) {

        Member member = memberRepository.findByEmail(email);
        if(member!=null && member.getName().equals(name) 
        		&& member.getPhone().equals(phone)) {
            return true;
        }
        else {
            return false;
        }
    }
}
