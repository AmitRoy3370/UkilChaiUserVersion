package com.example.demo700.Services.UserServices;

import java.util.List;

import com.example.demo700.DTOFiles.PostReactionResponse;
import com.example.demo700.ENums.PostReactions;
import com.example.demo700.Model.UserModels.PostReaction;

public interface PostReactionService {

	public PostReaction addPostReaction(PostReaction postReaction, String userId);

	public PostReaction updatePostReaction(PostReaction postReaction, String userId, String postReactionId);

	public List<PostReactionResponse> findByUserId(String userId);

	public List<PostReactionResponse> findByAdvocatePostId(String advocatePostId);

	public List<PostReactionResponse> findByAdvocatePostIdAndPostReaction(String advocatePostId, PostReactions postReaction);

	public List<PostReactionResponse> findByCommentContainingIgnoreCase(String comment);

	public List<PostReactionResponse> findAll();
	
	public PostReactionResponse findById(String id);
	
	public List<PostReactionResponse> findByAdvocateIdIn(List<String> advocatePostIds);
	
	public boolean removePostReaction(String id, String userId);
	
}
