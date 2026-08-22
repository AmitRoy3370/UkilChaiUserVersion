package com.example.demo700.Services.UserServices;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.Model.UserModels.Subscription;

public interface SubscriptionService {

	public Subscription addSubscription(Subscription subscription, String userId, MultipartFile signature);

	public Subscription updateSubscription(Subscription subscription, String userId, String id, MultipartFile signature);

	public Subscription findById(String id);

	public List<Subscription> findAll();

	public List<Subscription> findByCompanyId(String companyId);

	public List<Subscription> findBySubscriberNameContainingIgnoreCase(String subscriberName);

	public List<Subscription> findByCompanyIdAndNumberOfShareLte(String companyId, int numberOfShare);

	public List<Subscription> findByCompanyIdAndNumberOfShareGte(String companyId, int numberOfShare);

	public List<Subscription> findBySignatureId(String signatureId);

	public boolean removeSubscription(String id, String userId);

}
