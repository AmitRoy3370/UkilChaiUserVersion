package com.example.demo700.Model.AdvocateModels;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.demo700.ENums.AdvocateSpeciality;
import com.mongodb.lang.NonNull;

@Document(collection = "Post")
public class AdvocatePost {

	@Id
	private String id;

	@NonNull
	private String advocateId;

	private AdvocateSpeciality postType;
	
	@NonNull
	private String postContent;

	private String attachmentId;

	public AdvocatePost(String advocateId, String postContent, String attachmentId, AdvocateSpeciality postType) {
		super();
		this.advocateId = advocateId;
		this.postContent = postContent;
		this.attachmentId = attachmentId;
		this.postType = postType;
	}

	public AdvocatePost() {
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

	public String getPostContent() {
		return postContent;
	}

	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}

	public String getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}

	
	
	public AdvocateSpeciality getPostType() {
		return postType;
	}

	public void setPostType(AdvocateSpeciality postType) {
		this.postType = postType;
	}

	@Override
	public String toString() {
		return "AdvocatePost [id=" + id + ", advocateId=" + advocateId + ", postType=" + postType + ", postContent="
				+ postContent + ", attachmentId=" + attachmentId + "]";
	}

}
