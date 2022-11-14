package com.kids.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kids.dto.MemberFormDto;
import com.kids.dto.NoticeFormDto;
import com.kids.entity.Member;
import com.kids.service.MemberService;
import com.kids.service.NoticeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AdminController {

	private final NoticeService noticeService;
	private final MemberService memberService;
	
	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminMain() {
		return "/admin/index";
	}
	
	@GetMapping("/admin/noticeList")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminNoticeList() {
		return "/admin/noticeList";
	}
	
	@PostMapping("/admin/noticeList_process")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	public Iterable<NoticeFormDto> adminNoticeListAjax() {
		
		Iterable<NoticeFormDto> notice_list = noticeService.getNoticeList();
		
		return notice_list;
	}

	@GetMapping("/admin/noticeReg")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminNoticeReg() {
		return "/admin/noticeReg";
	}
	
	@PostMapping("/admin/noticeReg_process")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminNoticeReg_process(NoticeFormDto noticeFormDto) {
		
		System.out.println("=======================================");
		System.out.println(noticeFormDto.toString());
		
		noticeService.saveNotice(noticeFormDto);
		
		return "redirect:/admin/noticeList";
	}
	
	@GetMapping("/admin/notice/{idx}")
	@PreAuthorize("hasRole('ADMIN')")
	public String noticeDetail(@PathVariable("idx") Long idx, Model model) {
		
		NoticeFormDto noticeFormDto = noticeService.getNoticeDetail(idx);
		
		model.addAttribute("notice", noticeFormDto);
		return "/admin/noticeMod";
	}
	
	@PostMapping("/admin/noticeMod_process")
	@PreAuthorize("hasRole('ADMIN')")
	public String noticeUpdaate(NoticeFormDto noticeFormDto, RedirectAttributes rttr) {
		
		if( noticeFormDto.getSubject() == null || noticeFormDto.getContent() == null ) {
			rttr.addFlashAttribute("errorMessage", "제목 또는 내용을 확인해주세요.");
			System.out.println("업데이트 실패 !!!!!");
			return "/admin/noticeMod/" + noticeFormDto.getIdx();
		}
		if( noticeFormDto.getDelyn() == null ) {
			noticeFormDto.setDelyn("N");
		}
		
		noticeService.noticeUpdate(noticeFormDto);
		
		return "redirect:/admin/noticeList";
	}
	
	@GetMapping("/admin/memberList")
	@PreAuthorize("hasRole('ADMIN')")
	public String memberList() {

		return "/admin/memberList";
	}
	
	@PostMapping("/admin/memberList_process")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	public Iterable<Member> memberList_process() {
		
		Iterable<Member> notice_list = memberService.getMemberList();
		
		return notice_list;
	}
	
}
