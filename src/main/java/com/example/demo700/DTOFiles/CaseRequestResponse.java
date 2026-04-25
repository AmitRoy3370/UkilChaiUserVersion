package com.example.demo700.DTOFiles;

import java.time.Instant;
import java.util.Arrays;

import com.example.demo700.ENums.AdvocateSpeciality;

public class CaseRequestResponse {

	private String id;

	private String caseName;

	private AdvocateSpeciality caseType;

	private Instant requestDate;

	private String[] attachmentId;

	private String userId, userName;

	private String requestedAdvocateId, requestAdvocateName;

	public CaseRequestResponse(String id, String caseName, AdvocateSpeciality caseType, Instant requestDate,
			String[] attachmentId, String userId, String userName, String requestedAdvocateId,
			String requestAdvocateName) {
		super();
		this.id = id;
		this.caseName = caseName;
		this.caseType = caseType;
		this.requestDate = requestDate;
		this.attachmentId = attachmentId;
		this.userId = userId;
		this.userName = userName;
		this.requestedAdvocateId = requestedAdvocateId;
		this.requestAdvocateName = requestAdvocateName;
	}

	public CaseRequestResponse() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public AdvocateSpeciality getCaseType() {
		return caseType;
	}

	public void setCaseType(AdvocateSpeciality caseType) {
		this.caseType = caseType;
	}

	public Instant getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Instant requestDate) {
		this.requestDate = requestDate;
	}

	public String[] getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(String[] attachmentId) {
		this.attachmentId = attachmentId;
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

	public String getRequestedAdvocateId() {
		return requestedAdvocateId;
	}

	public void setRequestedAdvocateId(String requestedAdvocateId) {
		this.requestedAdvocateId = requestedAdvocateId;
	}

	public String getRequestAdvocateName() {
		return requestAdvocateName;
	}

	public void setRequestAdvocateName(String requestAdvocateName) {
		this.requestAdvocateName = requestAdvocateName;
	}

	@Override
	public String toString() {
		return "CaseRequestResponse [id=" + id + ", caseName=" + caseName + ", caseType=" + caseType + ", requestDate="
				+ requestDate + ", attachmentId=" + Arrays.toString(attachmentId) + ", userId=" + userId + ", userName="
				+ userName + ", requestedAdvocateId=" + requestedAdvocateId + ", requestAdvocateName="
				+ requestAdvocateName + "]";
	}

}
