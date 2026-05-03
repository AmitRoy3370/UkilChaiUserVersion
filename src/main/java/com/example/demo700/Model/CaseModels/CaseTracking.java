package com.example.demo700.Model.CaseModels;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.demo700.ENums.CasePayment;
import com.mongodb.lang.NonNull;

@Document(collection = "CaseTracking")
public class CaseTracking {

	@Id
	private String id;

	@NonNull
	private String caseId;

	@NonNull
	private CasePayment caseStage;

	private Instant trackingTime;

	private int stageNumber = 1;

	private boolean visibility = false;

	public CaseTracking(String caseId, CasePayment caseStage, Instant trackingTime, boolean visibility) {
		super();
		this.caseId = caseId;
		this.caseStage = caseStage;
		this.trackingTime = trackingTime;
		this.visibility = visibility;
	}

	public CaseTracking() {
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

	public int getStageNumber() {
		return stageNumber;
	}

	public void setStageNumber(int stageNumber) {
		this.stageNumber = stageNumber;
	}

	public Instant getTrackingTime() {
		return trackingTime;
	}

	public void setTrackingTime(Instant trackingTime) {
		this.trackingTime = trackingTime;
	}

	public boolean isVisibility() {
		return visibility;
	}

	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}

	@Override
	public String toString() {
		return "CaseTracking [id=" + id + ", caseId=" + caseId + ", caseStage=" + caseStage + ", trackingTime="
				+ trackingTime + ", stageNumber=" + stageNumber + ", visibility=" + visibility + "]";
	}

}
