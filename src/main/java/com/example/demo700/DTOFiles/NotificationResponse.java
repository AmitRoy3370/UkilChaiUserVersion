package com.example.demo700.DTOFiles;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 121L;

	private String id;
	private String userId;
	private String message;
	private boolean read;

	private Instant timeStamp;

	private List<String> destinations = new ArrayList<>();
	private Map<String, String> params = new HashMap<>();

	public NotificationResponse(String id, String userId, String message, boolean read, Instant timeStamp,
			List<String> destinations, Map<String, String> params) {
		super();
		this.id = id;
		this.userId = userId;
		this.message = message;
		this.read = read;
		this.timeStamp = timeStamp;
		this.destinations = destinations;
		this.params = params;
	}

	public NotificationResponse() {
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

	public List<String> getDestinations() {
		return destinations;
	}

	public void setDestinations(List<String> destinations) {
		this.destinations = destinations;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "NotificationResponse [id=" + id + ", userId=" + userId + ", message=" + message + ", read=" + read
				+ ", timeStamp=" + timeStamp + ", destinations=" + destinations + ", params=" + params + "]";
	}

}
