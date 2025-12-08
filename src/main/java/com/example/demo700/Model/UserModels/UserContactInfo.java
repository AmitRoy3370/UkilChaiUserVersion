package com.example.demo700.Model.UserModels;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "ContactInfo")
public class UserContactInfo {

	@Id
	private String id;

	@NonNull
	private String userId;

	@NonNull
	private String email;

	@NonNull
	private String phone;

	public UserContactInfo(String userId, String email, String phone) {
		super();
		this.userId = userId;
		this.email = email;
		this.phone = phone;
	}

	public UserContactInfo() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "UserContactInfo [id=" + id + ", userId=" + userId + ", email=" + email + ", phone=" + phone + "]";
	}

}
