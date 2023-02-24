package com.web.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@EnableJpaAuditing
public class AuditConfig {
	@Bean
	public AuditorAware<String> auditorProvider(){
		return new AuditorAware<String>() {
			
			@Override
			public Optional<String> getCurrentAuditor() {
				Authentication authentication = 
						SecurityContextHolder.getContext().getAuthentication();
				String email = "";
				if(authentication != null) {
					email =authentication.getName();
				}
				return Optional.of(email);
			}
		};
	}
}
