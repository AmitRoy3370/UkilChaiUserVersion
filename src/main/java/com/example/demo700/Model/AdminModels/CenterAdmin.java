package com.example.demo700.Model.AdminModels;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "CenterAdmin")
public class CenterAdmin {

	@Id
	String id;

	@NonNull
	String userId;

	List<String> admins;

	List<String> districts;

	List<String> advocates;

	public CenterAdmin(String userId, List<String> admins, List<String> districts, List<String> advocates) {
		super();
		this.userId = userId;
		this.admins = admins;
		this.districts = districts;
		this.advocates = advocates;
	}

	public CenterAdmin() {
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

	public List<String> getAdmins() {
		return admins;
	}

	public void setAdmins(List<String> admins) {
		this.admins = admins;
	}

	public List<String> getDistricts() {
		return districts;
	}

	public void setDistricts(List<String> districts) {
		this.districts = districts;
	}

	public List<String> getAdvocates() {
		return advocates;
	}

	public void setAdvocates(List<String> advocates) {
		this.advocates = advocates;
	}

	@Override
	public String toString() {
		return "CenterAdmin [id=" + id + ", userId=" + userId + ", admins=" + admins + ", districts=" + districts
				+ ", advocates=" + advocates + "]";
	}

}
