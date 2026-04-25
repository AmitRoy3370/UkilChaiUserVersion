package com.example.demo700.DTOFiles;

import java.util.List;

import com.example.demo700.ENums.AdvocateSpeciality;

public class PostResponse {

	private String id;

	private String advocateId, advocateName;

	private AdvocateSpeciality postType;

	private String postContent;

	private String attachmentId;

	private List<PostReactionResponse> reactions;

	public PostResponse(String id, String advocateId, String advocateName, AdvocateSpeciality postType,
			String postContent, String attachmentId, List<PostReactionResponse> reactions) {
		super();
		this.id = id;
		this.advocateId = advocateId;
		this.advocateName = advocateName;
		this.postType = postType;
		this.postContent = postContent;
		this.attachmentId = attachmentId;
		this.reactions = reactions;
	}

	public PostResponse() {
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

	public String getAdvocateName() {
		return advocateName;
	}

	public void setAdvocateName(String advocateName) {
		this.advocateName = advocateName;
	}

	public AdvocateSpeciality getPostType() {
		return postType;
	}

	public void setPostType(AdvocateSpeciality postType) {
		this.postType = postType;
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

	public List<PostReactionResponse> getReactions() {
		return reactions;
	}

	public void setReactions(List<PostReactionResponse> reactions) {
		this.reactions = reactions;
	}

	@Override
	public String toString() {
		return "PostResponse [id=" + id + ", advocateId=" + advocateId + ", advocateName=" + advocateName
				+ ", postType=" + postType + ", postContent=" + postContent + ", attachmentId=" + attachmentId
				+ ", reactions=" + reactions + "]";
	}

}
