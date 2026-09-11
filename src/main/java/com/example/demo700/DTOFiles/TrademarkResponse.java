package com.example.demo700.DTOFiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import com.mongodb.lang.NonNull;

public class TrademarkResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 855L;

	private String id;

	private String legalProtection;

	private String nationWiseValidity;

	private String applicationType;

	private String applicationName;

	private double governmentFee;

	private String organaizationalName;

	private String email;

	private String mobileNumber;

	private List<String> documents = new ArrayList<>();

	private String userId, userName;

	private String trademarkName;

	private String trademarkType;

	private String classOfGoods;

	private String adress;

	private TrademarkRegistrationProcessResponse registrationProcess;

	public TrademarkResponse(String id, String legalProtection, String nationWiseValidity, String applicationType,
			String applicationName, double governmentFee, String organaizationalName, String email, String mobileNumber,
			List<String> documents, String userId, String userName,
			TrademarkRegistrationProcessResponse registrationProcess) {
		super();
		this.id = id;
		this.legalProtection = legalProtection;
		this.nationWiseValidity = nationWiseValidity;
		this.applicationType = applicationType;
		this.applicationName = applicationName;
		this.governmentFee = governmentFee;
		this.organaizationalName = organaizationalName;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.documents = documents;
		this.userId = userId;
		this.userName = userName;
		this.registrationProcess = registrationProcess;
	}

	public TrademarkResponse(String id, String legalProtection, String nationWiseValidity, String applicationType,
			String applicationName, double governmentFee, String organaizationalName, String email, String mobileNumber,
			List<String> documents, String userId, String userName, String trademarkName, String trademarkType,
			String classOfGoods, String adress, TrademarkRegistrationProcessResponse registrationProcess) {
		super();
		this.id = id;
		this.legalProtection = legalProtection;
		this.nationWiseValidity = nationWiseValidity;
		this.applicationType = applicationType;
		this.applicationName = applicationName;
		this.governmentFee = governmentFee;
		this.organaizationalName = organaizationalName;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.documents = documents;
		this.userId = userId;
		this.userName = userName;
		this.trademarkName = trademarkName;
		this.trademarkType = trademarkType;
		this.classOfGoods = classOfGoods;
		this.adress = adress;
		this.registrationProcess = registrationProcess;
	}

	public TrademarkResponse() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLegalProtection() {
		return legalProtection;
	}

	public void setLegalProtection(String legalProtection) {
		this.legalProtection = legalProtection;
	}

	public String getNationWiseValidity() {
		return nationWiseValidity;
	}

	public void setNationWiseValidity(String nationWiseValidity) {
		this.nationWiseValidity = nationWiseValidity;
	}

	public String getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public double getGovernmentFee() {
		return governmentFee;
	}

	public void setGovernmentFee(double governmentFee) {
		this.governmentFee = governmentFee;
	}

	public String getOrganaizationalName() {
		return organaizationalName;
	}

	public void setOrganaizationalName(String organaizationalName) {
		this.organaizationalName = organaizationalName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public List<String> getDocuments() {
		return documents;
	}

	public void setDocuments(List<String> documents) {
		this.documents = documents;
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

	public TrademarkRegistrationProcessResponse getRegistrationProcess() {
		return registrationProcess;
	}

	public void setRegistrationProcess(TrademarkRegistrationProcessResponse registrationProcess) {
		this.registrationProcess = registrationProcess;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getTrademarkName() {
		return trademarkName;
	}

	public void setTrademarkName(String trademarkName) {
		this.trademarkName = trademarkName;
	}

	public String getTrademarkType() {
		return trademarkType;
	}

	public void setTrademarkType(String trademarkType) {
		this.trademarkType = trademarkType;
	}

	public String getClassOfGoods() {
		return classOfGoods;
	}

	public void setClassOfGoods(String classOfGoods) {
		this.classOfGoods = classOfGoods;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	@Override
	public String toString() {
		return "TrademarkResponse [id=" + id + ", legalProtection=" + legalProtection + ", nationWiseValidity="
				+ nationWiseValidity + ", applicationType=" + applicationType + ", applicationName=" + applicationName
				+ ", governmentFee=" + governmentFee + ", organaizationalName=" + organaizationalName + ", email="
				+ email + ", mobileNumber=" + mobileNumber + ", documents=" + documents + ", userId=" + userId
				+ ", userName=" + userName + ", trademarkName=" + trademarkName + ", trademarkType=" + trademarkType
				+ ", classOfGoods=" + classOfGoods + ", adress=" + adress + ", registrationProcess="
				+ registrationProcess + "]";
	}

}
