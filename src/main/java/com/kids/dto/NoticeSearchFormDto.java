package com.kids.dto;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Data;

@Data
public class NoticeSearchFormDto {

	private Long idx;
	private String subject;
	private String content;
	private LocalDateTime regdtm;

	
	@QueryProjection
	public NoticeSearchFormDto(Long idx, String subject, String content, LocalDateTime regdtm) {
		this.idx = idx;
		this.subject = subject;
		this.content = content;
		this.regdtm = regdtm;
	}

	
}
