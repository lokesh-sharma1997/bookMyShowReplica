package com.bookmyshow.main.config;


import java.time.LocalDate;

import com.bookmyshow.main.model.Event;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EndDateValidator implements ConstraintValidator<ValidEndDate, Event> {
	 @Override
	    public boolean isValid(Event event, ConstraintValidatorContext context) {
	        if (event.getStartDate() == null || event.getEndDate() == null) {
	            return true; 
	        }
	        return !event.getEndDate().isBefore(event.getStartDate());
	    }
}
