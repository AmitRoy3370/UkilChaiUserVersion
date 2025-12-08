package com.example.demo700.Validators;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.regex.Pattern;

public class URLValidator {

	private final String URL_REGEX = "^(https?:\\/\\/)" + "(([\\w\\-]+\\.)+[\\w\\-]+)" + "(:\\d+)?(\\/\\S*)?$";

	private final Pattern URL_PATTERN = Pattern.compile(URL_REGEX, Pattern.CASE_INSENSITIVE);

	@SuppressWarnings("deprecation")
	public boolean isValidURL(String url) {
		if (url == null || url.trim().isEmpty()) {
			return false;
		}

		if (!URL_PATTERN.matcher(url).matches()) {
			return false;
		}

		try {
			URL _url = new URL(url);
			return true;
		} catch (MalformedURLException e) {
			System.out.println(e);
			return false;
		}
	}
	
	public boolean isValid(List<String> urls) {
		
		for(String i : urls) {
			
			if(!isValidURL(i)) {
				
				return false;
				
			}
			
		}
		
		return true;
		
	}

}
