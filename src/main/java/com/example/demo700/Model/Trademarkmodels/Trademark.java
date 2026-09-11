package com.example.demo700.Model.Trademarkmodels;

import java.io.Serializable;

import java.util.List;
import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "Trademark")
public class Trademark implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 850L;

	@Id
	private String id;

	@NonNull
	@Indexed
	private String legalProtection;

	@NonNull
	@Indexed
	private String nationWiseValidity;

	@NonNull
	@Indexed
	private String applicationType;

	@NonNull
	@Indexed
	private String applicationName;

	@Indexed
	private double governmentFee;

	@Indexed
	@NonNull
	private String organaizationalName;

	@Indexed
	@NonNull
	private String trademarkName;

	@Indexed
	@NonNull
	private String trademarkType;

	@Indexed
	@NonNull
	private String classOfGoods;

	@Indexed
	@NonNull
	private String adress;

	@Indexed(unique = true)
	@NonNull
	private String email;

	@Indexed(unique = true)
	@NonNull
	private String mobileNumber;

	@Indexed
	private List<String> documents = new ArrayList<>();

	@Indexed
	@NonNull
	private String userId;

	public Trademark(String legalProtection, String nationWiseValidity, String applicationType, String applicationName,
			double governmentFee, String organaizationalName, String trademarkName, String trademarkType,
			String classOfGoods, String adress, String email, String mobileNumber, List<String> documents,
			String userId) {
		super();
		this.legalProtection = legalProtection;
		this.nationWiseValidity = nationWiseValidity;
		this.applicationType = applicationType;
		this.applicationName = applicationName;
		this.governmentFee = governmentFee;
		this.organaizationalName = organaizationalName;
		this.trademarkName = trademarkName;
		this.trademarkType = trademarkType;
		this.classOfGoods = classOfGoods;
		this.adress = adress;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.documents = documents;
		this.userId = userId;
	}

	public Trademark() {
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
		return "Trademark [id=" + id + ", legalProtection=" + legalProtection + ", nationWiseValidity="
				+ nationWiseValidity + ", applicationType=" + applicationType + ", applicationName=" + applicationName
				+ ", governmentFee=" + governmentFee + ", organaizationalName=" + organaizationalName
				+ ", trademarkName=" + trademarkName + ", trademarkType=" + trademarkType + ", classOfGoods="
				+ classOfGoods + ", adress=" + adress + ", email=" + email + ", mobileNumber=" + mobileNumber
				+ ", documents=" + documents + ", userId=" + userId + "]";
	}

}
