package com.example.demo700.Model.UserModels;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "User")
public class User {

	@Id
	private String id;

	@NonNull
	private String name;

	private String fullName;

	@NonNull
	private String password;

	private String profileImageId;

	public User(String name, String fullName, String password, String profileImageId) {
		super();
		this.name = name;
		this.fullName = fullName;
		this.password = password;
		this.profileImageId = profileImageId;
	}
	
	public User(String name, String password, String profileImageId) {
		super();
		this.name = name;
		
		this.password = password;
		this.profileImageId = profileImageId;
	}

	public User() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProfileImageId() {
		return profileImageId;
	}

	public void setProfileImageId(String profileImageId) {
		this.profileImageId = profileImageId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", fullName=" + fullName + ", password=" + password
				+ ", profileImageId=" + profileImageId + "]";
	}

}
