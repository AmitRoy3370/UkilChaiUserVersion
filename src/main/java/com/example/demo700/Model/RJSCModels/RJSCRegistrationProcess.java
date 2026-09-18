package com.example.demo700.Model.RJSCModels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "RJSCRegistrationProcess")
public class RJSCRegistrationProcess implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8051L;

	@Id
	private String id;

	@NonNull
	@Indexed
	private String userId;

	@NonNull
	@Indexed
	private String advocateId;

	@Indexed
	private boolean status;

	@NonNull
	@Indexed(unique = true)
	private String rjscId;

	private List<String> steps = new ArrayList<>();

	public RJSCRegistrationProcess(String userId, String advocateId, boolean status, String rjscId,
			List<String> steps) {
		super();
		this.userId = userId;
		this.advocateId = advocateId;
		this.status = status;
		this.rjscId = rjscId;
		this.steps = steps;
	}

	public RJSCRegistrationProcess() {
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

	public String getRjscId() {
		return rjscId;
	}

	public void setRjscId(String rjscId) {
		this.rjscId = rjscId;
	}

	@Override
	public String toString() {
		return "RJSCRegistrationProcess [id=" + id + ", userId=" + userId + ", advocateId=" + advocateId + ", status="
				+ status + ", rjscId=" + rjscId + ", steps=" + steps + "]";
	}

}
