package com.example.demo700.Model.CopyrightModels;

import java.io.Serializable;
import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "CopyrightPayment")
public class CopyrightPayment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 652L;

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

	@NonNull
	@Indexed(unique = true)
	private String transactionId;

	@NonNull
	@Indexed
	private String copyrightId;

	@Indexed
	private Instant sendingTime = Instant.now();

	private final String receiverPhoneNumber = "+8801874648472";
	
	@Indexed
	private double amount;

	public CopyrightPayment(String senderUserId, String senderUserName, String senderPhoneNumber, String transactionId,
			String copyrightId, Instant sendingTime, double amount) {
		super();
		this.senderUserId = senderUserId;
		this.senderUserName = senderUserName;
		this.senderPhoneNumber = senderPhoneNumber;
		this.transactionId = transactionId;
		this.copyrightId = copyrightId;
		this.sendingTime = sendingTime;
		this.amount = amount;
	}

	public CopyrightPayment() {
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

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getCopyrightId() {
		return copyrightId;
	}

	public void setCopyrightId(String copyrightId) {
		this.copyrightId = copyrightId;
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

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "CopyrightPayment [id=" + id + ", senderUserId=" + senderUserId + ", senderUserName=" + senderUserName
				+ ", senderPhoneNumber=" + senderPhoneNumber + ", transactionId=" + transactionId + ", copyrightId="
				+ copyrightId + ", sendingTime=" + sendingTime + ", receiverPhoneNumber=" + receiverPhoneNumber
				+ ", amount=" + amount + "]";
	}

}
