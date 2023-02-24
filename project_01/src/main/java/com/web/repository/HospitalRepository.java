package com.web.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import com.web.entity.Hospital;

public interface HospitalRepository extends JpaRepository<Hospital, String>, HospitalSearchRepository{
	
	
	@Query(value = "select hospital.hpid, "
			+ "hospital.duty_name, hospital.duty_addr, "
			+ "hospital.duty_div_nam, hospital.duty_tel1, "
			+ "ifnull(member.email, 0) as email "
			+ "from hospital left join member on hospital.hpid = member.hpid"
			,countQuery = "select count(*) from hospital",nativeQuery = true) 
	Page<Object[]> getTotalHospital(Pageable pageable);
	
	Hospital findByHpid(String hpid);
	
	@Query(value = "select hospital.hpid, "
			+ "hospital.duty_name, hospital.duty_addr, "
			+ "hospital.duty_div_nam, hospital.duty_tel1, "
			+ "ifnull(member.email, 0) as email "
			+ "from likelist inner join hospital on likelist.hpid = hospital.hpid "
			+ "left join member on likelist.hpid = member.hpid "
			+ "group by hospital.hpid "
			+ "order by count(hospital.hpid) desc limit 5", nativeQuery = true)
	List<Object[]> findBylikeHospital();
}
