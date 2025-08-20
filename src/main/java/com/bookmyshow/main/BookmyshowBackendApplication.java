package com.bookmyshow.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "BookMyShowReplica API", version = "1.0", description = "Backend APIs for BookMyShow Replica"))
public class BookmyshowBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookmyshowBackendApplication.class, args);
		System.out.println("===> BookMyShow Project Started... <===");
	}

}
