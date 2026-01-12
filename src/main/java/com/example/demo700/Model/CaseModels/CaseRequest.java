package com.example.demo700.Model.CaseModels;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.demo700.ENums.AdvocateSpeciality;
import com.mongodb.lang.NonNull;

@Document(collection = "CaseRequest")
public class CaseRequest {

	@Id
	private String id;

	@NonNull
	private String caseName;

	@NonNull
	private AdvocateSpeciality caseType;

	private Instant requestDate = Instant.now();

	private String[] attachmentId;

	@NonNull
	private String userId;

	public CaseRequest(String caseName, AdvocateSpeciality caseType, String[] attachmentId, String userId) {
		super();
		this.caseName = caseName.trim();
		this.caseType = caseType;
		this.attachmentId = attachmentId;
		this.userId = userId;
	}

	public CaseRequest() {
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

	@Override
	public String toString() {
		return "CaseRequest [id=" + id + ", caseName=" + caseName + ", caseType=" + caseType + ", requestDate="
				+ requestDate + ", attachmentId=" + attachmentId + ", userId=" + userId + "]";
	}

}
