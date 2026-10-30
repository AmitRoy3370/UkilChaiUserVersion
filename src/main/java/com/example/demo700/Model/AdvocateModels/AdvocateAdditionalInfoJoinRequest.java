package com.example.demo700.Model.AdvocateModels;

import java.io.Serializable;
import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "AdvocateAdditionalInfoInJoinRequest")
public class AdvocateAdditionalInfoJoinRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 147571L;

	@Id
	private String id;

	@Indexed(unique = true)
	@NonNull
	private String advocateId;

	@Indexed
	private String parmanentAdress;

	@Indexed
	private String cityOfUpozila;

	@Indexed(unique = true)
	private String nidNumber;

	@Indexed
	private Instant dateOfBirth;

	@Indexed(unique = true)
	private String whatsAppNumber;

	public AdvocateAdditionalInfoJoinRequest(String advocateId, String parmanentAdress, String cityOfUpozila,
			String nidNumber, Instant dateOfBirth, String whatsAppNumber) {
		super();
		this.advocateId = advocateId;
		this.parmanentAdress = parmanentAdress;
		this.cityOfUpozila = cityOfUpozila;
		this.nidNumber = nidNumber;
		this.dateOfBirth = dateOfBirth;
		this.whatsAppNumber = whatsAppNumber;
	}

	public AdvocateAdditionalInfoJoinRequest() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAdvocateId() {
		return advocateId;
	}

	public void setAdvocateId(String advocateId) {
		this.advocateId = advocateId;
	}

	public String getParmanentAdress() {
		return parmanentAdress;
	}

	public void setParmanentAdress(String parmanentAdress) {
		this.parmanentAdress = parmanentAdress;
	}

	public String getCityOfUpozila() {
		return cityOfUpozila;
	}

	public void setCityOfUpozila(String cityOfUpozila) {
		this.cityOfUpozila = cityOfUpozila;
	}

	public String getNidNumber() {
		return nidNumber;
	}

	public void setNidNumber(String nidNumber) {
		this.nidNumber = nidNumber;
	}

	public Instant getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Instant dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getWhatsAppNumber() {
		return whatsAppNumber;
	}

	public void setWhatsAppNumber(String whatsAppNumber) {
		this.whatsAppNumber = whatsAppNumber;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "AdvocateAdditionalInfoJoinRequest [id=" + id + ", advocateId=" + advocateId + ", parmanentAdress="
				+ parmanentAdress + ", cityOfUpozila=" + cityOfUpozila + ", nidNumber=" + nidNumber + ", dateOfBirth="
				+ dateOfBirth + ", whatsAppNumber=" + whatsAppNumber + "]";
	}

}
