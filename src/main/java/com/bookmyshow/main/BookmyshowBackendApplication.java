package com.bookmyshow.main;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@SpringBootApplication
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@OpenAPIDefinition
@PropertySource("${swagger.file.path}")
public class BookmyshowBackendApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(BookmyshowBackendApplication.class, args);
		
		System.out.println("===> BookMyShow Project Started..... <===");

//		String key = "U29tZVNlY3JldEtleVRoYXRJc1ZlcnlTZWN1cmUhISE=";
//	    String password = "Mansi@01";
//
//	    String encrypted = AESUtil.encrypt(password, key);
//	    String decrypted = AESUtil.decrypt(encrypted, key);
//
//	    System.out.println("Encrypted: " + encrypted);
//	    System.out.println("Decrypted: " + decrypted);

	}
	@Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
