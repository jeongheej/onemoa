package com.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.web.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long>
, NoticeSearchBoardRepository{
	
	@Modifying
	@Query("update Notice n set n.rvisitcount = n.rvisitcount +1 where n.nno = :nno")
	int updatevisitCount(@Param("nno") Long nno);
	
	@Query(value = "select n, w from Notice n left join n.writer w where n.nno=:nno") 
	Object getNoticeByNno(@Param("nno") Long nno);
	

}
