package com.bookmyshow.main.config;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bookmyshow.main.util.AESUtil;

public class Base64PasswordValidator implements ConstraintValidator<ValidBase64Password, String> {

	private static final Pattern PASSWORD_PATTERN = Pattern
			.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?!.*\\s).{8,20}$");

	@Override
	public boolean isValid(String encodedPassword, ConstraintValidatorContext context) {
		if (encodedPassword == null || encodedPassword.isEmpty()) {
			return false;
		}

		try {
			String key = "U29tZVNlY3JldEtleVRoYXRJc1ZlcnlTZWN1cmUhISE=";
	    String decrypted = AESUtil.decrypt(encodedPassword, key);
//			byte[] decodedBytes = Base64.getDecoder().decode(encodedPassword);
//			String decodedPassword = new String(decrypted);
//			System.out.println("decodedPassword" + decrypted);
			Matcher matcher = PASSWORD_PATTERN.matcher(decrypted);
			return matcher.matches();
		} catch (Exception e) {
			return false;
		}
	}
}
