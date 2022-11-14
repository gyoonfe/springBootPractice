package com.kids.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kids.dto.MemberFormDto;
import com.kids.dto.NoticeFormDto;
import com.kids.dto.NoticeSearchFormDto;
import com.kids.entity.Member;
import com.kids.entity.Notice;
import com.kids.service.MemberService;
import com.kids.service.NoticeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SubController {
	
	private final MemberService member_service;
	private final NoticeService notice_service;
	private final PasswordEncoder pwEncoder;
	
	@GetMapping("/sub/login")
	public String memberLogin() {

		return "/sub/login";		
	}
	
	@GetMapping("/sub/login/error")
	public String loginError(Model model) {
		model.addAttribute("msg", "아이디 또는 비밀번호를 확인해주세요.");
		return "/sub/login";
	}

	@GetMapping("/sub/join")
	public String memberJoin() {

		return "/sub/join";		
	}
	
	
	@GetMapping("/sub/joinForm")
	public String joinForm() {
		
		return "/sub/join_info";
	}
	
	@PostMapping("/sub/mailConfirm")
	@ResponseBody
	public Map<String, String> mailConfirm(@RequestParam("email") String email, Model model) throws Exception {
		
		String code = member_service.findByUsid(email);
		Map<String, String> rtm = new HashMap<String, String>();
		
		if( code.equals("ok") ) {
			rtm.put("result", "N");
			rtm.put("msg", "이미 가입된 회원입니다.");
		}else {
			rtm.put("result", "Y");
			rtm.put("msg", "인증메일이 발송되었습니다. 메일을 확인 후, 인증번호를 입력해주세요.");
			rtm.put("email", email);
			rtm.put("code", code);
		}

		return rtm;
	}
	
	@PostMapping("/sub/new")
	public String joinNew(MemberFormDto memberFormDto, Model model) {
		System.out.println(memberFormDto.toString());
		Member member = Member.createMember(memberFormDto, pwEncoder);
		member_service.saveMember(member);
		
		return "redirect:/";
	}
	
	@GetMapping(value = { "/sub/board", "/sub/board/{page}" } )
	public String boardList(@PathVariable Optional<Integer> page, Model model) {
		
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
		
		Page<NoticeSearchFormDto> notice_list = notice_service.getNoticeListPage(pageable);
		
		model.addAttribute("notice_list", notice_list);
		model.addAttribute("maxPage", 5);
		
		return "/sub/board";
	}
	@GetMapping(value= {"/sub/board2", "/sub/board2/{page}"})
	public String subBoardList2(@PathVariable Optional<Integer> page, Model model) {
		
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
		
		Page<Notice> notice_list =  notice_service.getNoticeListPage2(pageable);

		model.addAttribute("notice_list", notice_list);
		model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        Boolean check= notice_list.hasNext();
        model.addAttribute("check",check);
        
        System.out.println("boolean : "+check);
		
		return "/sub/board2";
	}

	
	@GetMapping("/sub/boardView/{idx}")
	public String boardView(@PathVariable("idx") Long idx, Model model) {
		
		NoticeFormDto notice = notice_service.getNoticeView(idx);
		model.addAttribute("notice", notice);
		
		return "/sub/board_view";
	}
	
}
