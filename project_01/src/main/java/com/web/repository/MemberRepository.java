package com.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.web.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String>{
	
	Member findByEmail(String email);
	
	@Query(value = "select ifnull(m.email, 0) from member m where m.name=:name and m.phone = :phone", nativeQuery = true)
	String searchid(@Param("name")String name, @Param("phone")String phone);
	
	@Modifying
	@Query(value = "UPDATE Member m set m.password = :password where m.email = :email", nativeQuery = true)
	void updateMemberPassword(@Param("password")String password, @Param("email")String email) throws Exception;
	
}
