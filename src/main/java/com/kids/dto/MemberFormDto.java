package com.kids.dto;

import com.kids.entity.BaseTimeEntity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberFormDto {

	private String usid;

	private String usemail;

	private String usemailetc;

	private String uspw;

	private String svcapplicant;

	private String svctarget;

	private int svcage;

	private String svcgender;
	
	private String svccountry;

	private String svccity;

	
}
