package com.example.demo700.Model.UserModels;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "CompanyContact")
public class CompanyContact {

	@Id
	private String id;

	@NonNull
	@Indexed
	private String contactPersonName;

	@NonNull
	@Indexed
	private String contactPersonMobile;

	@NonNull
	@Indexed
	private String contactPersonEmail;

	@NonNull
	@Indexed
	private String howDidHear;

	@Indexed
	private String anyOtherMessage;
	
	@Indexed
	@NonNull
	private String companyId;

	public CompanyContact(String contactPersonName, String contactPersonMobile, String contactPersonEmail,
			String howDidHear, String anyOtherMessage, String companyId) {
		super();
		this.contactPersonName = contactPersonName;
		this.contactPersonMobile = contactPersonMobile;
		this.contactPersonEmail = contactPersonEmail;
		this.howDidHear = howDidHear;
		this.anyOtherMessage = anyOtherMessage;
		this.companyId = companyId;
	}

	public CompanyContact() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContactPersonName() {
		return contactPersonName;
	}

	public void setContactPersonName(String contactPersonName) {
		this.contactPersonName = contactPersonName;
	}

	public String getContactPersonMobile() {
		return contactPersonMobile;
	}

	public void setContactPersonMobile(String contactPersonMobile) {
		this.contactPersonMobile = contactPersonMobile;
	}

	public String getContactPersonEmail() {
		return contactPersonEmail;
	}

	public void setContactPersonEmail(String contactPersonEmail) {
		this.contactPersonEmail = contactPersonEmail;
	}

	public String getHowDidHear() {
		return howDidHear;
	}

	public void setHowDidHear(String howDidHear) {
		this.howDidHear = howDidHear;
	}

	public String getAnyOtherMessage() {
		return anyOtherMessage;
	}

	public void setAnyOtherMessage(String anyOtherMessage) {
		this.anyOtherMessage = anyOtherMessage;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	@Override
	public String toString() {
		return "CompanyContact [id=" + id + ", contactPersonName=" + contactPersonName + ", contactPersonMobile="
				+ contactPersonMobile + ", contactPersonEmail=" + contactPersonEmail + ", howDidHear=" + howDidHear
				+ ", anyOtherMessage=" + anyOtherMessage + ", companyId=" + companyId + "]";
	}

}
