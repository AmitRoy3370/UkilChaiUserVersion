package com.example.demo700.Model.CopyrightModels;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "Copyright")
public class Copyright implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 650L;

	@Id
	private String id;

	@NonNull
	@Indexed
	private String userId;

	@NonNull
	@Indexed
	private String author;

	@NonNull
	@Indexed
	private String typeOfWork;

	@NonNull
	@Indexed
	private Instant yearOfCreation = Instant.now();

	@NonNull
	@Indexed
	private String titleOfWork;

	@NonNull
	@Indexed
	private String description;

	@NonNull
	@Indexed
	private String applicationName;

	@NonNull
	@Indexed(unique = true)
	private String mobileNumber;

	@NonNull
	@Indexed(unique = true)
	private String email;

	@NonNull
	@Indexed
	private String adress;

	@Indexed
	private List<String> documents = new ArrayList<>();

	public Copyright(String userId, String author, String typeOfWork, Instant yearOfCreation, String titleOfWork,
			String description, String applicationName, String mobileNumber, String email, String adress,
			List<String> documents) {
		super();
		this.userId = userId;
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
	}

	public Copyright() {
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Copyright [id=" + id + ", userId=" + userId + ", author=" + author + ", typeOfWork=" + typeOfWork
				+ ", yearOfCreation=" + yearOfCreation + ", titleOfWork=" + titleOfWork + ", description=" + description
				+ ", applicationName=" + applicationName + ", mobileNumber=" + mobileNumber + ", email=" + email
				+ ", adress=" + adress + ", documents=" + documents + "]";
	}

}
