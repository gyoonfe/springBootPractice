package com.kids.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kids.dto.NoticeFormDto;
import com.kids.dto.NoticeSearchFormDto;
import com.kids.entity.Notice;
import com.kids.repository.NoticeRepository;
import com.kids.repository.NoticeRepositoryCustom;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class NoticeService {

	private final NoticeRepository noticeRepository;
	
	public void saveNotice(NoticeFormDto noticeFormDto) {
		
		noticeFormDto.setHit(0);
		noticeFormDto.setDelyn("N");
		// 등록처리 : DTO를 저장하기 위해 Entity 변환 > save() 메서드를 이용하여 저장
		
		Notice notice = noticeFormDto.createNotice();
		noticeRepository.save(notice);
	}
	
	@Transactional
	public Iterable<NoticeFormDto> getNoticeList() {
		
		Iterable<NoticeFormDto> notice_list = noticeRepository.findByOrderByIdxDesc();
		
		return notice_list;
	}
	
	@Transactional
	public NoticeFormDto getNoticeDetail(Long idx) {
		
		noticeRepository.updateHit(idx);
		
//		Notice notice = noticeRepository.findByIdx(idx);
		Notice notice = noticeRepository.findById(idx).orElseThrow(EntityNotFoundException::new);
		
		return NoticeFormDto.of(notice);
	}
	
	@Transactional
	public void noticeUpdate(NoticeFormDto noticeFormDto) {

		Notice notice = noticeFormDto.createNotice();
		
		noticeRepository.save(notice);
	}
	
	@Transactional
	public Page<NoticeSearchFormDto> getNoticeListPage(Pageable pageable) {

		return noticeRepository.getSubNoticePage(pageable);
	}
	
	@Transactional
	public Page<Notice> getNoticeListPage2(Pageable pageable) {
		
		return noticeRepository.findAll(pageable);
		
	}
	
	@Transactional
	public NoticeFormDto getNoticeView(Long idx) {
		
		 Notice noticeEntity = noticeRepository.findByIdx(idx);
		
		 NoticeFormDto notice = NoticeFormDto.of(noticeEntity);
		 
		return notice;
	}
	
}
