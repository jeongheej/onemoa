package com.web.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.fasterxml.jackson.annotation.JacksonInject.Value;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;

import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.web.entity.Hospital;

import com.web.entity.QHospital;
import com.web.entity.QMember;


public class HospitalSearchRepositoryImpl extends QuerydslRepositorySupport implements HospitalSearchRepository{

	public HospitalSearchRepositoryImpl() {
		super(Hospital.class);
		
	}

	@Override
	public Page<Object[]> searchPage(String dutytype, String addresstype, String keyword, Pageable pageable) {
		JPQLQuery<Hospital> jpqlQuery = from(QHospital.hospital);
		jpqlQuery.innerJoin(QMember.member).on(QMember.member.hpid.eq(QHospital.hospital));
		JPQLQuery<Tuple> tuple = jpqlQuery.select(QHospital.hospital, QMember.member);
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		
		
		if(dutytype != null) {
			String[] typeArr = dutytype.split("");
			BooleanBuilder conditionBuilder = new BooleanBuilder();
			for(String t: typeArr) {
				switch (t) {
				case "a": conditionBuilder.or(QHospital.hospital.dutyDivNam.contains("종합병원"));
					break;
				
			
				case "b": conditionBuilder.or(QHospital.hospital.dutyDivNam.eq("병원"));
				break;
				
				case "c": conditionBuilder.or(QHospital.hospital.dutyDivNam.contains("의원"));
				break;
				
				case "d": conditionBuilder.or(QHospital.hospital.dutyDivNam.contains("요양병원"));
				break;
				
				case "e": conditionBuilder.or(QHospital.hospital.dutyDivNam.contains("한방병원"));
				break;
				
				
				case "g": conditionBuilder.or(QHospital.hospital.dutyDivNam.contains("한의원"));
				break;
				
				case "i": conditionBuilder.or(QHospital.hospital.dutyDivNam.contains("기타"));
				break;
				
				case "m": conditionBuilder.or(QHospital.hospital.dutyDivNam.contains("치과병원"));
				break;
				
				case "n": conditionBuilder.or(QHospital.hospital.dutyDivNam.contains("치과의원"));
				break;
				
				case "r": conditionBuilder.or(QHospital.hospital.dutyDivNam.contains("보건소"));
				break;
				}
			}
			
			booleanBuilder.and(conditionBuilder);
		
		}

		if(addresstype != null) {
			
	
			BooleanBuilder conditionBuilder2 = new BooleanBuilder();
		
				switch (addresstype) {
				case "seo": conditionBuilder2.or(QHospital.hospital.dutyAddr.contains("서울"));
					break;
				
			
				case "bu": conditionBuilder2.or(QHospital.hospital.dutyAddr.contains("부산"));
				break;
				
				case "dae": conditionBuilder2.or(QHospital.hospital.dutyAddr.contains("대구"));
				break;
				
				case "inc": conditionBuilder2.or(QHospital.hospital.dutyAddr.contains("인천"));
				break;
				
				case "daej": conditionBuilder2.or(QHospital.hospital.dutyAddr.contains("대전"));
				break;
				
				case "uls": conditionBuilder2.or(QHospital.hospital.dutyAddr.contains("울산"));
				break;
				
				case "sej": conditionBuilder2.or(QHospital.hospital.dutyAddr.contains("세종"));
				break;
				
				case "kyu": conditionBuilder2.or(QHospital.hospital.dutyAddr.contains("경기"));
				break;
				
				case "kang": conditionBuilder2.or(QHospital.hospital.dutyAddr.contains("강원"));
				break;
				
				case "chungn": conditionBuilder2.or(QHospital.hospital.dutyAddr.contains("충청북도"));
				break;
				
				case "chungs": conditionBuilder2.or(QHospital.hospital.dutyAddr.contains("충청남도"));
				break;
				
				case "jeonn": conditionBuilder2.or(QHospital.hospital.dutyAddr.contains("전라북도"));
				break;
				
				case "jeons": conditionBuilder2.or(QHospital.hospital.dutyAddr.contains("전라남도"));
				break;
				
				case "kyun": conditionBuilder2.or(QHospital.hospital.dutyAddr.contains("경상북도"));
				break;
				
				case "kyur": conditionBuilder2.or(QHospital.hospital.dutyAddr.contains("경상남도"));
				break;
				
				case "je": conditionBuilder2.or(QHospital.hospital.dutyAddr.contains("제주"));
				break;
			
				
			}
			
			booleanBuilder.and(conditionBuilder2);
		}
		
		if(keyword != null) {
			BooleanBuilder conditionBuilder3 = new BooleanBuilder();
			conditionBuilder3.or(QHospital.hospital.dutyName.contains(keyword));
			booleanBuilder.and(conditionBuilder3);
		}
		
		tuple.where(booleanBuilder);
		Sort sort = pageable.getSort();
		sort.stream().forEach(order-> {
			Order direction = order.isAscending()? Order.ASC:Order.DESC;
			String prop = order.getProperty();
			PathBuilder orderByExpression = new PathBuilder<>(Hospital.class, "hospital");
			tuple.orderBy(new OrderSpecifier<>(direction, orderByExpression.get(prop)));
		});
		
		tuple.groupBy(QHospital.hospital);
		tuple.offset(pageable.getOffset()); //페이지 시작지점
		tuple.limit(pageable.getPageSize());
		List<Tuple> result= tuple.fetch();
		long count = tuple.fetchCount();
		
		return new PageImpl<Object[]>(result.stream()
				.map(t->t.toArray()).collect(Collectors.toList()), pageable, count);
	}
	
	

	@Override
	public Page<Object[]> totalSearchPage(String dutytype, String addresstype, String keyword, Pageable pageable) {
		JPQLQuery<Hospital> jpqlQuery = from(QHospital.hospital);
		jpqlQuery.leftJoin(QMember.member).on(QMember.member.hpid.eq(QHospital.hospital));
		JPQLQuery<Tuple> tuple = jpqlQuery.select(QHospital.hospital.hpid, QHospital.hospital.dutyName,
				QHospital.hospital.dutyAddr, QHospital.hospital.dutyDivNam, QHospital.hospital.dutyTel1, QMember.member.email);
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		
		
		if(dutytype != null) {
			String[] typeArr = dutytype.split("");
			BooleanBuilder conditionBuilder = new BooleanBuilder();
			for(String t: typeArr) {
				switch (t) {
				case "a": conditionBuilder.or(QHospital.hospital.dutyDivNam.contains("종합병원"));
					break;
				
			
				case "b": conditionBuilder.or(QHospital.hospital.dutyDivNam.eq("병원"));
				break;
				
				case "c": conditionBuilder.or(QHospital.hospital.dutyDivNam.contains("의원"));
				break;
				
				case "d": conditionBuilder.or(QHospital.hospital.dutyDivNam.contains("요양병원"));
				break;
				
				case "e": conditionBuilder.or(QHospital.hospital.dutyDivNam.contains("한방병원"));
				break;
				
				
				case "g": conditionBuilder.or(QHospital.hospital.dutyDivNam.contains("한의원"));
				break;
				
				case "i": conditionBuilder.or(QHospital.hospital.dutyDivNam.contains("기타"));
				break;
				
				case "m": conditionBuilder.or(QHospital.hospital.dutyDivNam.contains("치과병원"));
				break;
				
				case "n": conditionBuilder.or(QHospital.hospital.dutyDivNam.contains("치과의원"));
				break;
				
				case "r": conditionBuilder.or(QHospital.hospital.dutyDivNam.contains("보건소"));
				break;
				}
			}
			
			booleanBuilder.and(conditionBuilder);
		}
		
		if(addresstype != null) {
	
			BooleanBuilder conditionBuilder2 = new BooleanBuilder();
		
				switch (addresstype) {
				case "seo": conditionBuilder2.or(QHospital.hospital.dutyAddr.contains("서울"));
					break;
				
			
				case "bu": conditionBuilder2.or(QHospital.hospital.dutyAddr.contains("부산"));
				break;
				
				case "dae": conditionBuilder2.or(QHospital.hospital.dutyAddr.contains("대구"));
				break;
				
				case "inc": conditionBuilder2.or(QHospital.hospital.dutyAddr.contains("인천"));
				break;
				
				case "daej": conditionBuilder2.or(QHospital.hospital.dutyAddr.contains("대전"));
				break;
				
				case "uls": conditionBuilder2.or(QHospital.hospital.dutyAddr.contains("울산"));
				break;
				
				case "sej": conditionBuilder2.or(QHospital.hospital.dutyAddr.contains("세종"));
				break;
				
				case "kyu": conditionBuilder2.or(QHospital.hospital.dutyAddr.contains("경기"));
				break;
				
				case "kang": conditionBuilder2.or(QHospital.hospital.dutyAddr.contains("강원"));
				break;
				
				case "chungn": conditionBuilder2.or(QHospital.hospital.dutyAddr.contains("충청북도"));
				break;
				
				case "chungs": conditionBuilder2.or(QHospital.hospital.dutyAddr.contains("충청남도"));
				break;
				
				case "jeonn": conditionBuilder2.or(QHospital.hospital.dutyAddr.contains("전라북도"));
				break;
				
				case "jeons": conditionBuilder2.or(QHospital.hospital.dutyAddr.contains("전라남도"));
				break;
				
				case "kyun": conditionBuilder2.or(QHospital.hospital.dutyAddr.contains("경상북도"));
				break;
				
				case "kyur": conditionBuilder2.or(QHospital.hospital.dutyAddr.contains("경상남도"));
				break;
				
				case "je": conditionBuilder2.or(QHospital.hospital.dutyAddr.contains("제주"));
				break;
			
				
			}
			
			booleanBuilder.and(conditionBuilder2);
		}
		
		tuple.where(booleanBuilder);
		Sort sort = pageable.getSort();
		sort.stream().forEach(order-> {
			Order direction = order.isAscending()? Order.ASC:Order.DESC;
			String prop = order.getProperty();
			PathBuilder orderByExpression = new PathBuilder<>(Hospital.class, "hospital");
			tuple.orderBy(new OrderSpecifier<>(direction, orderByExpression.get(prop)));
		});
		
		tuple.groupBy(QHospital.hospital.hpid);
		tuple.offset(pageable.getOffset()); 
		tuple.limit(pageable.getPageSize());
		List<Tuple> result= tuple.fetch();
		long count = tuple.fetchCount();
		
		return new PageImpl<Object[]>(result.stream()
				.map(t->t.toArray()).collect(Collectors.toList()), pageable, count);
	}

}
