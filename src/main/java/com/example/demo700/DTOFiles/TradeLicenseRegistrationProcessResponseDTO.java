package com.example.demo700.DTOFiles;

import java.io.Serializable;

import java.util.List;
import java.util.ArrayList;

import com.example.demo700.Model.TradeLicenseModels.TradeLicense;

public class TradeLicenseRegistrationProcessResponseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 804L;

	private String id;

	private String userId, userName;

	private String advocateId, advocateName;

	private String tradeLicenseId;

	private TradeLicense tradeLicense;

	private boolean status;

	private List<String> steps = new ArrayList<>();

	public TradeLicenseRegistrationProcessResponseDTO(String id, String userId, String userName, String advocateId,
			String advocateName, String tradeLicenseId, TradeLicense tradeLicense, boolean status, List<String> steps) {
		super();
		this.id = id;
		this.userId = userId;
		this.userName = userName;
		this.advocateId = advocateId;
		this.advocateName = advocateName;
		this.tradeLicenseId = tradeLicenseId;
		this.tradeLicense = tradeLicense;
		this.status = status;
		this.steps = steps;
	}

	public TradeLicenseRegistrationProcessResponseDTO() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getTradeLicenseId() {
		return tradeLicenseId;
	}

	public void setTradeLicenseId(String tradeLicenseId) {
		this.tradeLicenseId = tradeLicenseId;
	}

	public TradeLicense getTradeLicense() {
		return tradeLicense;
	}

	public void setTradeLicense(TradeLicense tradeLicense) {
		this.tradeLicense = tradeLicense;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
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
		return "TradeLicenseRegistrationProcessResponseDTO [id=" + id + ", userId=" + userId + ", userName=" + userName
				+ ", advocateId=" + advocateId + ", advocateName=" + advocateName + ", tradeLicenseId=" + tradeLicenseId
				+ ", tradeLicense=" + tradeLicense + ", status=" + status + ", steps=" + steps + "]";
	}

}
