package com.example.demo700.Model.RJSCModels;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "RJSC")
public class RJSC implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8050L;

	@Id
	private String id;

	@NonNull
	@Indexed
	private String userId;

	@NonNull
	@Indexed
	private String compilenceService;

	@NonNull
	@Indexed
	private String registrationNo;

	@NonNull
	@Indexed(unique = true)
	private String email;

	@NonNull
	@Indexed
	private String companyName;

	@NonNull
	@Indexed
	private Instant year = Instant.now();

	@Indexed
	private List<String> documents = new ArrayList<>();

	public RJSC(String userId, String compilenceService, String registrationNo, String email, String companyName,
			Instant year, List<String> documents) {
		super();
		this.userId = userId;
		this.compilenceService = compilenceService;
		this.registrationNo = registrationNo;
		this.email = email;
		this.companyName = companyName;
		this.year = year;
		this.documents = documents;
	}

	public RJSC() {
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "RJSC [id=" + id + ", userId=" + userId + ", compilenceService=" + compilenceService
				+ ", registrationNo=" + registrationNo + ", email=" + email + ", companyName=" + companyName + ", year="
				+ year + ", documents=" + documents + "]";
	}

}
