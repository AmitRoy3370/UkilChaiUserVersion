package com.example.demo700.Model.RJSCModels;

import java.io.Serializable;
import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "RJSCPayment")
public class RJSCPayment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8052L;

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

	@Indexed
	private final String receiverPhoneNumber = "+8801874648472";

	@NonNull
	@Indexed(unique = true)
	private String transactionId;

	@NonNull
	@Indexed
	private String rjscId;
	
	@Indexed
	private double amount;

	@Indexed
	private Instant sendingTime = Instant.now();

	public RJSCPayment(String senderUserId, String senderUserName, String senderPhoneNumber, String transactionId,
			double amount, Instant sendingTime, String rjscId) {
		super();
		this.senderUserId = senderUserId;
		this.senderUserName = senderUserName;
		this.senderPhoneNumber = senderPhoneNumber;
		this.transactionId = transactionId;
		this.amount = amount;
		this.sendingTime = sendingTime;
		this.rjscId = rjscId;
	}

	public RJSCPayment() {
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

	public String getRjscId() {
		return rjscId;
	}

	public void setRjscId(String rjscId) {
		this.rjscId = rjscId;
	}

	@Override
	public String toString() {
		return "RJSCPayment [id=" + id + ", senderUserId=" + senderUserId + ", senderUserName=" + senderUserName
				+ ", senderPhoneNumber=" + senderPhoneNumber + ", receiverPhoneNumber=" + receiverPhoneNumber
				+ ", transactionId=" + transactionId + ", rjscId=" + rjscId + ", amount=" + amount + ", sendingTime="
				+ sendingTime + "]";
	}

}
