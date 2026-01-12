package com.example.demo700.Model.CaseModels;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "CaseAppeal")
public class AppealCase {

	@Id
	private String id;

	@NonNull
	private String caseId;

	@NonNull
	private String reason;
	
	private Instant appealDate = Instant.now();

	public AppealCase(String caseId, String reason) {
		super();
		this.caseId = caseId;
		this.reason = reason;
	}

	public AppealCase() {
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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Instant getAppealDate() {
		return appealDate;
	}

	public void setAppealDate(Instant appealDate) {
		this.appealDate = appealDate;
	}

	@Override
	public String toString() {
		return "AppealCase [id=" + id + ", caseId=" + caseId + ", reason=" + reason + ", appealDate=" + appealDate
				+ "]";
	}

}
