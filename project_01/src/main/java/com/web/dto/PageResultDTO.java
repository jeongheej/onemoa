package com.web.dto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class PageResultDTO<DTO, VIEW> {
	//dto 아무거나 들어올수 있음, 일반화 시키려고 ? boardpageresultdto를 일반화 시킨것, view는 db의 뷰를 의미
	private List<DTO> dtoList; //페이지리스트아무거나 ?
	private int totalPage;
	private int page;
	private int size;
	private int start, end;
	private boolean prev, next;
	private int listSize = 5;
	private List<Integer> pageList;
	
	public PageResultDTO(Page<VIEW> result, Function<VIEW, DTO> fn) {
										//Function VIEW를 DTO로 리턴한다는 의미
		
		dtoList = result.get().map(fn).collect(Collectors.toList());
		totalPage = result.getTotalPages();
		makePageList(result.getPageable());
		
	}
	
	
	private void makePageList(Pageable pageable) {
		
		this.page = pageable.getPageNumber()+1;
		this.size = pageable.getPageSize();
		int tempEnd = (int)(Math.ceil(this.page/(double)this.size))*this.listSize;
		start = tempEnd - (this.listSize -1);
		end = totalPage> tempEnd? tempEnd : totalPage;
		prev = start > 1;
		next = totalPage > tempEnd;
		pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
		
	}
	
}
