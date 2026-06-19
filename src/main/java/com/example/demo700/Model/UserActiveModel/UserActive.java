package com.example.demo700.Model.UserActiveModel;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.mongodb.lang.NonNull;

@Document(collection = "UserActive")
public class UserActive {

	@Id
	private String id;

	@NonNull
	@Indexed(unique=true)
	private String userId;

	private boolean active;

	@Field("lastActivity")
	private Date lastActivity;

	public UserActive(String userId, boolean active) {
		super();
		this.userId = userId;
		this.active = active;
		this.lastActivity = new Date();
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

	public Date getLastActivity() {
		return lastActivity;
	}

	public void setLastActivity(Date lastActivity) {
		this.lastActivity = lastActivity;
	}

	@Override
	public String toString() {
		return "UserActive [id=" + id + ", userId=" + userId + ", active=" + active + ", lastActivity=" + lastActivity
				+ "]";
	}

}
