package com.example.demo700.Model.QNAModels;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "Answer")
public class AnswerQuestion {

	@Id
	private String id;
	@NonNull
	private String advocateId;

	@NonNull
	private String message;

	private Instant time = Instant.now();

	@NonNull
	private String questionId;

	private String attachmentId;

	public AnswerQuestion(String advocateId, String message, Instant time, String questionId) {
		super();
		this.advocateId = advocateId;
		this.message = message;
		this.time = time;
		this.questionId = questionId;
	}

	public AnswerQuestion() {
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

	@Override
	public String toString() {
		return "AnswerQuestion [id=" + id + ", advocateId=" + advocateId + ", message=" + message + ", time=" + time
				+ ", questionId=" + questionId + ", attachmentId=" + attachmentId + "]";
	}

}
