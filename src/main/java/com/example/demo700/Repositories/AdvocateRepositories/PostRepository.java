package com.example.demo700.Repositories.AdvocateRepositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo700.ENums.AdvocateSpeciality;
import com.example.demo700.Model.AdvocateModels.AdvocatePost;

@Repository
public interface PostRepository extends MongoRepository<AdvocatePost, String> {

	// Advocate ID দিয়ে posts খুঁজে বের করা
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
	
}
