package com.example.demo700.DTOFiles;

import java.io.Serializable;
import java.time.Instant;
import java.util.Arrays;

import com.example.demo700.Model.CaseModels.Case;

public class HearingResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 300001L;

	private String id;

	private String caseId;

	private int hearningNumber;

	private Instant issuedDate = Instant.now();

	private String attachmentsId[];

	private Case hearingCase;

	public HearingResponse(String id, String caseId, int hearningNumber, Instant issuedDate, String[] attachmentsId,
			Case hearingCase) {
		super();
		this.id = id;
		this.caseId = caseId;
		this.hearningNumber = hearningNumber;
		this.issuedDate = issuedDate;
		this.attachmentsId = attachmentsId;
		this.hearingCase = hearingCase;
	}

	public HearingResponse() {
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

	public int getHearningNumber() {
		return hearningNumber;
	}

	public void setHearningNumber(int hearningNumber) {
		this.hearningNumber = hearningNumber;
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

	public Case getHearingCase() {
		return hearingCase;
	}

	public void setHearingCase(Case hearingCase) {
		this.hearingCase = hearingCase;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "HearingResponse [id=" + id + ", caseId=" + caseId + ", hearningNumber=" + hearningNumber
				+ ", issuedDate=" + issuedDate + ", attachmentsId=" + Arrays.toString(attachmentsId) + ", hearingCase="
				+ hearingCase + "]";
	}

}
