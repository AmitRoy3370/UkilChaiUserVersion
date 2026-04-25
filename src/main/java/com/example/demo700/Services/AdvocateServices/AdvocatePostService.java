package com.example.demo700.Services.AdvocateServices;

import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.DTOFiles.PostResponse;
import com.example.demo700.ENums.AdvocateSpeciality;
import com.example.demo700.Model.AdvocateModels.AdvocatePost;

public interface AdvocatePostService {

	public AdvocatePost uploadPost(AdvocatePost advocatePost, String userId, MultipartFile file);

	List<PostResponse> seeAll();
	
	List<PostResponse> findByAdvocateId(String advocateId);

	// Post Type দিয়ে posts
	List<PostResponse> findByPostType(AdvocateSpeciality postType);

	// Advocate ID + Post Type
	List<PostResponse> findByAdvocateIdAndPostType(String advocateId, AdvocateSpeciality postType);

	// Post Content এ keyword search (case insensitive)
	List<PostResponse> findByPostContentContainingIgnoreCase(String keyword);

	// Latest posts (descending order)
	@Query(value = "{}", sort = "{ 'id' : -1 }")
	List<PostResponse> findLatestPosts();

	// Has attachment posts
	List<PostResponse> findByAttachmentIdIsNotNull();
	
	public AdvocatePost updateAdvocatePost(String postId, String userId, AdvocatePost advocatePost, MultipartFile file);
	
	public PostResponse searchPost(String advocateId);
	
	public boolean deleteAdvocatePost(String postId, String userId);

}
