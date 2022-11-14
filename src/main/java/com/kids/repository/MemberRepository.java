package com.kids.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kids.dto.MemberFormDto;
import com.kids.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String> {

	// SELECT * FROM member WHERE us_id = usid
	Member findByUsid(String usid);
	
	Iterable<Member> findByOrderByUsidDesc();
	
}
