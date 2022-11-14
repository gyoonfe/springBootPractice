package com.kids.entity;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@EntityListeners(value = {AuditingEntityListener.class}) // Entity의 공통속성 만들기 (auditing 적용하기)
@MappedSuperclass // 공통 정보가 필요할 때 사용하는 어노테이션
@Data
public abstract class BaseEntity extends BaseTimeEntity{ // 등록일, 수정일, 등록자, 수정자를 갖는 Entity

	@CreatedBy
	@Column(updatable = false)
	private String regid;
	
	@LastModifiedBy
	private String modid;
	
}
