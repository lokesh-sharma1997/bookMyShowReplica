package com.bookmyshow.main.config;

import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PastDateValidator implements ConstraintValidator<PastDate, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true; // If the date is empty, we don't validate it, making it optional
        }

        // Validate the format MM/DD/YYYY first
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        sdf.setLenient(false);
        try {
            Date date = sdf.parse(value);
            return date.before(new Date()); // Check if the date is in the past
        } catch (Exception e) {
            return false; // If it's not a valid date or format, it's invalid
        }
    }
}
