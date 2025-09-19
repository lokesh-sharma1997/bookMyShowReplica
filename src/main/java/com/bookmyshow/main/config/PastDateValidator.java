package com.bookmyshow.main.config;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PastDateValidator implements ConstraintValidator<PastDate, String> {
	private static final Pattern DOB_PATTERN = Pattern.compile("^(0?[1-9]|1[0-2])/(0?[1-9]|[12][0-9]|3[01])/(\\d{4})$");

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || value.isEmpty()) {
			return true; // Optional field, so no validation if empty
		}

		// First check if the pattern matches
		if (!DOB_PATTERN.matcher(value).matches()) {
			return false; // Pattern doesn't match MM/DD/YYYY format
		}

		// Now validate the actual date
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		sdf.setLenient(false); // Strict parsing
		try {
			Date date = sdf.parse(value);
			return date.before(new Date()); // Date must be in the past
		} catch (Exception e) {
			return false; // Parsing failed - invalid date
		}
	}
}
