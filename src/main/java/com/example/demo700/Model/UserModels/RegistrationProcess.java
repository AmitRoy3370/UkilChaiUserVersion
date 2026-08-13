package com.example.demo700.Model.UserModels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "RegistrationProcess")
public class RegistrationProcess implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 54L;

	@Id
	private String id;

	@NonNull
	@Indexed(unique = true)
	private String companyId;

	@NonNull
	@Indexed
	private String advocateId;

	@NonNull
	@Indexed
	private String userId;

	@NonNull
	@Indexed
	private boolean status;

	@Indexed
	private double shareValuePerShare;

	private List<String> steps = new ArrayList<>();

	public RegistrationProcess(String companyId, String advocateId, String userId, boolean status,
			double shareValuePerShare, List<String> steps) {
		super();
		this.companyId = companyId;
		this.advocateId = advocateId;
		this.userId = userId;
		this.status = status;
		this.shareValuePerShare = shareValuePerShare;
		this.steps = steps;
	}

	public RegistrationProcess() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getAdvocateId() {
		return advocateId;
	}

	public void setAdvocateId(String advocateId) {
		this.advocateId = advocateId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public double getShareValuePerShare() {
		return shareValuePerShare;
	}

	public void setShareValuePerShare(double shareValuePerShare) {
		this.shareValuePerShare = shareValuePerShare;
	}

	public List<String> getSteps() {
		return steps;
	}

	public void setSteps(List<String> steps) {
		this.steps = steps;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "RegistrationProcess [id=" + id + ", companyId=" + companyId + ", advocateId=" + advocateId + ", userId="
				+ userId + ", status=" + status + ", shareValuePerShare=" + shareValuePerShare + ", steps=" + steps
				+ "]";
	}

}
