package com.example.demo700.Model.UserModels;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.demo700.ENums.Gender;
import com.mongodb.lang.NonNull;

@Document(collection = "UserGender")
public class UserGender {

	@Id
	private String id;

	@NonNull
	@Indexed
	private String userId;

	@NonNull
	@Indexed
	private Gender gender;

	public UserGender(String userId, Gender gender) {
		super();
		this.userId = userId;
		this.gender = gender;
	}

	public UserGender() {
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

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "UserGender [id=" + id + ", userId=" + userId + ", gender=" + gender + "]";
	}

}
