package com.example.demo700.DTOFiles;

import java.io.Serializable;
import java.time.Instant;
import java.util.Arrays;

import com.example.demo700.Model.CaseModels.Case;

public class DocumentDraftResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 290001L;

	private String id;

	private String advocateId, caseId;

	private Instant issuedDate = Instant.now();

	private String attachmentsId[];

	private Case documentDraftedCase;

	public DocumentDraftResponse(String id, String advocateId, String caseId, Instant issuedDate,
			String[] attachmentsId, Case documentDraftedCase) {
		super();
		this.id = id;
		this.advocateId = advocateId;
		this.caseId = caseId;
		this.issuedDate = issuedDate;
		this.attachmentsId = attachmentsId;
		this.documentDraftedCase = documentDraftedCase;
	}

	public DocumentDraftResponse() {
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

	public Case getDocumentDraftedCase() {
		return documentDraftedCase;
	}

	public void setDocumentDraftedCase(Case documentDraftedCase) {
		this.documentDraftedCase = documentDraftedCase;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "DocumentDraftResponse [id=" + id + ", advocateId=" + advocateId + ", caseId=" + caseId + ", issuedDate="
				+ issuedDate + ", attachmentsId=" + Arrays.toString(attachmentsId) + ", documentDraftedCase="
				+ documentDraftedCase + "]";
	}

}
