package com.example.demo700.Model.AdminModels;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.demo700.ENums.AdvocateSpeciality;
import com.mongodb.lang.NonNull;

@Document(collection = "Admin")
public class Admin {

	@Id
	private String id;
	@NonNull
	private String userId;

	private Set<AdvocateSpeciality> advocateSpeciality;

	public Admin(String userId, Set<AdvocateSpeciality> advocateSpeciality) {
		super();
		this.userId = userId;
		this.advocateSpeciality = advocateSpeciality;
	}

	public Admin() {
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

	public Set<AdvocateSpeciality> getAdvocateSpeciality() {
		return advocateSpeciality;
	}

	public void setAdvocateSpeciality(Set<AdvocateSpeciality> advocateSpeciality) {
		this.advocateSpeciality = advocateSpeciality;
	}

	@Override
	public String toString() {
		return "Admin [id=" + id + ", userId=" + userId + ", advocateSpeciality=" + advocateSpeciality + "]";
	}

}
