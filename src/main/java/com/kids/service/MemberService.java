package com.kids.service;

import java.util.Random;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kids.dto.MemberFormDto;
import com.kids.entity.Auth;
import com.kids.entity.Member;
import com.kids.repository.AuthRepository;
import com.kids.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
	
	private final MemberRepository member_repository;
	private final AuthRepository auth_repository;
	private final JavaMailSender javaMailSender;
	
	
	public String createCode() {
		StringBuffer key = new StringBuffer();
		Random random = new Random();
		for(int i=0; i<8; i++) {
			int index = random.nextInt(3);
			switch(index) {
			case 0 :
				key.append( (char)(int)(random.nextInt(26) + 97) );
				break;
			case 1 :
				key.append( (char)(int)(random.nextInt(26) + 65) );
				break;
			case 2 :
				key.append( random.nextInt(10) );
				break;
			}
		}
		return key.toString();
	}

	public String findByUsid(String usid) {
		
		Member member = member_repository.findByUsid(usid);
		String code = "";
		
		if( member != null ) {
			System.out.println("가입된 회원입니다 !");
			return "ok";
		}else {
			System.out.println("이메일 발송 !");
			code = createCode();
			System.out.println("CODE : " + code);
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = null;
			String msgg = "";
			msgg += "<h1>키즈쉴드 입니다.</h1>";
			msgg += "<h3>회원가입 인증 번호 입니다.</h3>";
			msgg += "CODE : <string>" + code + "</string><br>";
			try {
				helper = new MimeMessageHelper(message, true, "utf-8");
				message.setFrom(new InternetAddress("cyhs604@naver.com"));
				helper.setTo(usid);
				helper.setSubject("키즈쉴드 이메일 인증번호 입니다.");
				helper.setText(msgg, true);
			}catch(Exception e) {
				e.printStackTrace();
			}
			javaMailSender.send(message);
			return code;
		}
	}
	
	public void saveMember(Member member) {
		Member saveMember = member_repository.save(member);
		Auth auth = new Auth();
		auth.createAuth(saveMember);
		auth_repository.save(auth);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		System.out.println("====================================================");
		System.out.println("THIS IS MemberService's loadUserByUsername ! ! ! ! !");
		
		Member member = member_repository.findByUsid(username);
		Auth auth = auth_repository.findByAuthRole(username);

		if( member == null ) {
			throw new UsernameNotFoundException(username);
		}else {
			System.out.println(member.toString());
			System.out.println(auth.toString());
		}
		
		return User.builder()
				   .username(member.getUsid())
				   .password(member.getUspw())
				   .roles(auth.getAuthrole().toString())
				   .build();
	}
	
	@Transactional
	public Iterable<Member> getMemberList() {
		
		Iterable<Member> member_list = member_repository.findByOrderByUsidDesc();
		
		return member_list;
	}
	
}
