package com.example.demo700.Repositories.UserRepositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.UserModels.ClientFeedback;

@Repository
public interface ClientFeedbackRepository extends MongoRepository<ClientFeedback, String> {

	public List<ClientFeedback> findByCaseId(String caseId);
	public List<ClientFeedback> findByUserId(String userId);
	
	public List<ClientFeedback> findByFeedbackContainingIgnoreCase(String feedback);
	
}
