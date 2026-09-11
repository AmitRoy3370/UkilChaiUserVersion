package com.example.demo700.DTOFiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.demo700.Model.Trademarkmodels.Trademark;

public class TrademarkRegistrationProcessResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 853L;

	private String id;

	private String centerAdminUserId, centerAdminUserName;

	private String advocateId, advocateName;

	private String tradeMarkId;

	private Trademark tradeMark;

	private boolean status;

	private List<String> steps = new ArrayList<>();

	public TrademarkRegistrationProcessResponse(String id, String centerAdminUserId, String centerAdminUserName,
			String advocateId, String advocateName, String tradeMarkId, Trademark tradeMark, boolean status,
			List<String> steps) {
		super();
		this.id = id;
		this.centerAdminUserId = centerAdminUserId;
		this.centerAdminUserName = centerAdminUserName;
		this.advocateId = advocateId;
		this.advocateName = advocateName;
		this.tradeMarkId = tradeMarkId;
		this.tradeMark = tradeMark;
		this.status = status;
		this.steps = steps;
	}

	public TrademarkRegistrationProcessResponse() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCenterAdminUserId() {
		return centerAdminUserId;
	}

	public void setCenterAdminUserId(String centerAdminUserId) {
		this.centerAdminUserId = centerAdminUserId;
	}

	public String getCenterAdminUserName() {
		return centerAdminUserName;
	}

	public void setCenterAdminUserName(String centerAdminUserName) {
		this.centerAdminUserName = centerAdminUserName;
	}

	public String getAdvocateId() {
		return advocateId;
	}

	public void setAdvocateId(String advocateId) {
		this.advocateId = advocateId;
	}

	public String getAdvocateName() {
		return advocateName;
	}

	public void setAdvocateName(String advocateName) {
		this.advocateName = advocateName;
	}

	public String getTradeMarkId() {
		return tradeMarkId;
	}

	public void setTradeMarkId(String tradeMarkId) {
		this.tradeMarkId = tradeMarkId;
	}

	public Trademark getTradeMark() {
		return tradeMark;
	}

	public void setTradeMark(Trademark tradeMark) {
		this.tradeMark = tradeMark;
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
		return "TrademarkRegistrationProcessResponse [id=" + id + ", centerAdminUserId=" + centerAdminUserId
				+ ", centerAdminUserName=" + centerAdminUserName + ", advocateId=" + advocateId + ", advocateName="
				+ advocateName + ", tradeMarkId=" + tradeMarkId + ", tradeMark=" + tradeMark + ", status=" + status
				+ ", steps=" + steps + "]";
	}

}
