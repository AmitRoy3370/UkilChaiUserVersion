package com.example.demo700.Model.UserModels;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "Director")
public class Director implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 51L;

	@Id
	private String id;

	@NonNull
	@Indexed
	private String userId;

	@Indexed
	private String position, fullName, fatherName, motherName, nidNumber, mobileNumber, email;

	@NonNull
	private String nid;

	public Director(String userId, String position, String fullName, String fatherName, String motherName,
			String nidNumber, String mobileNumber, String email, String nid) {
		super();
		this.userId = userId;
		this.position = position;
		this.fullName = fullName;
		this.fatherName = fatherName;
		this.motherName = motherName;
		this.nidNumber = nidNumber;
		this.mobileNumber = mobileNumber;
		this.email = email;
		this.nid = nid;
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

	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
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

	public String getNidNumber() {
		return nidNumber;
	}

	public void setNidNumber(String nidNumber) {
		this.nidNumber = nidNumber;
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

	@Override
	public String toString() {
		return "Director [id=" + id + ", userId=" + userId + ", position=" + position + ", fullName=" + fullName
				+ ", fatherName=" + fatherName + ", motherName=" + motherName + ", nidNumber=" + nidNumber
				+ ", mobileNumber=" + mobileNumber + ", email=" + email + ", nid=" + nid + "]";
	}

}
