package com.example.demo700.Model.UserModels;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "ClientFeedback")
public class ClientFeedback {

	@Id
	private String id;

	@NonNull
	private String feedback;

	@NonNull
	private String userId;

	@NonNull
	private String caseId;

	public ClientFeedback(String feedback, String userId, String caseId) {
		super();
		this.feedback = feedback;
		this.userId = userId;
		this.caseId = caseId;
	}

	public ClientFeedback() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	@Override
	public String toString() {
		return "ClientFeedback [id=" + id + ", feedback=" + feedback + ", userId=" + userId + ", caseId=" + caseId
				+ "]";
	}

}
