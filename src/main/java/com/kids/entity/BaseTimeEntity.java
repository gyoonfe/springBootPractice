package com.kids.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@EntityListeners(value = {AuditingEntityListener.class}) // Entity의 공통속성 만들기 (auditing 적용하기)
@MappedSuperclass // 공통 정보가 필요할 때 사용하는 어노테이션
@Data
public abstract class BaseTimeEntity { // 공통속성 Entity는 추상 클래스로 !!

	@CreatedDate // 엔티티가 생성돼서 저장될 때 시간을 자동으로 저장
	@Column(name = "reg_dtm", updatable = false)
	private LocalDateTime regdtm;
	
	@LastModifiedDate // 엔티티의 값이 변경될 때 시간을 자동으로 저장
	@Column(name = "mod_dtm", updatable = true)
	private LocalDateTime moddtm;
	
}
