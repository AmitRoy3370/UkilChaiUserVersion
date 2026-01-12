package com.example.demo700.Repositories.UserRepositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo700.ENums.PostReactions;
import com.example.demo700.Model.UserModels.PostReaction;

@Repository
public interface PostReactionRepository extends MongoRepository<PostReaction, String> {

	public List<PostReaction> findByUserId(String userId);
	
	public List<PostReaction> findByAdvocatePostId(String advocatePostId);
	
	public List<PostReaction> findByAdvocatePostIdAndPostReaction(String advocatePostId, PostReactions postReaction);
	
	public List<PostReaction> findByCommentContainingIgnoreCase(String comment);
	
}
