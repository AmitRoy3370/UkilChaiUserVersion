package com.example.demo700.Model.CaseModels;

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

	private int stageNumber = 1;

	public CaseTracking(String caseId, CasePayment caseStage) {
		super();
		this.caseId = caseId;
		this.caseStage = caseStage;
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

	@Override
	public String toString() {
		return "CaseTracking [id=" + id + ", caseId=" + caseId + ", caseStage=" + caseStage + ", stageNumber="
				+ stageNumber + "]";
	}

}
