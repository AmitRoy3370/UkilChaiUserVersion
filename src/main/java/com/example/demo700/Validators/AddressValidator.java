package com.example.demo700.Validators;

import java.util.List;
import java.util.regex.Pattern;

public class AddressValidator {

	private final String ADDRESS_REGEX = "^[#.0-9a-zA-Z\\s,-]+$";

	private final Pattern ADDRESS_PATTERN = Pattern.compile(ADDRESS_REGEX);

	public boolean isValidAddress(String address) {
		if (address == null || address.trim().isEmpty()) {
			return false;
		}

		if (address.length() < 5) {
			return false;
		}

		return ADDRESS_PATTERN.matcher(address).matches();
	}

	public boolean isValid(List<String> adresses) {
		
		for(String i : adresses) {
			
			if(!isValidAddress(i)) {
				
				return false;
				
			}
			
		}
		
		return true;
		
	}
	
}
