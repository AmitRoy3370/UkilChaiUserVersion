package com.example.demo700.Model.CaseModels;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "Hearings")
public class Hearing {

	@Id
	private String id;

	@NonNull
	private String caseId;

	private int hearningNumber;

	private Instant issuedDate = Instant.now();

	private String attachmentsId[];

	public Hearing(String caseId, int hearningNumber, String[] attachmentsId) {
		super();
		this.caseId = caseId;
		this.hearningNumber = hearningNumber;
		
		this.attachmentsId = attachmentsId;
	}

	public Hearing() {
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

	@Override
	public String toString() {
		return "Hearing [id=" + id + ", caseId=" + caseId + ", hearningNumber=" + hearningNumber + ", issuedDate="
				+ issuedDate + ", attachmentsId=" + Arrays.toString(attachmentsId) + "]";
	}

}
