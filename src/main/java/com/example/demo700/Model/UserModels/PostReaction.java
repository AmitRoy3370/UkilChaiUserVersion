package com.example.demo700.Model.UserModels;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.demo700.ENums.PostReactions;
import com.mongodb.lang.NonNull;

@Document(collection = "PostReaction")
public class PostReaction {

	@Id
	private String id;

	private PostReactions postReaction;

	private String comment;

	@NonNull
	private String userId;
	
	@NonNull
	private String advocatePostId;

	public PostReaction(PostReactions postReaction, String comment, String userId, String advocatePostId) {
		super();
		this.postReaction = postReaction;
		this.comment = comment;
		this.userId = userId;
		this.advocatePostId = advocatePostId;
	}

	public PostReaction() {
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

	public String getAdvocatePostId() {
		return advocatePostId;
	}

	public void setAdvocatePostId(String advocatePostId) {
		this.advocatePostId = advocatePostId;
	}

	@Override
	public String toString() {
		return "PostReaction [id=" + id + ", postReaction=" + postReaction + ", comment=" + comment + ", userId="
				+ userId + ", advocatePostId=" + advocatePostId + "]";
	}

}
