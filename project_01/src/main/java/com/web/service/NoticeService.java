package com.web.service;

import java.util.List;
import java.util.function.Function;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.dto.NoticeDTO;
import com.web.dto.PageRequestDTO;
import com.web.dto.PageResultDTO;
import com.web.entity.Member;
import com.web.entity.Notice;
import com.web.repository.NoticeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Service
@Log4j2
public class NoticeService {
	
	private final NoticeRepository noticeRepository;
	
	@Transactional
	public Long register(NoticeDTO noticeDTO) throws Exception{
		
		log.info(noticeDTO);
		Notice notice = dtoToEntity(noticeDTO);
		noticeRepository.save(notice);
		return notice.getNno();
	}
	
	public PageResultDTO<NoticeDTO, Object[]> getList(PageRequestDTO pageRequestDTO) throws Exception{
		
		Function<Object[], NoticeDTO> fn = (e-> entityToDTO((Notice)e[0], (Member)e[1]));
		Page<Object[]> results = noticeRepository.searchPage(pageRequestDTO.getType()
				, pageRequestDTO.getKeyword(), pageRequestDTO.getPageable(Sort.by("nno").descending()));
		return new PageResultDTO<>(results, fn);
	}
	
	
	public NoticeDTO getread(Long nno) throws Exception {
		
		Object result = noticeRepository.getNoticeByNno(nno);
		Object[] arr = (Object[]) result;
 		
		return this.entityToDTO((Notice)arr[0], (Member)arr[1]);
				
	}
	
	@Transactional
	public void modify(NoticeDTO noticeDTO) throws Exception {
		Notice notice = noticeRepository.findById(noticeDTO.getNno())
				.orElseThrow(EntityNotFoundException::new);
		notice.changeTitle(noticeDTO.getTitle());
		notice.changeContent(noticeDTO.getContent());
		noticeRepository.save(notice);
	}
	
	public void remove(Long nno) throws Exception {
		
		noticeRepository.deleteById(nno);
	}
	
	public Notice dtoToEntity(NoticeDTO noticeDTO) {
		//dto를 entity로 맵핑
		Member member = Member.builder()
				.email(noticeDTO.getWriterEmail())
				.build();
		
		Notice notice = Notice.builder()
				.nno(noticeDTO.getNno())
				.title(noticeDTO.getTitle())
				.content(noticeDTO.getContent())
				.writer(member)
				.rvisitcount(noticeDTO.getRvisitcount())
				.build();
		
		return notice;
	}
	
	public NoticeDTO entityToDTO(Notice notice, Member member) {
		
		NoticeDTO noticeDTO = NoticeDTO.builder()
				.nno(notice.getNno())
				.title(notice.getTitle())
				.content(notice.getContent())
				.rvisitcount(notice.getRvisitcount())
				.regTime(notice.getRegTime())
				.updateTime(notice.getUpdateTime())
				.writerEmail(member.getEmail())
				.writerName(member.getName())
				.build();
		
		return noticeDTO;
		
	}
	
	@Transactional
	public int updateVisitCount(Long nno) {
		return noticeRepository.updatevisitCount(nno);
	}
	
}
