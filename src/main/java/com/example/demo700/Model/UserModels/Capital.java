package com.example.demo700.Model.UserModels;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "Capital")
public class Capital implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 53L;

	@Id
	private String id;

	@NonNull
	@Indexed
	private String companyId;

	@Indexed
	private double authorizedCapital;

	@Indexed
	private int totalShare, numberOfShare;

	@Indexed
	private double shareValue;

	public Capital(String companyId, double authorizedCapital, int totalShare, int numberOfShare, double shareValue) {
		super();
		this.companyId = companyId;
		this.authorizedCapital = authorizedCapital;
		this.totalShare = totalShare;
		this.numberOfShare = numberOfShare;
		this.shareValue = shareValue;
	}

	public Capital() {
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

	public double getAuthorizedCapital() {
		return authorizedCapital;
	}

	public void setAuthorizedCapital(double authorizedCapital) {
		this.authorizedCapital = authorizedCapital;
	}

	public int getTotalShare() {
		return totalShare;
	}

	public void setTotalShare(int totalShare) {
		this.totalShare = totalShare;
	}

	public int getNumberOfShare() {
		return numberOfShare;
	}

	public void setNumberOfShare(int numberOfShare) {
		this.numberOfShare = numberOfShare;
	}

	public double getShareValue() {
		return shareValue;
	}

	public void setShareValue(double shareValue) {
		this.shareValue = shareValue;
	}

	@Override
	public String toString() {
		return "Capital [id=" + id + ", companyId=" + companyId + ", authorizedCapital=" + authorizedCapital
				+ ", totalShare=" + totalShare + ", numberOfShare=" + numberOfShare + ", shareValue=" + shareValue
				+ "]";
	}

}
