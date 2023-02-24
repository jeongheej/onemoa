package com.web.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.Data;

@Data
public class PageRequestDTO {
	
	private int page;
	private int size;
	private String type;
	private String keyword;
	private String dutytype;
	private String addresstype;
	
	public PageRequestDTO(int page, int size, String dutytype, String addresstype, String keyword) {
		this.page = page;
		this.size = size;
		this.dutytype = dutytype;
		this.addresstype = addresstype;
		this.keyword = keyword;
	}
	
	
	public PageRequestDTO(int page, int size, String type, String keyword) {

		this.page = page;
		this.size = size;
		this.type = type;
		this.keyword = keyword;
	}
	
	public PageRequestDTO(int page) {
		this.page = page;
		
	}
	
	public PageRequestDTO() {
		this.page = 1; this.size = 5;
	}
	
	public Pageable getPageable(Sort sort) {
		return PageRequest.of(page-1, size, sort);
	}
	
	public Pageable getPageableNoSort() {
		return PageRequest.of(page-1, size);
	}
	
}
