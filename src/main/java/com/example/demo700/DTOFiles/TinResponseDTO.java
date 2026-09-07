package com.example.demo700.DTOFiles;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class TinResponseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1060L;
	private String id;
	private String userId, userName;
	private String fullName, fatherName, motherName;
	private String phone;
	private Instant dateOfBirth;
	private String presentAdress, permanentAdress;
	private List<String> documents = new ArrayList<>();
	private TinRegistrationProcessDTO registrationProcess;

	public TinResponseDTO(String id, String userId, String userName, String fullName, String fatherName,
			String motherName, String phone, Instant dateOfBirth, String presentAdress, String permanentAdress,
			List<String> documents, TinRegistrationProcessDTO registrationProcess) {
		super();
		this.id = id;
		this.userId = userId;
		this.userName = userName;
		this.fullName = fullName;
		this.fatherName = fatherName;
		this.motherName = motherName;
		this.phone = phone;
		this.dateOfBirth = dateOfBirth;
		this.presentAdress = presentAdress;
		this.permanentAdress = permanentAdress;
		this.documents = documents;
		this.registrationProcess = registrationProcess;
	}

	public TinResponseDTO() {
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

	public TinRegistrationProcessDTO getRegistrationProcess() {
		return registrationProcess;
	}

	public void setRegistrationProcess(TinRegistrationProcessDTO registrationProcess) {
		this.registrationProcess = registrationProcess;
	}

	@Override
	public String toString() {
		return "TinResponseDTO [id=" + id + ", userId=" + userId + ", userName=" + userName + ", fullName=" + fullName
				+ ", fatherName=" + fatherName + ", motherName=" + motherName + ", phone=" + phone + ", dateOfBirth="
				+ dateOfBirth + ", presentAdress=" + presentAdress + ", permanentAdress=" + permanentAdress
				+ ", documents=" + documents + ", registrationProcess=" + registrationProcess + "]";
	}

}
