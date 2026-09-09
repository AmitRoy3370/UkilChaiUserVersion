package com.example.demo700.Model.TradeLicenseModels;

import java.io.Serializable;
import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "TradeLicensePayment")
public class TradeLicensePayment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 803L;

	@Id
	private String id;

	@Indexed
	@NonNull
	private String senderUserId;

	@Indexed
	@NonNull
	private String senderPhoneNumber;

	private final String receiverPhoneNumber = "+8801874648472";

	@NonNull
	@Indexed(unique = true)
	private String transactionId;

	@NonNull
	private double amount;
	
	@NonNull
	private String tradeLicenseId;

	@NonNull
	private Instant sendingTime = Instant.now();

	public TradeLicensePayment(String senderUserId, String senderPhoneNumber, String transactionId, double amount,
			Instant sendingTime, String tradeLicenseId) {
		super();
		this.senderUserId = senderUserId;
		this.senderPhoneNumber = senderPhoneNumber;
		this.transactionId = transactionId;
		this.amount = amount;
		this.sendingTime = sendingTime;
		this.tradeLicenseId = tradeLicenseId;
	}

	public TradeLicensePayment() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Instant getSendingTime() {
		return sendingTime;
	}

	public void setSendingTime(Instant sendingTime) {
		this.sendingTime = sendingTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getReceiverPhoneNumber() {
		return receiverPhoneNumber;
	}

	public String getTradeLicenseId() {
		return tradeLicenseId;
	}

	public void setTradeLicenseId(String tradeLicenseId) {
		this.tradeLicenseId = tradeLicenseId;
	}

	@Override
	public String toString() {
		return "TradeLicensePayment [id=" + id + ", senderUserId=" + senderUserId + ", senderPhoneNumber="
				+ senderPhoneNumber + ", receiverPhoneNumber=" + receiverPhoneNumber + ", transactionId="
				+ transactionId + ", amount=" + amount + ", tradeLicenseId=" + tradeLicenseId + ", sendingTime="
				+ sendingTime + "]";
	}

}
