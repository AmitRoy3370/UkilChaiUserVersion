package com.example.demo700.Model.CopyrightModels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "CopyrightRegistrationProcess")
public class CopyrightRegistrationProcess implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 651L;

	@Id
	private String id;

	@NonNull
	@Indexed(unique = true)
	private String copyrightId;

	@NonNull
	@Indexed
	private String userId;

	@NonNull
	@Indexed
	private String advocateId;

	@Indexed
	private List<String> stpes = new ArrayList<>();

	@Indexed
	private boolean status;

	public CopyrightRegistrationProcess(String copyrightId, String userId, String advocateId, List<String> stpes,
			boolean status) {
		super();
		this.copyrightId = copyrightId;
		this.userId = userId;
		this.advocateId = advocateId;
		this.stpes = stpes;
		this.status = status;
	}

	public CopyrightRegistrationProcess() {
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

	public String getAdvocateId() {
		return advocateId;
	}

	public void setAdvocateId(String advocateId) {
		this.advocateId = advocateId;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "CopyrightRegistrationProcess [id=" + id + ", copyrightId=" + copyrightId + ", userId=" + userId
				+ ", advocateId=" + advocateId + ", stpes=" + stpes + ", status=" + status + "]";
	}

}
