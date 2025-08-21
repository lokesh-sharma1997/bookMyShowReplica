package com.bookmyshow.main;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookmyshowBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookmyshowBackendApplication.class, args);
		System.out.println("===> BookMyShow Project Started... <===");
	}
<<<<<<< HEAD
	

=======
>>>>>>> master
	@Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

<<<<<<< HEAD
=======

>>>>>>> master
}
