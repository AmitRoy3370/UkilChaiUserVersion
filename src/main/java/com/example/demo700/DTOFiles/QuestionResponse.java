package com.example.demo700.DTOFiles;

import java.time.Instant;
import java.util.List;

import com.example.demo700.ENums.AdvocateSpeciality;

public class QuestionResponse {

	private String id;

	private String userId, userName;

	private String message;

	private AdvocateSpeciality questionType;

	private Instant postTime = Instant.now();

	private String attachmentId;
	
	private List<AnswerResponse> answers;

	public QuestionResponse(String id, String userId, String userName, String message, AdvocateSpeciality questionType,
			Instant postTime, String attachmentId, List<AnswerResponse> answers) {
		super();
		this.id = id;
		this.userId = userId;
		this.userName = userName;
		this.message = message;
		this.questionType = questionType;
		this.postTime = postTime;
		this.attachmentId = attachmentId;
		this.answers = answers;
	}

	public QuestionResponse() {
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public AdvocateSpeciality getQuestionType() {
		return questionType;
	}

	public void setQuestionType(AdvocateSpeciality questionType) {
		this.questionType = questionType;
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

	
	
	public List<AnswerResponse> getAnswers() {
		return answers;
	}

	public void setAnswers(List<AnswerResponse> answers) {
		this.answers = answers;
	}

	@Override
	public String toString() {
		return "QuestionResponse [id=" + id + ", userId=" + userId + ", userName=" + userName + ", message=" + message
				+ ", questionType=" + questionType + ", postTime=" + postTime + ", attachmentId=" + attachmentId
				+ ", answers=" + answers + "]";
	}

}
