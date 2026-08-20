package com.example.demo700.DTOFiles;

import java.io.Serializable;
import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "CompanyInformationPayment")
public class CompanyPaymentResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1712L;

	@Id
	private String id;

	@NonNull
	@Indexed
	private String companyId, companyName;

	@NonNull
	@Indexed
	private String senderUserId, senderUserName;

	@NonNull
	@Indexed
	private String senderPhoneNumber;

	private final String receiverPhoneNumber = "+8801874648472";

	@NonNull
	@Indexed(unique = true)
	private String transactionId;

	@NonNull
	private double amount;

	@NonNull
	private Instant sendingTime = Instant.now();

	public CompanyPaymentResponse(String id, String cmpanyId, String companyName, String senderUserId,
			String senderUserName, String senderPhoneNumber, String transactionId, double amount, Instant sendingTime) {
		super();
		this.id = id;
		this.companyId = cmpanyId;
		this.companyName = companyName;
		this.senderUserId = senderUserId;
		this.senderUserName = senderUserName;
		this.senderPhoneNumber = senderPhoneNumber;
		this.transactionId = transactionId;
		this.amount = amount;
		this.sendingTime = sendingTime;
	}

	public CompanyPaymentResponse() {
		super();
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getSenderUserName() {
		return senderUserName;
	}

	public void setSenderUserName(String senderUserName) {
		this.senderUserName = senderUserName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCmpanyId() {
		return companyId;
	}

	public void setCmpanyId(String cmpanyId) {
		this.companyId = cmpanyId;
	}

	public String getSenderUserId() {
		return senderUserId;
	}

	public void setSenderUserId(String senderUserId) {
		this.senderUserId = senderUserId;
	}

	public String getSenderPhoneNumber() {
		return senderPhoneNumber;
	}

	public void setSenderPhoneNumber(String senderPhoneNumber) {
		this.senderPhoneNumber = senderPhoneNumber;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getReceiverPhoneNumber() {
		return receiverPhoneNumber;
	}

	public Instant getSendingTime() {
		return sendingTime;
	}

	public void setSendingTime(Instant sendingTime) {
		this.sendingTime = sendingTime;
	}

	@Override
	public String toString() {
		return "CompanyPaymentResponse [id=" + id + ", cmpanyId=" + companyId + ", senderUserId=" + senderUserId
				+ ", senderPhoneNumber=" + senderPhoneNumber + ", receiverPhoneNumber=" + receiverPhoneNumber
				+ ", transactionId=" + transactionId + ", amount=" + amount + ", sendingTime=" + sendingTime + "]";
	}

}
