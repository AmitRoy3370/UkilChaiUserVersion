package com.example.demo700.Model.CaseModels;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "ClosedCase")
public class CaseClose {

	@Id
	private String id;

	@NonNull
	private String caseId;

	private Instant closedDate = Instant.now();

	private boolean open;
	
	public CaseClose(String caseId, boolean open) {
		super();
		this.caseId = caseId;
		this.open = open;
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

	public Instant getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Instant closedDate) {
		this.closedDate = closedDate;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	@Override
	public String toString() {
		return "CaseClose [id=" + id + ", caseId=" + caseId + ", closedDate=" + closedDate + ", open=" + open + "]";
	}

}
