package com.example.demo700.Model.PaymentModels;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.demo700.ENums.CasePayment;
import com.mongodb.lang.NonNull;

@Document(collection = "PaymentDetails")
public class PaymentDetails {

	@Id
	private String id;

	@NonNull
	private CasePayment paymentFor;

	private double price;

	@NonNull
	private String userId;

	@NonNull
	private String caseId;

	public PaymentDetails(CasePayment paymentFor, double price, String userId, String caseId) {
		super();
		this.paymentFor = paymentFor;
		this.price = price;
		this.userId = userId;
		this.caseId = caseId;
	}

	public PaymentDetails() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public CasePayment getPaymentFor() {
		return paymentFor;
	}

	public void setPaymentFor(CasePayment paymentFor) {
		this.paymentFor = paymentFor;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	@Override
	public String toString() {
		return "PaymentDetails [id=" + id + ", paymentFor=" + paymentFor + ", price=" + price + ", userId=" + userId
				+ ", caseId=" + caseId + "]";
	}

}
