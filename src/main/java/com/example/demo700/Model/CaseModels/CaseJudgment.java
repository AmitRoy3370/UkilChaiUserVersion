package com.example.demo700.Model.CaseModels;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "CaseJudgement")
public class CaseJudgment {

	@Id
	private String id;

	@NonNull
	private String caseId;

	@NonNull
	private String result;

	private String judgmentAttachmentId;

	private Instant date = Instant.now();

	public CaseJudgment(String caseId, String result, String judgmentAttachmentId, Instant date) {
		super();
		this.caseId = caseId;
		this.result = result;
		this.judgmentAttachmentId = judgmentAttachmentId;
		this.date = date;
	}

	public CaseJudgment() {
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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getJudgmentAttachmentId() {
		return judgmentAttachmentId;
	}

	public void setJudgmentAttachmentId(String judgmentAttachmentId) {
		this.judgmentAttachmentId = judgmentAttachmentId;
	}

	public Instant getDate() {
		return date;
	}

	public void setDate(Instant date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "CaseJudgment [id=" + id + ", caseId=" + caseId + ", result=" + result + ", judgmentAttachmentId="
				+ judgmentAttachmentId + ", date=" + date + "]";
	}

}
