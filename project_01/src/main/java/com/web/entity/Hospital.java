package com.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hospital")
@ToString
@Builder
public class Hospital {
	

	@Id
	private String hpid;
	
	private String dutyAddr;
	private String dutyDiv;
	private String dutyDivNam;
	private String dutyEmcls;
	private String dutyEmclsName;
	
	@Column(columnDefinition = "TEXT")
	private String dutyEryn;
	
	@Column(columnDefinition = "TEXT")
	private String dutyEtc;
	private String dutyMapimg;
	private String dutyName;
	private String dutyTel1;
	private String dutyTel3;
	private String dutyTime1c;
	private String dutyTime2c;
	private String dutyTime3c;
	private String dutyTime4c;
	private String dutyTime5c;
	private String dutyTime6c;
	private String dutyTime7c;
	private String dutyTime8c;
	private String dutyTime1s;
	private String dutyTime2s;
	private String dutyTime3s;
	private String dutyTime4s;
	private String dutyTime5s;
	private String dutyTime6s;
	private String dutyTime7s;
	private String dutyTime8s;
	

	private String postCdn1;
	private String postCdn2;
	private String wgs84Lon;
	private String wgs84Lat;
	private String dutyInf;

	

}
