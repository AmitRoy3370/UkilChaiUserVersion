package com.example.demo700.DTOFiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.demo700.Model.CopyrightModels.Copyright;

public class CopyrightRegistrationProcessResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 653L;

	private String id;

	private String copyrightId;

	private String userId, userName;

	private String advocateId, advocateName;

	private List<String> stpes = new ArrayList<>();

	private boolean status;

	private Copyright copyright;

	public CopyrightRegistrationProcessResponse(String id, String copyrightId, String userId, String userName,
			String advocateId, String advocateName, List<String> stpes, boolean status, Copyright copyright) {
		super();
		this.id = id;
		this.copyrightId = copyrightId;
		this.userId = userId;
		this.userName = userName;
		this.advocateId = advocateId;
		this.advocateName = advocateName;
		this.stpes = stpes;
		this.status = status;
		this.copyright = copyright;
	}

	public CopyrightRegistrationProcessResponse() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCopyrightId() {
		return copyrightId;
	}

	public void setCopyrightId(String copyrightId) {
		this.copyrightId = copyrightId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public List<String> getStpes() {
		return stpes;
	}

	public void setStpes(List<String> stpes) {
		this.stpes = stpes;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Copyright getCopyright() {
		return copyright;
	}

	public void setCopyright(Copyright copyright) {
		this.copyright = copyright;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "CopyrightRegistrationProcessResponse [id=" + id + ", copyrightId=" + copyrightId + ", userId=" + userId
				+ ", userName=" + userName + ", advocateId=" + advocateId + ", advocateName=" + advocateName
				+ ", stpes=" + stpes + ", status=" + status + ", copyright=" + copyright + "]";
	}

}
