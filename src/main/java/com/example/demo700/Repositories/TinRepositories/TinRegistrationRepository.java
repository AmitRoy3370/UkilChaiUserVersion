package com.example.demo700.Repositories.TinRepositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.TinModels.TinRegistrationProcess;

@Repository
public interface TinRegistrationRepository extends MongoRepository<TinRegistrationProcess, String> {

    @Query("{ 'centerAdminId': ?0 }")
    public List<TinRegistrationProcess> findByCenterAdminId(String centerAdminId);
    
    @Query("{ 'advocateId': ?0 }")
    public List<TinRegistrationProcess> findByAdvocateId(String advocateId);
    
    @Query("{ 'tinId': ?0 }")
    public TinRegistrationProcess findByTinId(String tinId);
    
    @Query("{ 'steps': { $regex: ?0, $options: 'i' } }")
    public List<TinRegistrationProcess> findByStepsContainingIgnoreCase(String steps);
    
}