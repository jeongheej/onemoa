package com.web.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.web.constant.Role;
import com.web.dto.MemberDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Table(name = "member")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Member {
	
	@Id
	private String email;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String address;
	
	@Column(nullable = false)
	private String phone;
	
	private Date birthday;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Role role;
	

	
	@OneToOne
	@JoinColumn(name = "hpid")
	private Hospital hpid;
	
	
	
	
	
	
}
