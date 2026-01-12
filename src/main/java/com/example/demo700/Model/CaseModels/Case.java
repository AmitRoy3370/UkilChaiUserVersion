package com.example.demo700.Model.CaseModels;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.demo700.ENums.AdvocateSpeciality;
import com.mongodb.lang.NonNull;

@Document(collection = "Case")
public class Case {

	@Id
	private String id;

	@NonNull
	private String caseName;

	@NonNull
	private String userId;

	@NonNull
	private String advocateId;

	@NonNull
	private AdvocateSpeciality caseType;

	private String attachmentsId[];

	private Instant issuedTime = Instant.now();

	public Case(String caseName, String userId, String advocateId, AdvocateSpeciality caseType,
			String attachmentsId[]) {
		super();
		this.caseName = caseName.trim();
		this.userId = userId;
		this.advocateId = advocateId;
		this.caseType = caseType;
		this.attachmentsId = attachmentsId;
	}

	public Case() {
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAdvocateId() {
		return advocateId;
	}

	public void setAdvocateId(String advocateId) {
		this.advocateId = advocateId;
	}

	public AdvocateSpeciality getCaseType() {
		return caseType;
	}

	public void setCaseType(AdvocateSpeciality caseType) {
		this.caseType = caseType;
	}

	public String[] getAttachmentsId() {
		return attachmentsId;
	}

	public void setAttachmentId(String[] attachmentId) {
		this.attachmentsId = attachmentId;
	}

	public Instant getIssuedTime() {
		return issuedTime;
	}

	public void setIssuedTime(Instant issuedTime) {
		this.issuedTime = issuedTime;
	}

	@Override
	public String toString() {
		return "Case [id=" + id + ", caseName=" + caseName + ", userId=" + userId + ", advocateId=" + advocateId
				+ ", caseType=" + caseType + ", attachmentsId=" + Arrays.toString(attachmentsId) + ", issuedTime="
				+ issuedTime + "]";
	}

}
