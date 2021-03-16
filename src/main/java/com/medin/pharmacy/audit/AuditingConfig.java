package com.medin.pharmacy.audit;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

class SpringSecurityAuditAwareImpl implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			return null;
		}
		if (authentication.getPrincipal().equals("anonymousUser")) {
			return Optional.ofNullable("anonymousUser");
		} else {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			return Optional.ofNullable(userDetails.getUsername());
		}
	}
}

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AuditingConfig {

	@Bean
	public AuditorAware<String> auditorAware() {
		return new SpringSecurityAuditAwareImpl();
	}
}