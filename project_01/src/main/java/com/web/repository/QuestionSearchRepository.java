package com.web.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionSearchRepository {
	Page<Object[]> searchPage(String type, String keyword, Pageable pageable);
}
