package com.example.demo700.Repositories.UserRepositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.UserModels.Subscription;

@Repository
public interface SubscriptionRepository extends MongoRepository<Subscription, String> {

	public List<Subscription> findByCompanyId(String companyId);
	public List<Subscription> findBySubscriberNameContainingIgnoreCase(String subscriberName);
	public List<Subscription> findByCompanyIdAndNumberOfShareLte(String companyId, double numberOfShare);
	public List<Subscription> findByCompanyIdAndNumberOfShareGte(String companyId, double numberOfShare);
	public List<Subscription> findBySignatureId(String signatureId);

}
