package com.example.demo700.Model.UserModels;

import java.io.Serializable;
import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "CompanyInformationPayment")
public class CompanyRequestPayment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 712L;

	@Id
	private String id;

	@NonNull
	@Indexed
	private String companyId;

	@NonNull
	@Indexed
	private String senderUserId;

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

	public CompanyRequestPayment(String cmpanyId, String senderUserId, String senderPhoneNumber, String transactionId,
			double amount) {
		super();
		this.companyId = cmpanyId;
		this.senderUserId = senderUserId;
		this.senderPhoneNumber = senderPhoneNumber;
		this.transactionId = transactionId;
		this.amount = amount;
	}

	public CompanyRequestPayment() {
		super();
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
		return "CompanyRequestPayment [id=" + id + ", cmpanyId=" + companyId + ", senderUserId=" + senderUserId
				+ ", senderPhoneNumber=" + senderPhoneNumber + ", receiverPhoneNumber=" + receiverPhoneNumber
				+ ", transactionId=" + transactionId + ", amount=" + amount + ", sendingTime=" + sendingTime + "]";
	}

}
