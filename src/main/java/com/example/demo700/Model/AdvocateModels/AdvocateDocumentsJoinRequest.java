package com.example.demo700.Model.AdvocateModels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection="AdvocateDocumentsInJoinRequest")
public class AdvocateDocumentsJoinRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 147572L;

	@Id
	private String id;

	@Indexed(unique = true)
	@NonNull
	private String advocateId;

	@Indexed
	private List<String> attachments = new ArrayList<>();

	public AdvocateDocumentsJoinRequest(String advocateId, List<String> attachments) {
		super();
		this.advocateId = advocateId;
		this.attachments = attachments;
	}

	public AdvocateDocumentsJoinRequest() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAdvocateId() {
		return advocateId;
	}

	public void setAdvocateId(String advocateId) {
		this.advocateId = advocateId;
	}

	public List<String> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<String> attachments) {
		this.attachments = attachments;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "AdvocateDocumentsJoinRequest [id=" + id + ", advocateId=" + advocateId + ", attachments=" + attachments
				+ "]";
	}

}
