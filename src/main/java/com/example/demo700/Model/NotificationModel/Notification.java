package com.example.demo700.Model.NotificationModel;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "notifications")
public class Notification {

	@Id
	private String id;
	private String userId;
	private String message;
	private boolean read;

	private Instant timeStamp;

	public Notification(String userId, String message, boolean read) {
		super();
		this.userId = userId;
		this.message = message;
		this.read = read;
		this.timeStamp = Instant.now();
	}

	public Notification() {
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public Instant getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Instant timeStamp) {
		this.timeStamp = timeStamp;
	}

	@Override
	public String toString() {
		return "Notification [id=" + id + ", userId=" + userId + ", message=" + message + ", read=" + read
				+ ", timeStamp=" + timeStamp + "]";
	}

}

