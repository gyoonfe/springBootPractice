package com.kids;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@SpringBootApplication
@EnableJpaAuditing // AuditingEntity 리스너 활성화를 위한 어노테이션 !!!
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class KidsProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(KidsProjectApplication.class, args);
	}

}
