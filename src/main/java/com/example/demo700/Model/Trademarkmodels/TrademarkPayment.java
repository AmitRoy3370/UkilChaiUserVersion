package com.example.demo700.Model.Trademarkmodels;

import java.io.Serializable;
import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "TrademarkPayment")
public class TrademarkPayment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 852L;

	@Id
	private String id;

	@NonNull
	@Indexed
	private String senderUserId;

	@NonNull
	@Indexed
	private String senderPhoneNumber;

	private final String receiverPhoneNumber = "+8801874648472";

	@NonNull
	@Indexed
	private String tradeMarkId;

	@NonNull
	@Indexed(unique = true)
	private String transactionId;

	@Indexed
	private double amount;

	private Instant sendingTime = Instant.now();

	public TrademarkPayment(String senderUserId, String senderPhoneNumber, String tradeMarkId, String transactionId,
			double amount) {
		super();
		this.senderUserId = senderUserId;

		this.senderPhoneNumber = senderPhoneNumber;
		this.tradeMarkId = tradeMarkId;
		this.transactionId = transactionId;
		this.amount = amount;
	}

	public TrademarkPayment() {
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

	public String getTradeMarkId() {
		return tradeMarkId;
	}

	public void setTradeMarkId(String tradeMarkId) {
		this.tradeMarkId = tradeMarkId;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
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
		return "TrademarkPayment [id=" + id + ", senderUserId=" + senderUserId + ", senderPhoneNumber="
				+ senderPhoneNumber + ", receiverPhoneNumber=" + receiverPhoneNumber + ", tradeMarkId=" + tradeMarkId
				+ ", transactionId=" + transactionId + ", amount=" + amount + ", sendingTime=" + sendingTime + "]";
	}

}
