package com.exchange.reactive;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleCORSFilter implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {

		registry.addMapping("/**")
				.allowedOrigins("*")
				.allowedMethods("DELETE", "GET", "OPTIONS", "PATCH", "POST", "PUT")
				.allowedHeaders("Origin", "Accept", "Content-Type", "Authorization", "credential", "X-XSRF-TOKEN","uuid")
//				.allowCredentials(true)
				.maxAge(3600);

	}
}
