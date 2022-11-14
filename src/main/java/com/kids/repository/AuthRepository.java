package com.kids.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kids.entity.Auth;

public interface AuthRepository extends JpaRepository<Auth, Long> {

	@Query("SELECT a FROM Auth a WHERE a.member.usid = :usid")
	Auth findByAuthRole(@Param("usid") String usid);
	
}
