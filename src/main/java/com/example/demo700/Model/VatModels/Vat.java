package com.example.demo700.Model.VatModels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "Vat")
public class Vat implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 750L;

	@Id
	private String id;

	@NonNull
	@Indexed
	private String userId;

	@NonNull
	@Indexed
	private String adress;

	@NonNull
	@Indexed(unique = true)
	private String tinNo;

	@NonNull
	@Indexed
	private String buisnessName;

	@NonNull
	@Indexed(unique = true)
	private String tradeLicenseNo;

	@NonNull
	@Indexed
	private String annualTurnOver;

	@NonNull
	@Indexed
	private String mainProduct;

	@NonNull
	@Indexed
	private String natureOfBuisness;

	@NonNull
	@Indexed
	private int numberOfBuisness;

	@NonNull
	@Indexed
	private int numberOfEmployee;

	@Indexed
	private List<String> documents = new ArrayList<>();

	public Vat(String userId, String adress, String tinNo, String buisnessName, String tradeLicenseNo,
			String annualTurnOver, String mainProduct, String natureOfBuisness, int numberOfBuisness,
			int numberOfEmployee, List<String> documents) {
		super();
		this.userId = userId;
		this.adress = adress;
		this.tinNo = tinNo;
		this.buisnessName = buisnessName;
		this.tradeLicenseNo = tradeLicenseNo;
		this.annualTurnOver = annualTurnOver;
		this.mainProduct = mainProduct;
		this.natureOfBuisness = natureOfBuisness;
		this.numberOfBuisness = numberOfBuisness;
		this.numberOfEmployee = numberOfEmployee;
		this.documents = documents;
	}

	public Vat() {
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

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getTinNo() {
		return tinNo;
	}

	public void setTinNo(String tinNo) {
		this.tinNo = tinNo;
	}

	public String getBuisnessName() {
		return buisnessName;
	}

	public void setBuisnessName(String buisnessName) {
		this.buisnessName = buisnessName;
	}

	public String getTradeLicenseNo() {
		return tradeLicenseNo;
	}

	public void setTradeLicenseNo(String tradeLicenseNo) {
		this.tradeLicenseNo = tradeLicenseNo;
	}

	public String getAnnualTurnOver() {
		return annualTurnOver;
	}

	public void setAnnualTurnOver(String annualTurnOver) {
		this.annualTurnOver = annualTurnOver;
	}

	public String getMainProduct() {
		return mainProduct;
	}

	public void setMainProduct(String mainProduct) {
		this.mainProduct = mainProduct;
	}

	public String getNatureOfBuisness() {
		return natureOfBuisness;
	}

	public void setNatureOfBuisness(String natureOfBuisness) {
		this.natureOfBuisness = natureOfBuisness;
	}

	public int getNumberOfBuisness() {
		return numberOfBuisness;
	}

	public void setNumberOfBuisness(int numberOfBuisness) {
		this.numberOfBuisness = numberOfBuisness;
	}

	public int getNumberOfEmployee() {
		return numberOfEmployee;
	}

	public void setNumberOfEmployee(int numberOfEmployee) {
		this.numberOfEmployee = numberOfEmployee;
	}

	public List<String> getDocuments() {
		return documents;
	}

	public void setDocuments(List<String> documents) {
		this.documents = documents;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Vat [id=" + id + ", userId=" + userId + ", adress=" + adress + ", tinNo=" + tinNo + ", buisnessName="
				+ buisnessName + ", tradeLicenseNo=" + tradeLicenseNo + ", annualTurnOver=" + annualTurnOver
				+ ", mainProduct=" + mainProduct + ", natureOfBuisness=" + natureOfBuisness + ", numberOfBuisness="
				+ numberOfBuisness + ", numberOfEmployee=" + numberOfEmployee + ", documents=" + documents + "]";
	}

}
