package com.bookmyshow.main.config;


import java.time.LocalDate;

import com.bookmyshow.main.dto.EventDTO;
import com.bookmyshow.main.model.Event;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EndDateValidator implements ConstraintValidator<ValidEndDate, EventDTO> {
	 @Override
	    public boolean isValid(EventDTO eventdto, ConstraintValidatorContext context) {
	        if (eventdto.getStartDate() == null || eventdto.getEndDate() == null) {
	            return true; 
	        }
	        return !eventdto.getEndDate().isBefore(eventdto.getStartDate());
	    }
}
