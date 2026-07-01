package com.example.demo700.DTOFiles;

import java.time.Instant;
import java.util.Arrays;

import com.example.demo700.ENums.AdvocateSpeciality;

public class CaseResponse {

	private String id;

	private String caseName;

	private String userId, userName, userFullName;

	private String advocateId, advocateName, advocateFullName;

	private AdvocateSpeciality caseType;

	private String attachmentsId[];

	private Instant issuedTime;

	public CaseResponse(String id, String caseName, String userId, String userName, String advocateId,
			String advocateName, AdvocateSpeciality caseType, String[] attachmentsId, Instant issuedTime) {
		super();
		this.id = id;
		this.caseName = caseName;
		this.userId = userId;
		this.userName = userName;
		this.advocateId = advocateId;
		this.advocateName = advocateName;
		this.caseType = caseType;
		this.attachmentsId = attachmentsId;
		this.issuedTime = issuedTime;
	}

	public CaseResponse(String id, String caseName, String userId, String userName, String userFullName,
			String advocateId, String advocateName, String advocateFullName, AdvocateSpeciality caseType,
			String[] attachmentsId, Instant issuedTime) {
		super();
		this.id = id;
		this.caseName = caseName;
		this.userId = userId;
		this.userName = userName;
		this.userFullName = userFullName;
		this.advocateId = advocateId;
		this.advocateName = advocateName;
		this.advocateFullName = advocateFullName;
		this.caseType = caseType;
		this.attachmentsId = attachmentsId;
		this.issuedTime = issuedTime;
	}

	public CaseResponse() {
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public AdvocateSpeciality getCaseType() {
		return caseType;
	}

	public void setCaseType(AdvocateSpeciality caseType) {
		this.caseType = caseType;
	}

	public String[] getAttachmentsId() {
		return attachmentsId;
	}

	public void setAttachmentsId(String[] attachmentsId) {
		this.attachmentsId = attachmentsId;
	}

	public Instant getIssuedTime() {
		return issuedTime;
	}

	public void setIssuedTime(Instant issuedTime) {
		this.issuedTime = issuedTime;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public String getAdvocateFullName() {
		return advocateFullName;
	}

	public void setAdvocateFullName(String advocateFullName) {
		this.advocateFullName = advocateFullName;
	}

	@Override
	public String toString() {
		return "CaseResponse [id=" + id + ", caseName=" + caseName + ", userId=" + userId + ", userName=" + userName
				+ ", userFullName=" + userFullName + ", advocateId=" + advocateId + ", advocateName=" + advocateName
				+ ", advocateFullName=" + advocateFullName + ", caseType=" + caseType + ", attachmentsId="
				+ Arrays.toString(attachmentsId) + ", issuedTime=" + issuedTime + "]";
	}

}
