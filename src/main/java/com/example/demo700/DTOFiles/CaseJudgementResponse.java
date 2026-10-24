package com.example.demo700.DTOFiles;

import java.io.Serializable;
import java.time.Instant;

import com.example.demo700.Model.CaseModels.Case;

public class CaseJudgementResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 260001L;

	private String id;

	private String caseId;

	private String result;

	private String judgmentAttachmentId;

	private Instant date = Instant.now();

	private Case judgementCase;

	public CaseJudgementResponse(String id, String caseId, String result, String judgmentAttachmentId, Instant date,
			Case judgementCase) {
		super();
		this.id = id;
		this.caseId = caseId;
		this.result = result;
		this.judgmentAttachmentId = judgmentAttachmentId;
		this.date = date;
		this.judgementCase = judgementCase;
	}

	public CaseJudgementResponse() {
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

	public Case getJudgementCase() {
		return judgementCase;
	}

	public void setJudgementCase(Case judgementCase) {
		this.judgementCase = judgementCase;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "CaseJudgementResponse [id=" + id + ", caseId=" + caseId + ", result=" + result
				+ ", judgmentAttachmentId=" + judgmentAttachmentId + ", date=" + date + ", judgementCase="
				+ judgementCase + "]";
	}

}
