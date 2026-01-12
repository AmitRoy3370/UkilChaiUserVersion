package com.example.demo700.Services.UserServices;

import java.util.List;

import com.example.demo700.ENums.PostReactions;
import com.example.demo700.Model.UserModels.PostReaction;

public interface PostReactionService {

	public PostReaction addPostReaction(PostReaction postReaction, String userId);

	public PostReaction updatePostReaction(PostReaction postReaction, String userId, String postReactionId);

	public List<PostReaction> findByUserId(String userId);

	public List<PostReaction> findByAdvocatePostId(String advocatePostId);

	public List<PostReaction> findByAdvocatePostIdAndPostReaction(String advocatePostId, PostReactions postReaction);

	public List<PostReaction> findByCommentContainingIgnoreCase(String comment);

	public List<PostReaction> findAll();
	
	public PostReaction findById(String id);
	
	public boolean removePostReaction(String id, String userId);
	
}
