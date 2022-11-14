package com.kids.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.kids.constant.Role;
import com.kids.dto.MemberFormDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member extends BaseEntity {

	@Id
	@Column(name = "us_id")
	private String usid;
	
	@Column(name = "us_email")
	private String usemail;
	
	@Column(name = "us_email_etc")
	private String usemailetc;
	
	@Column(name = "us_pw")
	private String uspw;
	
	@Column(name = "svc_applicant")
	private String svcapplicant;
	
	@Column(name = "svc_target")
	private String svctarget;
	
	@Column(name = "svc_age")
	private int svcage;
	
	@Column(name = "svc_gender")
	private String svcgender;
	
	@Column(name = "svc_country")
	private String svccountry;
	
	@Column(name = "svc_city")
	private String svccity;
	
	@Column(name = "out_yn")
	private String outyn;
	
	@Column(name = "out_dtm")
	private LocalDateTime outdtm;
	
	public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder pwEncoder) {
		Member member = new Member();
		
		member.setUsid(memberFormDto.getUsid());
		member.setUsemail(memberFormDto.getUsemail());
		member.setUsemailetc(memberFormDto.getUsemailetc());
		member.setUspw( pwEncoder.encode(memberFormDto.getUspw()) );
		member.setSvcapplicant(memberFormDto.getSvcapplicant());
		member.setSvctarget(memberFormDto.getSvctarget());
		member.setSvcage(memberFormDto.getSvcage());
		member.setSvcgender(memberFormDto.getSvcgender());
		member.setSvccountry(memberFormDto.getSvccountry());
		member.setSvccity(memberFormDto.getSvccity());
		member.setOutyn("N");
		member.setOutdtm(null);
		
		return member;
	}
	
}
