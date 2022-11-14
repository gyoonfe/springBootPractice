package com.kids.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareImpl implements AuditorAware<String> { // 현재 로그인한 사용자의 정보를 등록자와 수정자로 지정하는 클래스

	@Override
	public Optional<String> getCurrentAuditor() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		String usid = "";
		
		if( authentication != null ) {
			usid = authentication.getName();
			System.out.println(" ==================== AuditorAwareImpl _ usid : " + usid);
		}
		
		return Optional.of(usid);
	}
	
}
