package com.kids.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kids.dto.NoticeSearchFormDto;


public interface NoticeRepositoryCustom {

	Page<NoticeSearchFormDto> getSubNoticePage(Pageable pageable);
	
}
