package com.web.service;

import java.util.Optional;
import java.util.function.Function;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.dto.NoticeDTO;
import com.web.dto.PageRequestDTO;
import com.web.dto.PageResultDTO;
import com.web.dto.QuestionDTO;
import com.web.dto.ReviewDTO;
import com.web.entity.Member;
import com.web.entity.Notice;
import com.web.entity.Question;
import com.web.entity.QuestionReply;
import com.web.entity.Review;
import com.web.repository.QuestionReplyRepository;
import com.web.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class QuestionService {
	
	private final QuestionRepository questionRepository;
	private final QuestionReplyRepository replyRepository;
	
	@Transactional
	public Long register(QuestionDTO questionDTO) throws Exception{
		
		log.info(questionDTO);
		Question question = dtoToEntity(questionDTO);
		questionRepository.save(question);
		return question.getQno();
	}
	

	public PageResultDTO<QuestionDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
		
		Function<Object[], QuestionDTO> fn = (e-> entityToDTO((Question)e[0], (Member)e[1], (Long)e[2]));
		
		
		Page<Object[]> results = questionRepository.searchPage(pageRequestDTO.getType()
				, pageRequestDTO.getKeyword(), pageRequestDTO.getPageable(Sort.by("qno").descending()));
		
		return new PageResultDTO<>(results, fn);
	}
	
	
	public QuestionDTO getread(Long qno) {
		Object result = questionRepository.getQuestionByqno(qno);
		Object[] arr = (Object[]) result;
 		
		return this.entityToDTO((Question)arr[0], (Member)arr[1], (Long)arr[2]);
	}
	
	@Transactional
	public int updateVisitCount(Long rno) {
		return questionRepository.updatevisitCount(rno);
	}
	
	
	public boolean qpasswordcheck(Long qno, String qpassword) {

        Question question = questionRepository.findById(qno).orElseThrow(EntityNotFoundException::new);
        if(question !=null && question.getQpassword().equals(qpassword)) {
            return true;
        }
        else {
            return false;
        }
    }
	
	@Transactional
	public void removeWithReplies(Long qno) {
		
		
		replyRepository.deleteByQno(qno);
		questionRepository.deleteById(qno);
		
	}
	
	@Transactional
	public void modify(QuestionDTO questionDTO) throws Exception {
		Question question = questionRepository.findById(questionDTO.getQno())
				.orElseThrow(EntityNotFoundException::new);
		question.changeTitle(questionDTO.getTitle());
		question.changeContent(questionDTO.getContent());
		questionRepository.save(question);
	}
	
	
	public Question dtoToEntity(QuestionDTO questionDTO) {
		
		Member member = Member.builder()
				.email(questionDTO.getWriterEmail())
				.build();
		
		Question question = Question.builder()
				.qno(questionDTO.getQno())
				.title(questionDTO.getTitle())
				.content(questionDTO.getContent())
				.writer(member)
				.secretYn(questionDTO.getSecretYn())
				.qpassword(questionDTO.getQpassword())
				.qvisitcount(questionDTO.getQvisitcount())
				.build();
		
		return question;
	}
	
	
	
	public QuestionDTO entityToDTO(Question question, Member member, Long replyCount) {
		
		QuestionDTO questionDTO = QuestionDTO.builder()
				.qno(question.getQno())
				.title(question.getTitle())
				.content(question.getContent())
				.qpassword(question.getQpassword())
				.qvisitcount(question.getQvisitcount())
				.replyCount(replyCount.intValue())
				.updateTime(question.getUpdateTime())
				.secretYn(question.getSecretYn())
				.writerEmail(member.getEmail())
				.writerName(member.getName())
				.role(member.getRole().toString())
				.build();
		
		return questionDTO;
		
	}
}
