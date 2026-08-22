package com.example.demo700.Repositories.UserRepositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.UserModels.Subscription;

@Repository
public interface SubscriptionRepository extends MongoRepository<Subscription, String> {

    @Query("{ 'companyId' : ?0 }")
    public List<Subscription> findByCompanyId(String companyId);

    @Query("{ 'companyId' : { $in: ?0 } }")
    public List<Subscription> findByCompanyIdIn(List<String> companiesId);

    @Query("{ 'subscriberName' : { $regex: ?0, $options: 'i' } }")
    public List<Subscription> findBySubscriberNameContainingIgnoreCase(String subscriberName);

    @Query("{ 'companyId' : ?0, 'numberOfShare' : { $lte: ?1 } }")
    public List<Subscription> findByCompanyIdAndNumberOfShareLte(String companyId, int numberOfShare);

    @Query("{ 'companyId' : ?0, 'numberOfShare' : { $gte: ?1 } }")
    public List<Subscription> findByCompanyIdAndNumberOfShareGte(String companyId, int numberOfShare);

    @Query("{ 'signatureId' : ?0 }")
    public List<Subscription> findBySignatureId(String signatureId);

}
