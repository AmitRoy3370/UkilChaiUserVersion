package com.example.demo700.Services.AdvocateServices;

import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.ENums.AdvocateSpeciality;
import com.example.demo700.Model.AdvocateModels.AdvocatePost;

public interface AdvocatePostService {

	public AdvocatePost uploadPost(AdvocatePost advocatePost, String userId, MultipartFile file);

	List<AdvocatePost> seeAll();
	
	List<AdvocatePost> findByAdvocateId(String advocateId);

	// Post Type দিয়ে posts
	List<AdvocatePost> findByPostType(AdvocateSpeciality postType);

	// Advocate ID + Post Type
	List<AdvocatePost> findByAdvocateIdAndPostType(String advocateId, AdvocateSpeciality postType);

	// Post Content এ keyword search (case insensitive)
	List<AdvocatePost> findByPostContentContainingIgnoreCase(String keyword);

	// Latest posts (descending order)
	@Query(value = "{}", sort = "{ 'id' : -1 }")
	List<AdvocatePost> findLatestPosts();

	// Has attachment posts
	List<AdvocatePost> findByAttachmentIdIsNotNull();
	
	public AdvocatePost updateAdvocatePost(String postId, String userId, AdvocatePost advocatePost, MultipartFile file);
	
	public boolean deleteAdvocatePost(String postId, String userId);

}
