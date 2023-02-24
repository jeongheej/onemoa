package com.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Table(name = "hospitalreview")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Hospitalreview extends BaseTimeEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long hrno;
	
	@Column(nullable = false)
	private Integer grade;
	
	@Lob
	@Column(nullable = false)
	private String review;
	
	@ManyToOne
	@JoinColumn(name = "hpid")
	private Hospital hpid;
	
	@ManyToOne
	@JoinColumn(name = "email")
	private Member email;
	
}
