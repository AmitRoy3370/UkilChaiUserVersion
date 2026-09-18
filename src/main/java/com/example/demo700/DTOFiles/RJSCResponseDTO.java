package com.example.demo700.DTOFiles;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

public class RJSCResponseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8054L;

	private String id;

	private String userId, userName;

	private String compilenceService;

	private String registrationNo;

	private String email;

	private String companyName;

	private Instant year = Instant.now();

	private List<String> documents = new ArrayList<>();

	private RJSCRegistrationProcessResponseDTO registrationProcess;

	public RJSCResponseDTO(String id, String userId, String userName, String compilenceService, String registrationNo,
			String email, String companyName, Instant year, List<String> documents,
			RJSCRegistrationProcessResponseDTO registrationProcess) {
		super();
		this.id = id;
		this.userId = userId;
		this.userName = userName;
		this.compilenceService = compilenceService;
		this.registrationNo = registrationNo;
		this.email = email;
		this.companyName = companyName;
		this.year = year;
		this.documents = documents;
		this.registrationProcess = registrationProcess;
	}

	public RJSCResponseDTO() {
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

	public String getCompilenceService() {
		return compilenceService;
	}

	public void setCompilenceService(String compilenceService) {
		this.compilenceService = compilenceService;
	}

	public String getRegistrationNo() {
		return registrationNo;
	}

	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Instant getYear() {
		return year;
	}

	public void setYear(Instant year) {
		this.year = year;
	}

	public List<String> getDocuments() {
		return documents;
	}

	public void setDocuments(List<String> documents) {
		this.documents = documents;
	}

	public RJSCRegistrationProcessResponseDTO getRegistrationProcess() {
		return registrationProcess;
	}

	public void setRegistrationProcess(RJSCRegistrationProcessResponseDTO registrationProcess) {
		this.registrationProcess = registrationProcess;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "RJSCResponseDTO [id=" + id + ", userId=" + userId + ", userName=" + userName + ", compilenceService="
				+ compilenceService + ", registrationNo=" + registrationNo + ", email=" + email + ", companyName="
				+ companyName + ", year=" + year + ", documents=" + documents + ", registrationProcess="
				+ registrationProcess + "]";
	}

}
