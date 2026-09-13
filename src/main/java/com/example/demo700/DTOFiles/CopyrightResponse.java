package com.example.demo700.DTOFiles;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class CopyrightResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 654L;

	private String id;
	
	private String userId, userName;

	private String author;

	private String typeOfWork;

	private Instant yearOfCreation = Instant.now();

	private String titleOfWork;

	private String description;

	private String applicationName;

	private String mobileNumber;

	private String email;

	private String adress;

	private List<String> documents = new ArrayList<>();

	private CopyrightRegistrationProcessResponse registrationProcess;

	public CopyrightResponse(String userId, String userName, String author, String typeOfWork, Instant yearOfCreation,
			String titleOfWork, String description, String applicationName, String mobileNumber, String email,
			String adress, List<String> documents, CopyrightRegistrationProcessResponse registrationProcess) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.author = author;
		this.typeOfWork = typeOfWork;
		this.yearOfCreation = yearOfCreation;
		this.titleOfWork = titleOfWork;
		this.description = description;
		this.applicationName = applicationName;
		this.mobileNumber = mobileNumber;
		this.email = email;
		this.adress = adress;
		this.documents = documents;
		this.registrationProcess = registrationProcess;
	}

	public CopyrightResponse() {
		super();
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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTypeOfWork() {
		return typeOfWork;
	}

	public void setTypeOfWork(String typeOfWork) {
		this.typeOfWork = typeOfWork;
	}

	public Instant getYearOfCreation() {
		return yearOfCreation;
	}

	public void setYearOfCreation(Instant yearOfCreation) {
		this.yearOfCreation = yearOfCreation;
	}

	public String getTitleOfWork() {
		return titleOfWork;
	}

	public void setTitleOfWork(String titleOfWork) {
		this.titleOfWork = titleOfWork;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public List<String> getDocuments() {
		return documents;
	}

	public void setDocuments(List<String> documents) {
		this.documents = documents;
	}

	public CopyrightRegistrationProcessResponse getRegistrationProcess() {
		return registrationProcess;
	}

	public void setRegistrationProcess(CopyrightRegistrationProcessResponse registrationProcess) {
		this.registrationProcess = registrationProcess;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "CopyrightResponse [id=" + id + ", userId=" + userId + ", userName=" + userName + ", author=" + author
				+ ", typeOfWork=" + typeOfWork + ", yearOfCreation=" + yearOfCreation + ", titleOfWork=" + titleOfWork
				+ ", description=" + description + ", applicationName=" + applicationName + ", mobileNumber="
				+ mobileNumber + ", email=" + email + ", adress=" + adress + ", documents=" + documents
				+ ", registrationProcess=" + registrationProcess + "]";
	}

}
