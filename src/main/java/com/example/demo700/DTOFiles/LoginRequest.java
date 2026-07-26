package com.example.demo700.DTOFiles;

import java.io.Serializable;

public class LoginRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 11L;
	private String userName;
	private String password;

	public LoginRequest() {
	}

	public LoginRequest(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}
}
