package com.example.demo700.DTOFiles;

import java.io.Serializable;
import java.util.List;

import com.example.demo700.Model.UserModels.Capital;
import com.example.demo700.Model.UserModels.RegistrationProcess;
import com.example.demo700.Model.UserModels.Subscription;

public class CompanyResponse implements Serializable {

	private static final long serialVersionUID = 1050L;

	private String id;

	private String companyName;

	private String type, natureOfBuisness, category;

	private String officeRegistryId;

	private List<String> shareHolders, documents, directorsId;

	private List<String> shareHoldersName, directorsName;

	private String authorized;

	private List<String> capital;

	private String creatorId, creatorName;

	private List<Capital> capitals;
	
	List<Subscription> subscriptions;

	private RegistrationProcess registrationProcess;

	public CompanyResponse(String id, String companyName, String type, String natureOfBuisness, String category,
			String officeRegistryId, List<String> shareHolders, List<String> documents, List<String> directorsId,
			List<String> shareHoldersName, List<String> directorsName, String authorized, List<String> capital,
			String creatorId, String creatorName, List<Capital> capitals, RegistrationProcess registrationProcess, List<Subscription> subscriptions) {
		super();
		this.id = id;
		this.companyName = companyName;
		this.type = type;
		this.natureOfBuisness = natureOfBuisness;
		this.category = category;
		this.officeRegistryId = officeRegistryId;
		this.shareHolders = shareHolders;
		this.documents = documents;
		this.directorsId = directorsId;
		this.shareHoldersName = shareHoldersName;
		this.directorsName = directorsName;
		this.authorized = authorized;
		this.capital = capital;
		this.creatorId = creatorId;
		this.creatorName = creatorName;
		this.capitals = capitals;
		this.registrationProcess = registrationProcess;
		this.subscriptions = subscriptions;
	}

	public CompanyResponse() {
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

	public void setDirectorsId(List<String> directorsId) {
		this.directorsId = directorsId;
	}

	public List<String> getShareHoldersName() {
		return shareHoldersName;
	}

	public void setShareHoldersName(List<String> shareHoldersName) {
		this.shareHoldersName = shareHoldersName;
	}

	public List<String> getDirectorsName() {
		return directorsName;
	}

	public void setDirectorsName(List<String> directorsName) {
		this.directorsName = directorsName;
	}

	public String getAuthorized() {
		return authorized;
	}

	public void setAuthorized(String authorized) {
		this.authorized = authorized;
	}

	public List<String> getCapital() {
		return capital;
	}

	public void setCapital(List<String> capital) {
		this.capital = capital;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public List<Capital> getCapitals() {
		return capitals;
	}

	public void setCapitals(List<Capital> capitals) {
		this.capitals = capitals;
	}

	public RegistrationProcess getRegistrationProcess() {
		return registrationProcess;
	}

	public void setRegistrationProcess(RegistrationProcess registrationProcess) {
		this.registrationProcess = registrationProcess;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<Subscription> getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(List<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
	}

	@Override
	public String toString() {
		return "CompanyResponse [id=" + id + ", companyName=" + companyName + ", type=" + type + ", natureOfBuisness="
				+ natureOfBuisness + ", category=" + category + ", officeRegistryId=" + officeRegistryId
				+ ", shareHolders=" + shareHolders + ", documents=" + documents + ", directorsId=" + directorsId
				+ ", shareHoldersName=" + shareHoldersName + ", directorsName=" + directorsName + ", authorized="
				+ authorized + ", capital=" + capital + ", creatorId=" + creatorId + ", creatorName=" + creatorName
				+ ", capitals=" + capitals + ", subscriptions=" + subscriptions + ", registrationProcess="
				+ registrationProcess + "]";
	}

}
