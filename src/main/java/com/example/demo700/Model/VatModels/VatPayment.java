package com.example.demo700.Model.VatModels;

import java.io.Serializable;
import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "VatPayment")
public class VatPayment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 752L;

	@Id
	private String id;

	@NonNull
	@Indexed
	private String senderUserId;

	@NonNull
	@Indexed
	private String senderUserName;

	@NonNull
	@Indexed
	private String senderPhoneNumber;

	private final String receiverPhoneNumber = "+8801874648472";

	@Indexed
	private double amount;
	
	@NonNull
	@Indexed
	private String transactionId;

	@Indexed
	private Instant sendingTime = Instant.now();
	
	@NonNull
	@Indexed
	private String vatId;

	public VatPayment(String senderUserId, String senderUserName, String senderPhoneNumber, double amount,
			Instant sendingTime, String transactionId, String vatId) {
		super();
		this.senderUserId = senderUserId;
		this.senderUserName = senderUserName;
		this.senderPhoneNumber = senderPhoneNumber;
		this.amount = amount;
		this.sendingTime = sendingTime;
		this.transactionId = transactionId;
		this.vatId = vatId;
	}

	public VatPayment() {
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

	public String getSenderUserName() {
		return senderUserName;
	}

	public void setSenderUserName(String senderUserName) {
		this.senderUserName = senderUserName;
	}

	public String getSenderPhoneNumber() {
		return senderPhoneNumber;
	}

	public void setSenderPhoneNumber(String senderPhoneNumber) {
		this.senderPhoneNumber = senderPhoneNumber;
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

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getVatId() {
		return vatId;
	}

	public void setVatId(String vatId) {
		this.vatId = vatId;
	}

	@Override
	public String toString() {
		return "VatPayment [id=" + id + ", senderUserId=" + senderUserId + ", senderUserName=" + senderUserName
				+ ", senderPhoneNumber=" + senderPhoneNumber + ", receiverPhoneNumber=" + receiverPhoneNumber
				+ ", amount=" + amount + ", transactionId=" + transactionId + ", sendingTime=" + sendingTime
				+ ", vatId=" + vatId + "]";
	}

}
