package com.example.demo700.Repositories.VatRepositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.VatModels.VatRegistrationProcess;

@Repository
public interface VatRegistrationProcessRepository extends MongoRepository<VatRegistrationProcess, String> {

    @Query("{ 'vatId': ?0 }")
    public VatRegistrationProcess findByVatId(String vatId);
    
    public List<VatRegistrationProcess> findByVatIdIn(List<String> vatsId);

    @Query("{ 'userId': ?0 }")
    public List<VatRegistrationProcess> findByUserId(String userId);

    @Query("{ 'advocateId': ?0 }")
    public List<VatRegistrationProcess> findByAdvocateId(String advocateId);

    @Query("{ 'status': ?0 }")
    public List<VatRegistrationProcess> findByStatus(boolean status);

    @Query("{ 'steps': { $regex: ?0, $options: 'i' } }")
    public List<VatRegistrationProcess> findByStepsContainingIgnoreCase(String steps);

}