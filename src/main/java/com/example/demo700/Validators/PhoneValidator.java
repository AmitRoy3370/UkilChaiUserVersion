package com.example.demo700.Validators;

public class PhoneValidator {
	
	String phoneNumber;
	
	public PhoneValidator(String phoneNumber) {
		
		this.phoneNumber = phoneNumber;
		
	}
	
	public boolean isValid() {
		
		char ch[] = this.phoneNumber.toCharArray();
		
		if(ch.length != 11) {
			
			return false;
			
		}
		
		if(ch[0] != '0' || ch[1] != '1') {
			
			return false;
			
		}
		
		for(char i : ch) {
			
			if(!Character.isDigit(i)) {
				
				return false;
				
			}
			
		}
		
		return true;
		
	}

}
