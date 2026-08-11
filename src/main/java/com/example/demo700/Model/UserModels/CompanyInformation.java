package com.example.demo700.Model.UserModels;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "CompanyInformation")
public class CompanyInformation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 50L;

	@Id
	private String id;
	@NonNull
	@Indexed(unique = true)
	private String companyName;
	@NonNull
	@Indexed
	private String type, natureOfBuisness, category;
	@Indexed
	private String officeRegistryId;
	@Indexed
	private List<String> shareHolders, documents, directorsId;
	@Indexed
	private String authorized;
	@Indexed
	private String capital;

	@Indexed
	private String creatorId;
	
	public CompanyInformation(String companyName, String type, String natureOfBuisness, String category,
			String officeRegistryId, List<String> shareHolders, List<String> documents, List<String> directors,
			String authorized, String capital, String creatorId) {
		super();
		this.companyName = companyName;
		this.type = type;
		this.natureOfBuisness = natureOfBuisness;
		this.category = category;
		this.officeRegistryId = officeRegistryId;
		this.shareHolders = shareHolders;
		this.documents = documents;
		this.directorsId = directors;
		this.authorized = authorized;
		this.capital = capital;
		this.creatorId = creatorId;
	}

	public CompanyInformation() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNatureOfBuisness() {
		return natureOfBuisness;
	}

	public void setNatureOfBuisness(String natureOfBuisness) {
		this.natureOfBuisness = natureOfBuisness;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getOfficeRegistryId() {
		return officeRegistryId;
	}

	public void setOfficeRegistryId(String officeRegistryId) {
		this.officeRegistryId = officeRegistryId;
	}

	public List<String> getShareHolders() {
		return shareHolders;
	}

	public void setShareHolders(List<String> shareHolders) {
		this.shareHolders = shareHolders;
	}

	public List<String> getDocuments() {
		return documents;
	}

	public void setDocuments(List<String> documents) {
		this.documents = documents;
	}

	public List<String> getDirectorsId() {
		return directorsId;
	}

	public void setDirectorsId(List<String> directors) {
		this.directorsId = directors;
	}

	public String getAuthorized() {
		return authorized;
	}

	public void setAuthorized(String authorized) {
		this.authorized = authorized;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	@Override
	public String toString() {
		return "CompanyInformation [id=" + id + ", companyName=" + companyName + ", type=" + type
				+ ", natureOfBuisness=" + natureOfBuisness + ", category=" + category + ", officeRegistryId="
				+ officeRegistryId + ", shareHolders=" + shareHolders + ", documents=" + documents + ", directorsId="
				+ directorsId + ", authorized=" + authorized + ", capital=" + capital + ", creatorId=" + creatorId
				+ "]";
	}

}
