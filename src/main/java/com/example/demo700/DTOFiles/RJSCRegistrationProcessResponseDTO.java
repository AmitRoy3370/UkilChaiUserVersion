package com.example.demo700.DTOFiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.example.demo700.Model.RJSCModels.RJSC;

public class RJSCRegistrationProcessResponseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8053L;

	private String id;

	private String userId, userName;

	private String advocateId, advocateName;

	private boolean status;

	private List<String> steps = new ArrayList<>();

	private RJSC rjsc;

	public RJSCRegistrationProcessResponseDTO(String id, String userId, String userName, String advocateId,
			String advocateName, boolean status, List<String> steps, RJSC rjsc) {
		super();
		this.id = id;
		this.userId = userId;
		this.userName = userName;
		this.advocateId = advocateId;
		this.advocateName = advocateName;
		this.status = status;
		this.steps = steps;
		this.rjsc = rjsc;
	}

	public RJSCRegistrationProcessResponseDTO() {
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

	public RJSC getRjsc() {
		return rjsc;
	}

	public void setRjsc(RJSC rjsc) {
		this.rjsc = rjsc;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "RJSCRegistrationProcessResponseDTO [id=" + id + ", userId=" + userId + ", userName=" + userName
				+ ", advocateId=" + advocateId + ", advocateName=" + advocateName + ", status=" + status + ", steps="
				+ steps + ", rjsc=" + rjsc + "]";
	}

}
