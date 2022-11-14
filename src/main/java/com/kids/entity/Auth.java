package com.kids.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.kids.constant.Role;

import lombok.Data;

@Entity
@Table(name = "auth")
@Data
public class Auth {

	@Id
	@Column(name = "idx")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idx;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "auth_role")
	private Role authrole;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "usid") // 연관관계 = Foreign Key를 설정하는 것 !!!
	private Member member;
	
	private LocalDateTime regdtm;
	
	public void createAuth(Member member) {
		this.setAuthrole(Role.USER);
		this.setRegdtm(LocalDateTime.now());
		this.setMember(member);
	}
	
}
