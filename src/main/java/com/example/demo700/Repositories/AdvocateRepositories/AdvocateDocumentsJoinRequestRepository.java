package com.example.demo700.Repositories.AdvocateRepositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.AdvocateModels.AdvocateDocuments;
import com.example.demo700.Model.AdvocateModels.AdvocateDocumentsJoinRequest;

@Repository
public interface AdvocateDocumentsJoinRequestRepository extends MongoRepository<AdvocateDocumentsJoinRequest, String> {

	// ==================== BY ADVOCATE ID ====================
		@Query("{ 'advocateId': ?0 }")
		AdvocateDocumentsJoinRequest findByAdvocateId(String advocateId);
		
		List<AdvocateDocumentsJoinRequest> findByAdvocateIdIn(List<String> advocateId);

		// ==================== ATTACHMENTS (case-insensitive contains) ====================
		// NOTE: `attachments` is assumed to be a String field.
		// If it is a List<String> in your model, this query still works —
		// Mongo $regex on an array field matches if any element matches.
		@Query("{ 'attachments': { $regex: ?0, $options: 'i' } }")
		List<AdvocateDocumentsJoinRequest> findByAttachmentsContainingIgnoreCase(String attachments);
	
}
