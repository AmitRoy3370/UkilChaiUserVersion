package com.example.demo700.DTOFiles;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.example.demo700.Model.TinModels.Tin;

public class TinRegistrationProcessDTO implements Serializable {


	/**
	 *
	 */
	private static final long serialVersionUID = 10060L;

	private String id;

	private Tin tin;

	private String requestedUserId, requestedUserName;

	private String advocateId, advocateName;

	private String centerAdminId, centerAdminName;

	private String tinId;

	private List<String> steps = new ArrayList<>();

	public TinRegistrationProcessDTO(String id, Tin tin, String requestedUserId, String requestedUserName,
			String advocateId, String advocateName, String centerAdminId, String centerAdminName, String tinId,
			List<String> steps) {
		super();
		this.id = id;
		this.tin = tin;
		this.requestedUserId = requestedUserId;
		this.requestedUserName = requestedUserName;
		this.advocateId = advocateId;
		this.advocateName = advocateName;
		this.centerAdminId = centerAdminId;
		this.centerAdminName = centerAdminName;
		this.tinId = tinId;
		this.steps = steps;
	}

	public TinRegistrationProcessDTO() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Tin getTin() {
		return tin;
	}

	public void setTin(Tin tin) {
		this.tin = tin;
	}

	public String getRequestedUserId() {
		return requestedUserId;
	}

	public void setRequestedUserId(String requestedUserId) {
		this.requestedUserId = requestedUserId;
	}

	public String getRequestedUserName() {
		return requestedUserName;
	}

	public void setRequestedUserName(String requestedUserName) {
		this.requestedUserName = requestedUserName;
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

	public String getCenterAdminId() {
		return centerAdminId;
	}

	public void setCenterAdminId(String centerAdminId) {
		this.centerAdminId = centerAdminId;
	}

	public String getCenterAdminName() {
		return centerAdminName;
	}

	public void setCenterAdminName(String centerAdminName) {
		this.centerAdminName = centerAdminName;
	}

	public String getTinId() {
		return tinId;
	}

	public void setTinId(String tinId) {
		this.tinId = tinId;
	}

	public List<String> getSteps() {
		return steps;
	}

	public void setSteps(List<String> steps) {
		this.steps = steps;
	}

	@Override
	public String toString() {
		return "TinRegistrationProcessDTO [id=" + id + ", tin=" + tin + ", requestedUserId=" + requestedUserId
				+ ", requestedUserName=" + requestedUserName + ", advocateId=" + advocateId + ", advocateName="
				+ advocateName + ", centerAdminId=" + centerAdminId + ", centerAdminName=" + centerAdminName
				+ ", tinId=" + tinId + ", steps=" + steps + "]";
	}

}
