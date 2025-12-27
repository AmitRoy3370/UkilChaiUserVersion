package com.example.demo700.Model.QNAModels;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.demo700.ENums.AdvocateSpeciality;
import com.mongodb.lang.NonNull;

@Document(collection = "Question")
public class AskQuestion {

	@Id
	private String id;

	@NonNull
	private String userId;

	@NonNull
	private String message;

	@NonNull
	private AdvocateSpeciality questionType;
	
	private Instant postTime = Instant.now();

	private String attachmentId;

	public AskQuestion(String sender, String message, String attachmentId, AdvocateSpeciality questionType) {
		super();
		this.userId = sender;
		this.questionType = questionType;
		this.message = message;
		this.attachmentId = attachmentId;
	}

	public AskQuestion() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Instant getPostTime() {
		return postTime;
	}

	public void setPostTime(Instant postTime) {
		this.postTime = postTime;
	}

	public String getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public AdvocateSpeciality getQuestionType() {
		return questionType;
	}

	public void setQuestionType(AdvocateSpeciality questionType) {
		this.questionType = questionType;
	}

	@Override
	public String toString() {
		return "AskQuestion [id=" + id + ", userId=" + userId + ", message=" + message + ", questionType="
				+ questionType + ", postTime=" + postTime + ", attachmentId=" + attachmentId + "]";
	}

}
