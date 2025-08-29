package com.bookmyshow.main.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bookmyshow.main.response.ApiResponseWrapper;

@Configuration
public class ApiResponseConfig {

	@Bean
	public ApiResponseWrapper apiResponseWrapper() {
		return new ApiResponseWrapper();
	}
}
