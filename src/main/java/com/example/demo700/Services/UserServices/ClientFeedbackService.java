package com.example.demo700.Services.UserServices;

import java.util.List;

import com.example.demo700.Model.UserModels.ClientFeedback;

public interface ClientFeedbackService {
	
	public ClientFeedback addClientFeedback(ClientFeedback clientFeedback, String userId);
	public ClientFeedback updateCientFeedback(ClientFeedback clientFeedback, String userId, String clientFeedbackId);
	
	public List<ClientFeedback> findByCaseId(String caseId);
	public List<ClientFeedback> findByUserId(String userId);
	
	public List<ClientFeedback> findByFeedbackContainingIgnoreCase(String feedback);
	
	public List<ClientFeedback> findAll();
	
	public ClientFeedback findById(String id);
	
	public boolean removeClientFeedback(String id, String userId);

}
