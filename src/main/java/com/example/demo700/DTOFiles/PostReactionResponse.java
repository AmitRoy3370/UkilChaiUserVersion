package com.example.demo700.DTOFiles;

import com.example.demo700.ENums.PostReactions;

public class PostReactionResponse {

	private String id;

	private PostReactions postReaction;

	private String comment;

	private String userId, userName, fullName;

	private String advocatePostId;

	public PostReactionResponse(String id, PostReactions postReaction, String comment, String userId, String userName,
			String advocatePostId) {
		super();
		this.id = id;
		this.postReaction = postReaction;
		this.comment = comment;
		this.userId = userId;
		this.userName = userName;
		this.advocatePostId = advocatePostId;
	}

	public PostReactionResponse(String id, PostReactions postReaction, String comment, String userId, String userName,
			String fullName, String advocatePostId) {
		super();
		this.id = id;
		this.postReaction = postReaction;
		this.comment = comment;
		this.userId = userId;
		this.userName = userName;
		this.fullName = fullName;
		this.advocatePostId = advocatePostId;
	}

	public PostReactionResponse() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PostReactions getPostReaction() {
		return postReaction;
	}

	public void setPostReaction(PostReactions postReaction) {
		this.postReaction = postReaction;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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

	public String getAdvocatePostId() {
		return advocatePostId;
	}

	public void setAdvocatePostId(String advocatePostId) {
		this.advocatePostId = advocatePostId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Override
	public String toString() {
		return "PostReactionResponse [id=" + id + ", postReaction=" + postReaction + ", comment=" + comment
				+ ", userId=" + userId + ", userName=" + userName + ", fullName=" + fullName + ", advocatePostId="
				+ advocatePostId + "]";
	}

}
