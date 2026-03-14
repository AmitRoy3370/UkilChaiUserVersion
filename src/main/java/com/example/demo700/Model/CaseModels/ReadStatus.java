package com.example.demo700.Model.CaseModels;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "ReadStatus")
public class ReadStatus {

	@Id
	private String id;

	@NonNull
	private String caseId;

	@NonNull
	private String advocateId;

	@NonNull
	private String status;

	private Instant issuedTime = Instant.now();

	public ReadStatus(String caseId, String advocateId, String status) {
		super();
		this.caseId = caseId;
		this.advocateId = advocateId;
		this.status = status;
	}

	public ReadStatus() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getAdvocateId() {
		return advocateId;
	}

	public void setAdvocateId(String advocateId) {
		this.advocateId = advocateId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Instant getIssuedTime() {
		return issuedTime;
	}

	public void setIssuedTime(Instant issuedTime) {
		this.issuedTime = issuedTime;
	}

	@Override
	public String toString() {
		return "ReadStatus [id=" + id + ", caseId=" + caseId + ", advocateId=" + advocateId + ", status=" + status
				+ ", issuedTime=" + issuedTime + "]";
	}

}
