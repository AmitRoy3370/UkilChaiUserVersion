package com.example.demo700.DTOFiles;

import java.time.Instant;

public class AnswerResponse {

	private String id;

	private String advocateId, advocateName, advocateFullName;

	private String message;

	private Instant time = Instant.now();

	private String questionId;

	private String attachmentId;

	public AnswerResponse(String id, String advocateId, String advocateName, String message, Instant time,
			String questionId, String attachmentId) {
		super();
		this.id = id;
		this.advocateId = advocateId;
		this.advocateName = advocateName;
		this.message = message;
		this.time = time;
		this.questionId = questionId;
		this.attachmentId = attachmentId;
	}

	public AnswerResponse(String id, String advocateId, String advocateName, String advocateFullName, String message,
			Instant time, String questionId, String attachmentId) {
		super();
		this.id = id;
		this.advocateId = advocateId;
		this.advocateName = advocateName;
		this.advocateFullName = advocateFullName;
		this.message = message;
		this.time = time;
		this.questionId = questionId;
		this.attachmentId = attachmentId;
	}

	public AnswerResponse() {
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

	public String getAdvocateName() {
		return advocateName;
	}

	public void setAdvocateName(String advocateName) {
		this.advocateName = advocateName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Instant getTime() {
		return time;
	}

	public void setTime(Instant time) {
		this.time = time;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}

	public String getAdvocateFullName() {
		return advocateFullName;
	}

	public void setAdvocateFullName(String advocateFullName) {
		this.advocateFullName = advocateFullName;
	}

	@Override
	public String toString() {
		return "AnswerResponse [id=" + id + ", advocateId=" + advocateId + ", advocateName=" + advocateName
				+ ", advocateFullName=" + advocateFullName + ", message=" + message + ", time=" + time + ", questionId="
				+ questionId + ", attachmentId=" + attachmentId + "]";
	}

}
