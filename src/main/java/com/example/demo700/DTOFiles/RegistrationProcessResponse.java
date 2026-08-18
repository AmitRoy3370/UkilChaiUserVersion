package com.example.demo700.DTOFiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RegistrationProcessResponse implements Serializable {

	private static final long serialVersionUID = 1054L;

	private String id;

	private String companyId, companyName;

	private String advocateId, advocateName;

	private String userId, userName;

	private boolean status;

	private double shareValuePerShare;

	private List<String> steps = new ArrayList<>();

	public RegistrationProcessResponse(String id, String companyId, String companyName, String advocateId,
			String advocateName, String userId, String userName, boolean status, double shareValuePerShare,
			List<String> steps) {
		super();
		this.id = id;
		this.companyId = companyId;
		this.companyName = companyName;
		this.advocateId = advocateId;
		this.advocateName = advocateName;
		this.userId = userId;
		this.userName = userName;
		this.status = status;
		this.shareValuePerShare = shareValuePerShare;
		this.steps = steps;
	}

	public RegistrationProcessResponse() {
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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAdvocateId() {
		return advocateId;
	}

	public void setAdvocateId(String advocateId) {
		this.advocateId = advocateId;
	}

	public String getAdvocateName() {
		return advocateName;
	}

	public void setAdvocateName(String advocateName) {
		this.advocateName = advocateName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
		return "RegistrationProcessResponse [id=" + id + ", companyId=" + companyId + ", companyName=" + companyName
				+ ", advocateId=" + advocateId + ", advocateName=" + advocateName + ", userId=" + userId + ", userName="
				+ userName + ", status=" + status + ", shareValuePerShare=" + shareValuePerShare + ", steps=" + steps
				+ "]";
	}

}
