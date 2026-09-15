package com.example.demo700.Model.VatModels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "VatRegistrationProcess")
public class VatRegistrationProcess implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 751L;

	@Id
	private String id;

	@NonNull
	@Indexed(unique = true)
	private String vatId;

	@NonNull
	@Indexed
	private String userId;

	@NonNull
	@Indexed
	private String advocateId;

	@Indexed
	private boolean status;

	@Indexed
	private List<String> steps = new ArrayList<>();

	public VatRegistrationProcess(String vatId, String userId, String advocateId, boolean status, List<String> steps) {
		super();
		this.vatId = vatId;
		this.userId = userId;
		this.advocateId = advocateId;
		this.status = status;
		this.steps = steps;
	}

	public VatRegistrationProcess() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVatId() {
		return vatId;
	}

	public void setVatId(String vatId) {
		this.vatId = vatId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAdvocateId() {
		return advocateId;
	}

	public void setAdvocateId(String advocateId) {
		this.advocateId = advocateId;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public List<String> getSteps() {
		return steps;
	}

	public void setSteps(List<String> steps) {
		this.steps = steps;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "VatRegistrationProcess [id=" + id + ", vatId=" + vatId + ", userId=" + userId + ", advocateId="
				+ advocateId + ", status=" + status + ", steps=" + steps + "]";
	}

}
