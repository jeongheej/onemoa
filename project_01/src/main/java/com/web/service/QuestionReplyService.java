package com.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.dto.QuestionDTO;
import com.web.dto.QuestionReplyDTO;
import com.web.entity.Member;
import com.web.entity.Question;
import com.web.entity.QuestionReply;
import com.web.entity.Review;
import com.web.repository.MemberRepository;
import com.web.repository.QuestionReplyRepository;
import com.web.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionReplyService {
	
	private final QuestionReplyRepository replyRepository;
	private final MemberRepository memberRepository;
	private final QuestionRepository questionRepository;
	
	@Transactional
	public List<QuestionReplyDTO> getList(Long qno) {
		
		System.out.println(">>>>>>>>>>>>>>> 조회하고 있어요");
		
		List<QuestionReply> result = replyRepository.getRepliesQuestionByqno(qno);
		return result.stream().map(reply-> this.entityToDTO((QuestionReply)reply))
				.collect(Collectors.toList());
	}
	
	@Transactional
	public void modify(QuestionReplyDTO replyDTO) {
		QuestionReply reply = replyRepository.findById(replyDTO.getQrno())
				.orElseThrow(EntityNotFoundException::new);
		reply.changeContent(replyDTO.getContent());
		replyRepository.save(reply);
		
		
	}
	
	
	public Long register(QuestionReplyDTO questionReplyDTO) {
		QuestionReply questionReply = this.dtoToEntity(questionReplyDTO);
		replyRepository.save(questionReply);
		return questionReply.getQrno();
	}
	
	
	public HashMap<String, Object> commentPost(Long qno, String content, Long depth, Long qgroup, String email) throws Exception {
        HashMap<String, Object> map = new HashMap<>();

        QuestionReplyDTO questionReplyDTO = new QuestionReplyDTO();
        
        questionReplyDTO.setContent(content);
        questionReplyDTO.setDepth(depth);
        questionReplyDTO.setQgroup(qgroup);
      

        Optional<Member> member = memberRepository.findById(email);
        Optional<Question> question = questionRepository.findById(qno);

        QuestionReply questionReply = dtoToEntity(questionReplyDTO);

        questionReply.setQno(question.get());
        questionReply.setWriter(member.get());

        replyRepository.save(questionReply);
        map.put("result","success");
        return map;
    }
	
	@Transactional
	public void remove(Long qrno) {
		//모댓글인 경우 전체삭제
        	replyRepository.deleteById(qrno);
        	replyRepository.deleteByqgroup(qrno);
       
	}
	
	@Transactional
	public void recoremove(Long qrno) {
		
        	replyRepository.deleteById(qrno);
        	
       
	}
	
	public QuestionReplyDTO entityToDTO(QuestionReply questionReply) {
		
		QuestionReplyDTO questionReplyDTO = QuestionReplyDTO.builder()
				.qrno(questionReply.getQrno())
				.content(questionReply.getContent())
				.depth(questionReply.getDepth())
				.qgroup(questionReply.getQgroup())
				.qno(questionReply.getQno().getQno())
				.updateTime(questionReply.getUpdateTime())
				.writerEmail(questionReply.getWriter().getEmail())
				.writerName(questionReply.getWriter().getName())
				.build();
		
		return questionReplyDTO;
		
	}
	
	public QuestionReply dtoToEntity(QuestionReplyDTO questionReplyDTO) {
		
	
		QuestionReply questionReply = QuestionReply.builder()
				.qrno(questionReplyDTO.getQrno()).content(questionReplyDTO.getContent())
				.depth(questionReplyDTO.getDepth()).qgroup(questionReplyDTO.getQgroup())
				.build();

		return questionReply;
	}
}
