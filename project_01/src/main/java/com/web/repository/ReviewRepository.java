package com.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.web.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>
,ReviewSearchRepository{
	
	@Modifying
	@Query("update Review r set r.revisitcount = r.revisitcount +1 where r.rno = :rno")
	int updatevisitCount(@Param("rno") Long rno);
	
	@Query(value = "select r, w from Review r left join r.writer w where r.rno=:rno") 
	Object getReviewByRno(@Param("rno") Long rno);
}
