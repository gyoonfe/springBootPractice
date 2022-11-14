package com.kids.dto;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;

import com.kids.entity.Notice;
import com.querydsl.core.annotations.QueryProjection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeFormDto {

	private Long idx;
	private String subject;
	private String content;
	private int hit;
	private LocalDateTime regdtm;
	private LocalDateTime moddtm;
	private String regid;
	private String modid;
	private String delyn;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public Notice createNotice() {
		return modelMapper.map(this, Notice.class);
	}
	
	public static NoticeFormDto of(Notice notice) {
		return modelMapper.map(notice, NoticeFormDto.class);
	}
	

	
}
