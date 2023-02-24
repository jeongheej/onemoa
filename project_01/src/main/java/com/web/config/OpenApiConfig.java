package com.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
public class OpenApiConfig {
	
	@Value("${open.api.public.data.servicekey}")
	public String serviceKey;
	
	@Value("${open.api.public.data.url}")
	public String url;
	
}
