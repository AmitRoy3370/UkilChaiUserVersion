package com.example.demo700.DTOFiles;

import java.io.Serializable;
import java.time.Instant;

import com.example.demo700.Model.Trademarkmodels.Trademark;

public class TrademarkPaymentResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 854L;

	private String id;

	private String senderUserId, senderUserName;

	private String senderName;

	private String senderPhoneNumber, receiverPhoneNumber;

	private String transactionId;

	private String trademarkId;

	private double amount;

	private Instant sendingTime;

	private Trademark trademark;

	public TrademarkPaymentResponse(String id, String senderUserId, String senderUserName, String senderName,
			String senderPhoneNumber, String transactionId, String trademarkId, double amount, Instant sendingTime) {
		super();
		this.id = id;
		this.senderUserId = senderUserId;
		this.senderUserName = senderUserName;
		this.senderName = senderName;
		this.senderPhoneNumber = senderPhoneNumber;
		this.transactionId = transactionId;
		this.trademarkId = trademarkId;
		this.amount = amount;
		this.sendingTime = sendingTime;
	}

	public TrademarkPaymentResponse(String id, String senderUserId, String senderUserName, String senderName,
			String senderPhoneNumber, String receiverPhoneNumber, String transactionId, String trademarkId,
			double amount, Instant sendingTime, Trademark trademark) {
		super();
		this.id = id;
		this.senderUserId = senderUserId;
		this.senderUserName = senderUserName;
		this.senderName = senderName;
		this.senderPhoneNumber = senderPhoneNumber;
		this.receiverPhoneNumber = receiverPhoneNumber;
		this.transactionId = transactionId;
		this.trademarkId = trademarkId;
		this.amount = amount;
		this.sendingTime = sendingTime;
		this.trademark = trademark;
	}

	public TrademarkPaymentResponse() {
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

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
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

	public String getTrademarkId() {
		return trademarkId;
	}

	public void setTrademarkId(String trademarkId) {
		this.trademarkId = trademarkId;
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

	public void setReceiverPhoneNumber(String receiverPhoneNumber) {
		this.receiverPhoneNumber = receiverPhoneNumber;
	}

	public Trademark getTrademark() {
		return trademark;
	}

	public void setTrademark(Trademark trademark) {
		this.trademark = trademark;
	}

	@Override
	public String toString() {
		return "TrademarkPaymentResponse [id=" + id + ", senderUserId=" + senderUserId + ", senderUserName="
				+ senderUserName + ", senderName=" + senderName + ", senderPhoneNumber=" + senderPhoneNumber
				+ ", receiverPhoneNumber=" + receiverPhoneNumber + ", transactionId=" + transactionId + ", trademarkId="
				+ trademarkId + ", amount=" + amount + ", sendingTime=" + sendingTime + ", trademark=" + trademark
				+ "]";
	}

}
