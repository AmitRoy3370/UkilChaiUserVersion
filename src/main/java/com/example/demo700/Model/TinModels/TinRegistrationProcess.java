package com.example.demo700.Model.TinModels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "TinRegistrationProcess")
public class TinRegistrationProcess implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 61L;

	@Id
	private String id;

	@NonNull
	@Indexed
	private String centerAdminId;

	@NonNull
	@Indexed
	private String advocateId;

	@NonNull
	@Indexed(unique = true)
	private String tinId;

	private List<String> steps = new ArrayList<>();

	public TinRegistrationProcess(String centerAdminId, String advocateId, String tinId, List<String> steps) {
		super();
		this.centerAdminId = centerAdminId;
		this.advocateId = advocateId;
		this.tinId = tinId;
		this.steps = steps;
	}

	public TinRegistrationProcess() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCenterAdminId() {
		return centerAdminId;
	}

	public void setCenterAdminId(String centerAdminId) {
		this.centerAdminId = centerAdminId;
	}

	public String getAdvocateId() {
		return advocateId;
	}

	public void setAdvocateId(String advocateId) {
		this.advocateId = advocateId;
	}

	public String getTinId() {
		return tinId;
	}

	public void setTinId(String tinId) {
		this.tinId = tinId;
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
		return "TinRegistrationProcess [id=" + id + ", centerAdminId=" + centerAdminId + ", advocateId=" + advocateId
				+ ", tinId=" + tinId + ", steps=" + steps + "]";
	}

}
