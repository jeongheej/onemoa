package com.web.service;

import java.util.function.Function;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.dto.PageRequestDTO;
import com.web.dto.PageResultDTO;
import com.web.dto.ReviewDTO;
import com.web.entity.Member;
import com.web.entity.Review;
import com.web.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Service
@Log4j2
public class ReviewService {
	
	private final ReviewRepository reviewRepository;
	
	@Transactional
	public Long register(ReviewDTO reviewDTO) throws Exception{
		
		log.info(reviewDTO);
		Review review = dtoToEntity(reviewDTO);
		reviewRepository.save(review);
		return review.getRno();
	}
	
	public PageResultDTO<ReviewDTO, Object[]> getList(PageRequestDTO pageRequestDTO) throws Exception{
		
		Function<Object[], ReviewDTO> fn = (e-> entityToDTO((Review)e[0], (Member)e[1]));
		Page<Object[]> results = reviewRepository.searchPage(pageRequestDTO.getType()
				, pageRequestDTO.getKeyword(), pageRequestDTO.getPageable(Sort.by("rno").descending()));
		return new PageResultDTO<>(results, fn);
	}
	
	
	public ReviewDTO getread(Long rno) throws Exception {
		
		Object result = reviewRepository.getReviewByRno(rno);
		Object[] arr = (Object[]) result;
 		
		return this.entityToDTO((Review)arr[0], (Member)arr[1]);
				
	}
	
	@Transactional
	public void modify(ReviewDTO reviewDTO) throws Exception {
		Review review = reviewRepository.findById(reviewDTO.getRno())
				.orElseThrow(EntityNotFoundException::new);
		review.changeTitle(reviewDTO.getTitle());
		review.changeContent(reviewDTO.getContent());
		reviewRepository.save(review);
	}
	
	public void remove(Long rno) throws Exception {
		
		reviewRepository.deleteById(rno);
	}
	
	public Review dtoToEntity(ReviewDTO reviewDTO) {
		//dto를 entity로 맵핑
		Member member = Member.builder()
				.email(reviewDTO.getWriterEmail())
				.build();
		
		Review review = Review.builder()
				.rno(reviewDTO.getRno())
				.title(reviewDTO.getTitle())
				.content(reviewDTO.getContent())
				.writer(member)
				.revisitcount(reviewDTO.getRevisitcount())
				.build();
		
		return review;
	}
	
	public ReviewDTO entityToDTO(Review review, Member member) {
		
		ReviewDTO reviewDTO = ReviewDTO.builder()
				.rno(review.getRno())
				.title(review.getTitle())
				.content(review.getContent())
				.revisitcount(review.getRevisitcount())
				.regTime(review.getRegTime())
				.updateTime(review.getUpdateTime())
				.writerEmail(member.getEmail())
				.writerName(member.getName())
				.build();
		
		return reviewDTO;
		
	}
	
	@Transactional
	public int updateVisitCount(Long rno) {
		return reviewRepository.updatevisitCount(rno);
	}
	
}
