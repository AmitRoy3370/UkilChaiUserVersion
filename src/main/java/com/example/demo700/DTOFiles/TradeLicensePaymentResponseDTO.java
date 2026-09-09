package com.example.demo700.DTOFiles;

import java.io.Serializable;
import java.time.Instant;

public class TradeLicensePaymentResponseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 805L;

	private String id;

	private String senderUserId, senderUserName;

	private String senderPhoneNumber, receiverPhoneNumber;

	private String transactionId;

	private Instant sendingTime = Instant.now();

	private double amount;

	private String tradeLicenseId;

	public TradeLicensePaymentResponseDTO(String id, String senderUserId, String senderUserName,
			String senderPhoneNumber, String transactionId, Instant sendingTime, double amount, String tradeLicenseId) {
		super();
		this.id = id;
		this.senderUserId = senderUserId;
		this.senderUserName = senderUserName;
		this.senderPhoneNumber = senderPhoneNumber;
		this.transactionId = transactionId;
		this.sendingTime = sendingTime;
		this.amount = amount;
		this.tradeLicenseId = tradeLicenseId;
	}

	public TradeLicensePaymentResponseDTO() {
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

	public Instant getSendingTime() {
		return sendingTime;
	}

	public void setSendingTime(Instant sendingTime) {
		this.sendingTime = sendingTime;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getTradeLicenseId() {
		return tradeLicenseId;
	}

	public void setTradeLicenseId(String tradeLicenseId) {
		this.tradeLicenseId = tradeLicenseId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getReceiverPhoneNumber() {
		return receiverPhoneNumber;
	}

	public void setReceiverPhoneNumber(String receiverPhoneNumber) {
		this.receiverPhoneNumber = receiverPhoneNumber;
	}

	@Override
	public String toString() {
		return "TradeLicensePaymentResponseDTO [id=" + id + ", senderUserId=" + senderUserId + ", senderUserName="
				+ senderUserName + ", senderPhoneNumber=" + senderPhoneNumber + ", receiverPhoneNumber="
				+ receiverPhoneNumber + ", transactionId=" + transactionId + ", sendingTime=" + sendingTime
				+ ", amount=" + amount + ", tradeLicenseId=" + tradeLicenseId + "]";
	}

}
