package com.example.demo700.Model.TradeLicenseModels;

import java.io.Serializable;

import java.util.List;
import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "TradeLicense")
public class TradeLicense implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 800L;

	@Id
	private String id;

	@NonNull
	@Indexed
	private String userId;

	@NonNull
	@Indexed
	private String buisnessName;

	@NonNull
	@Indexed(unique = true)
	private String mobileNumber;

	@NonNull
	@Indexed(unique = true)
	private String emailAdress;

	@NonNull
	@Indexed
	private String buisnessType;

	@NonNull
	@Indexed
	private String buisnessCategory;

	@Indexed
	private List<String> documents = new ArrayList<>();

	public TradeLicense(String userId, String buisnessName, String mobileNumber, String emailAdress,
			String buisnessType, String buisnessCategory, List<String> documents) {
		super();
		this.userId = userId;
		this.buisnessName = buisnessName;
		this.mobileNumber = mobileNumber;
		this.emailAdress = emailAdress;
		this.buisnessType = buisnessType;
		this.buisnessCategory = buisnessCategory;
		this.documents = documents;
	}

	public TradeLicense() {
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "TradeLicense [id=" + id + ", userId=" + userId + ", buisnessName=" + buisnessName + ", mobileNumber="
				+ mobileNumber + ", emailAdress=" + emailAdress + ", buisnessType=" + buisnessType
				+ ", buisnessCategory=" + buisnessCategory + ", documents=" + documents + "]";
	}

}
