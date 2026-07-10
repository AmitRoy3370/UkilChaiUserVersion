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

	private String postTitle;

	@NonNull
	private String postContent;

	private String attachmentId;

	public AdvocatePost(String advocateId, String postContent, String attachmentId, AdvocateSpeciality postType,
			String postTitle) {
		super();
		this.advocateId = advocateId;
		this.postContent = postContent;
		this.attachmentId = attachmentId;
		this.postType = postType;
		this.postTitle = postTitle;
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

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;

	}

	public String getPostTitle() {
		return this.postTitle;
	}

	@Override
	public String toString() {
		return "AdvocatePost [id=" + id + ", advocateId=" + advocateId + ", postType=" + postType + ", postContent="
				+ postContent + ", attachmentId=" + attachmentId + "postTitle=" + postTitle + "]";
	}

}
