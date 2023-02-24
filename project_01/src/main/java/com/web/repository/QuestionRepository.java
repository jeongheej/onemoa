package com.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.web.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long>,QuestionSearchRepository{
	
//	@Query(value = "select question.* , member.*, count(questionreply.qno) replycount from question left join member on question.email = member.email "
//			+"left join questionreply on question.qno = questionreply.qno where question.qno = :qno"
//			, countQuery = "select count(question) from question" , nativeQuery = true) 
	@Query("select q, w, count(r) from Question q left join q.writer w "
			+ "left join QuestionReply r on q = r.qno where q.qno=:qno")
	Object getQuestionByqno(@Param("qno") Long qno);
	
	@Modifying
	@Query("update Question q set q.qvisitcount = q.qvisitcount +1 where q.qno = :qno")
	int updatevisitCount(@Param("qno") Long qno);

}
