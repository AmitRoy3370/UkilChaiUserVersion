package com.example.demo700.Model.UserActiveModel;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "UserActive")
public class UserActive {

	@Id
	private String id;

	@NonNull
	private String userId;

	private boolean active;

	public UserActive(String userId, boolean active) {
		super();
		this.userId = userId;
		this.active = active;
	}

	public UserActive() {
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "UserActive [id=" + id + ", userId=" + userId + ", active=" + active + "]";
	}

}
