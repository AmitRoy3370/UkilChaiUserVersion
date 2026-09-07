package com.example.demo700.Model.TinModels;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "Tin")
public class Tin implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 60L;

	@Id
	private String id;

	@NonNull
	@Indexed
	private String userId;
	
	@NonNull
	@Indexed
	private String fullName;

	@Indexed
	private String fatherName, motherName;

	@NonNull
	@Indexed(unique = true)
	private String phone;

	@NonNull
	@Indexed
	private Instant dateOfBirth;

	@Indexed
	private String presentAdress, permanentAdress;

	private List<String> documents = new ArrayList<>();

	public Tin(String fullName, String fatherName, String motherName, String phone, Instant dateOfBirth,
			String presentAdress, String permanentAdress, List<String> documents, String userId) {
		super();
		this.fullName = fullName;
		this.fatherName = fatherName;
		this.motherName = motherName;
		this.phone = phone;
		this.dateOfBirth = dateOfBirth;
		this.presentAdress = presentAdress;
		this.permanentAdress = permanentAdress;
		this.documents = documents;
		this.userId = userId;
	}

	public Tin() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Instant getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Instant dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPresentAdress() {
		return presentAdress;
	}

	public void setPresentAdress(String presentAdress) {
		this.presentAdress = presentAdress;
	}

	public String getPermanentAdress() {
		return permanentAdress;
	}

	public void setPermanentAdress(String permanentAdress) {
		this.permanentAdress = permanentAdress;
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

	@Override
	public String toString() {
		return "Tin [id=" + id + ", userId=" + userId + ", fullName=" + fullName + ", fatherName=" + fatherName
				+ ", motherName=" + motherName + ", phone=" + phone + ", dateOfBirth=" + dateOfBirth
				+ ", presentAdress=" + presentAdress + ", permanentAdress=" + permanentAdress + ", documents="
				+ documents + "]";
	}

}
