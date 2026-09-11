package com.example.demo700.Model.Trademarkmodels;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.ArrayList;

import com.mongodb.lang.NonNull;

@Document(collection = "TrademarkRegistrationProcess")
public class TrademarkRegistrationProcess implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 851L;

	@Id
	private String id;

	@NonNull
	@Indexed
	private String userId;

	@NonNull
	@Indexed
	private String advocateId;

	@NonNull
	@Indexed(unique = true)
	private String tradeMarkId;

	@NonNull
	@Indexed
	private boolean status;

	@Indexed
	private List<String> steps = new ArrayList<>();

	public TrademarkRegistrationProcess(String userId, String advocateId, String tradeMarkId, boolean status,
			List<String> steps) {
		super();
		this.userId = userId;
		this.advocateId = advocateId;
		this.tradeMarkId = tradeMarkId;
		this.status = status;
		this.steps = steps;
	}

	public TrademarkRegistrationProcess() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAdvocateId() {
		return advocateId;
	}

	public void setAdvocateId(String advocateId) {
		this.advocateId = advocateId;
	}

	public String getTradeMarkId() {
		return tradeMarkId;
	}

	public void setTradeMarkId(String tradeMarkId) {
		this.tradeMarkId = tradeMarkId;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public List<String> getSteps() {
		return steps;
	}

	public void setSteps(List<String> steps) {
		this.steps = steps;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "TrademarkRegistrationProcess [id=" + id + ", userId=" + userId + ", advocateId=" + advocateId
				+ ", tradeMarkId=" + tradeMarkId + ", status=" + status + ", steps=" + steps + "]";
	}

}
