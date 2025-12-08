package com.example.demo700.Validators;

import java.util.regex.Pattern;

public class EmailValidator {
	private final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

	public boolean isValidEmail(String email) {
		return Pattern.matches(EMAIL_REGEX, email);
	}

}
