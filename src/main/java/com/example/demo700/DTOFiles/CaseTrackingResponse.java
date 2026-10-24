package com.example.demo700.DTOFiles;

import java.io.Serializable;
import java.time.Instant;

import com.example.demo700.ENums.CasePayment;
import com.example.demo700.Model.CaseModels.Case;

public class CaseTrackingResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 280001L;

	private String id;

	private String caseId;

	private CasePayment caseStage;

	private Instant trackingTime;

	private int stageNumber = 1;

	private boolean visibility = false;

	private Case trackingCase;

	public CaseTrackingResponse(String id, String caseId, CasePayment caseStage, Instant trackingTime, int stageNumber,
			boolean visibility, Case trackingCase) {
		super();
		this.id = id;
		this.caseId = caseId;
		this.caseStage = caseStage;
		this.trackingTime = trackingTime;
		this.stageNumber = stageNumber;
		this.visibility = visibility;
		this.trackingCase = trackingCase;
	}

	public CaseTrackingResponse() {
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

	public CasePayment getCaseStage() {
		return caseStage;
	}

	public void setCaseStage(CasePayment caseStage) {
		this.caseStage = caseStage;
	}

	public Instant getTrackingTime() {
		return trackingTime;
	}

	public void setTrackingTime(Instant trackingTime) {
		this.trackingTime = trackingTime;
	}

	public int getStageNumber() {
		return stageNumber;
	}

	public void setStageNumber(int stageNumber) {
		this.stageNumber = stageNumber;
	}

	public boolean isVisibility() {
		return visibility;
	}

	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}

	public Case getTrackingCase() {
		return trackingCase;
	}

	public void setTrackingCase(Case trackingCase) {
		this.trackingCase = trackingCase;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "CaseTrackingResponse [id=" + id + ", caseId=" + caseId + ", caseStage=" + caseStage + ", trackingTime="
				+ trackingTime + ", stageNumber=" + stageNumber + ", visibility=" + visibility + ", trackingCase="
				+ trackingCase + "]";
	}

}
