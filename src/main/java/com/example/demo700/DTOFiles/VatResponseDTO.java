package com.example.demo700.DTOFiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VatResponseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 754L;

	private String id;

	private String userId, userName;

	private String adress;

	private String tinNo;

	private String buisnessName;

	private String tradeLicenseNo;

	private String annualTurnOver;

	private String mainProduct;

	private String natureOfBuisness;

	private int numberOfBuisness;

	private int numberOfEmployee;

	private List<String> documents = new ArrayList<>();

	private VatRegistrationProcessResponseDTO vatRegistrationProcessResponseDTO;

	public VatResponseDTO(String id, String userId, String userName, String adress, String tinNo, String buisnessName,
			String tradeLicenseNo, String annualTurnOver, String mainProduct, String natureOfBuisness,
			int numberOfBuisness, int numberOfEmployee, List<String> documents,
			VatRegistrationProcessResponseDTO vatRegistrationProcessResponseDTO) {
		super();
		this.id = id;
		this.userId = userId;
		this.userName = userName;
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
		this.vatRegistrationProcessResponseDTO = vatRegistrationProcessResponseDTO;
	}

	public VatResponseDTO() {
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public VatRegistrationProcessResponseDTO getVatRegistrationProcessResponseDTO() {
		return vatRegistrationProcessResponseDTO;
	}

	public void setVatRegistrationProcessResponseDTO(
			VatRegistrationProcessResponseDTO vatRegistrationProcessResponseDTO) {
		this.vatRegistrationProcessResponseDTO = vatRegistrationProcessResponseDTO;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "VatResponseDTO [id=" + id + ", userId=" + userId + ", userName=" + userName + ", adress=" + adress
				+ ", tinNo=" + tinNo + ", buisnessName=" + buisnessName + ", tradeLicenseNo=" + tradeLicenseNo
				+ ", annualTurnOver=" + annualTurnOver + ", mainProduct=" + mainProduct + ", natureOfBuisness="
				+ natureOfBuisness + ", numberOfBuisness=" + numberOfBuisness + ", numberOfEmployee=" + numberOfEmployee
				+ ", documents=" + documents + ", vatRegistrationProcessResponseDTO="
				+ vatRegistrationProcessResponseDTO + "]";
	}

}
