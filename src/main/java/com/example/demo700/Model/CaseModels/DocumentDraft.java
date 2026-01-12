package com.example.demo700.Model.CaseModels;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "DocumentDraft")
public class DocumentDraft {

	@Id
	private String id;

	@NonNull
	private String advocateId, caseId;

	private Instant issuedDate = Instant.now();

	private String attachmentsId[];

	public DocumentDraft(String advocateId, String caseId, Instant issuedDate, String[] attachmentsId) {
		super();
		this.advocateId = advocateId;
		this.caseId = caseId;
		this.issuedDate = issuedDate;
		this.attachmentsId = attachmentsId;
	}

	public DocumentDraft() {
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

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public Instant getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(Instant issuedDate) {
		this.issuedDate = issuedDate;
	}

	public String[] getAttachmentsId() {
		return attachmentsId;
	}

	public void setAttachmentsId(String[] attachmentsId) {
		this.attachmentsId = attachmentsId;
	}

	@Override
	public String toString() {
		return "DocumentDraft [id=" + id + ", advocateId=" + advocateId + ", caseId=" + caseId + ", issuedDate="
				+ issuedDate + ", attachmentsId=" + Arrays.toString(attachmentsId) + "]";
	}

}
