package com.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.web.entity.Question;
import com.web.entity.QuestionReply;

public interface QuestionReplyRepository extends JpaRepository<QuestionReply, Long> {
	
	
	@Modifying
	@Query(value = "delete from questionreply where qno= :qno", nativeQuery = true)
	void deleteByQno(@Param("qno") Long qno);
	
	@Query(value = "select qr from QuestionReply qr "
			+ "inner join Member m on qr.writer = m.email inner join Question q on q.qno = qr.qno "
			+ "where qr.qno.qno = :qno order by qr.qgroup asc, qr.qrno asc")
	List<QuestionReply> getRepliesQuestionByqno(@Param("qno") Long qno);
	
	@Query(value = "select qrno from questionreply order by qrno desc limit 1", nativeQuery = true)
	Long lastqrno();
	
	@Modifying
	@Query(value = "delete from questionreply where qgroup = :qrno", nativeQuery = true)
	void deleteByqgroup(@Param("qrno") Long qrno);
	
}
