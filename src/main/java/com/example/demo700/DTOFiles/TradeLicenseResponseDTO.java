package com.example.demo700.DTOFiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TradeLicenseResponseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 806L;

	private String id;

	private String userId, userName;

	private String buisnessName;

	private String mobileNumber;

	private String emailAdress;

	private String buisnessType;

	private String buisnessCategory;

	private List<String> documents = new ArrayList<>();

	private TradeLicenseRegistrationProcessResponseDTO tradeLicenseRegistrationProcess;

	public TradeLicenseResponseDTO(String id, String userId, String userName, String buisnessName, String mobileNumber,
			String emailAdress, String buisnessType, String buisnessCategory, List<String> documents,
			TradeLicenseRegistrationProcessResponseDTO tradeLicenseRegistrationProcess) {
		super();
		this.id = id;
		this.userId = userId;
		this.userName = userName;
		this.buisnessName = buisnessName;
		this.mobileNumber = mobileNumber;
		this.emailAdress = emailAdress;
		this.buisnessType = buisnessType;
		this.buisnessCategory = buisnessCategory;
		this.documents = documents;
		this.tradeLicenseRegistrationProcess = tradeLicenseRegistrationProcess;
		
	}

	public TradeLicenseResponseDTO() {
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

	public String getBuisnessName() {
		return buisnessName;
	}

	public void setBuisnessName(String buisnessName) {
		this.buisnessName = buisnessName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmailAdress() {
		return emailAdress;
	}

	public void setEmailAdress(String emailAdress) {
		this.emailAdress = emailAdress;
	}

	public String getBuisnessType() {
		return buisnessType;
	}

	public void setBuisnessType(String buisnessType) {
		this.buisnessType = buisnessType;
	}

	public String getBuisnessCategory() {
		return buisnessCategory;
	}

	public void setBuisnessCategory(String buisnessCategory) {
		this.buisnessCategory = buisnessCategory;
	}

	public List<String> getDocuments() {
		return documents;
	}

	public void setDocuments(List<String> documents) {
		this.documents = documents;
	}

	public TradeLicenseRegistrationProcessResponseDTO getTradeLicenseRegistrationProcess() {
		return tradeLicenseRegistrationProcess;
	}

	public void setTradeLicenseRegistrationProcess(
			TradeLicenseRegistrationProcessResponseDTO tradeLicenseRegistrationProcess) {
		this.tradeLicenseRegistrationProcess = tradeLicenseRegistrationProcess;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "TradeLicenseResponseDTO [id=" + id + ", userId=" + userId + ", userName=" + userName + ", buisnessName="
				+ buisnessName + ", mobileNumber=" + mobileNumber + ", emailAdress=" + emailAdress + ", buisnessType="
				+ buisnessType + ", buisnessCategory=" + buisnessCategory + ", documents=" + documents
				+ ", tradeLicenseRegistrationProcess=" + tradeLicenseRegistrationProcess + "]";
	}

}
