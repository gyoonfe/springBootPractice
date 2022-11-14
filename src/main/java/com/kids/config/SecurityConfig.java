package com.kids.config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.kids.service.MemberService;

@Configuration
@EnableWebSecurity
// extends WebSecurityConfigureAdapter = 2.7.~ 이하 버전에서는 지원하지 않음
// 5.7 버전 이후로는 @EnableWebSecurity 사용
public class SecurityConfig {

//	@Autowired
//	MemberService member_service;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// 인증, 인가, 보안과 관련된 것들을 관리
		
		http.formLogin()
			.loginPage("/sub/login")
			.defaultSuccessUrl("/")
			.usernameParameter("usid")
			.failureUrl("/sub/login/error")
			.and()
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/sub/logout"))
			.logoutSuccessUrl("/");
		
		http.authorizeHttpRequests()
			.mvcMatchers("/", "/public/**", "/sub/**", "/images/**", "/download/**").permitAll()
			.mvcMatchers("/admin/**").hasRole("ADMIN")
			.anyRequest().authenticated();
		
		// authenticationEntryPoint = 인증되지 않은 사용자의 리소스 접근권한을 처리.
		http.exceptionHandling()
		    .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
		    .accessDeniedHandler(new CustomAccessDeniedHandler());
		
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() { // 암호화 처리
		return new BCryptPasswordEncoder();
	}
	
	
}
