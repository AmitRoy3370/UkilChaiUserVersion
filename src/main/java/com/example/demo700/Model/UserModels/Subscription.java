package com.example.demo700.Model.UserModels;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "Subscription")
public class Subscription {

	@Id
	private String id;

	@Indexed
	@NonNull
	private String companyId;

	@NonNull
	@Indexed
	private String SubscriberName;

	@Indexed
	@NonNull
	private int numberOfShare;

	@Indexed
	private String signatureId;

	public Subscription(String companyId, String subscriberName, int numberOfShare, String signatureId) {
		super();
		this.companyId = companyId;
		SubscriberName = subscriberName;
		this.numberOfShare = numberOfShare;
		this.signatureId = signatureId;
	}

	public Subscription() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getSubscriberName() {
		return SubscriberName;
	}

	public void setSubscriberName(String subscriberName) {
		SubscriberName = subscriberName;
	}

	public int getNumberOfShare() {
		return numberOfShare;
	}

	public void setNumberOfShare(int numberOfShare) {
		this.numberOfShare = numberOfShare;
	}

	public String getSignatureId() {
		return signatureId;
	}

	public void setSignatureId(String signatureId) {
		this.signatureId = signatureId;
	}

	@Override
	public String toString() {
		return "Subscription [id=" + id + ", companyId=" + companyId + ", SubscriberName=" + SubscriberName
				+ ", numberOfShare=" + numberOfShare + ", signatureId=" + signatureId + "]";
	}

}
