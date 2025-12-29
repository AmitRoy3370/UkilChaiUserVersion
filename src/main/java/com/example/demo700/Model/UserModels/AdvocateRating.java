package com.example.demo700.Model.UserModels;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "AdvocateRating")
public class AdvocateRating {

	@Id
	private String id;

	@NonNull
	private String advocateId;

	private int rating;

	@NonNull
	private String userId;

	public AdvocateRating(String advocateId, int rating, String userId) {
		super();
		this.advocateId = advocateId;
		this.rating = rating;
		this.userId = userId;
	}

	public AdvocateRating() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAdvocateId() {
		return advocateId;
	}

	public void setAdvocateId(String advocateId) {
		this.advocateId = advocateId;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "AdvocateRating [id=" + id + ", advocateId=" + advocateId + ", rating=" + rating + ", userId=" + userId
				+ "]";
	}

}
