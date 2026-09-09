package com.example.demo700.Model.TradeLicenseModels;

import java.io.Serializable;

import java.util.List;
import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "TradeLicenseRegistrationProcess")
public class TradeLicenseRegistrationProcess implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 801L;

	@Id
	private String id;

	@Indexed
	@NonNull
	private String userId;

	@Indexed
	@NonNull
	private String advocateId;

	@Indexed
	private boolean status;

	@NonNull
	@Indexed(unique = true)
	private String tradeLicenseId;

	@Indexed
	private List<String> steps = new ArrayList<>();

	public TradeLicenseRegistrationProcess(String userId, String advocateId, boolean status, String tradeLicenseId,
			List<String> steps) {
		super();
		this.userId = userId;
		this.advocateId = advocateId;
		this.status = status;
		this.tradeLicenseId = tradeLicenseId;
		this.steps = steps;
	}

	public TradeLicenseRegistrationProcess() {
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

	public String getTradeLicenseId() {
		return tradeLicenseId;
	}

	public void setTradeLicenseId(String tradeLicenseId) {
		this.tradeLicenseId = tradeLicenseId;
	}

	@Override
	public String toString() {
		return "TradeLicenseRegistrationProcess [id=" + id + ", userId=" + userId + ", advocateId=" + advocateId
				+ ", status=" + status + ", tradeLicenseId=" + tradeLicenseId + ", steps=" + steps + "]";
	}

}
