package com.bookmyshow.main;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class BookmyshowBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookmyshowBackendApplication.class, args);
		System.out.println("===> BookMyShow Project Started... <===");
		 
	}
	

	

	@Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
