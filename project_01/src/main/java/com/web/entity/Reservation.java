package com.web.entity;

import java.util.Date;

import javax.persistence.Column;
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
@Table(name = "reservation")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Reservation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long rvno; 
	
	@Column(nullable = false)
	private String reservationPhone;
	
	@Column(nullable = false)
	private String reservationAddress;
	
	@Column(nullable = false)
	private Date reservationDate;
	
	@Column(nullable = false)
	private String reservationTime;
	
	@Column(nullable = false)
	private String symptom;
	
	@Column(nullable = false)
	private String etc;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "email")
	private Member email; 
	
	
	
}
