package com.web.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HospitalSearchRepository {
	
	Page<Object[]> totalSearchPage(String dutytype, String addresstype, String keyword, Pageable pageable);
	Page<Object[]> searchPage(String dutytype, String addresstype, String keyword, Pageable pageable);
}
