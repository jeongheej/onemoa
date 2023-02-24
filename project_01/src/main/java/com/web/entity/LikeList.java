package com.web.entity;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Table(name = "likelist")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LikeList {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long lno; 
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "email")
	private Member email;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hpid")
	private Hospital hpid;
	
}
