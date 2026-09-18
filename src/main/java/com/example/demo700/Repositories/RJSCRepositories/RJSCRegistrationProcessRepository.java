package com.example.demo700.Repositories.RJSCRepositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.RJSCModels.RJSCRegistrationProcess;

@Repository
public interface RJSCRegistrationProcessRepository extends MongoRepository<RJSCRegistrationProcess, String> {

    @Query("{ 'userId': ?0 }")
    public List<RJSCRegistrationProcess> findByUserId(String userId);

    @Query("{ 'advocateId': ?0 }")
    public List<RJSCRegistrationProcess> findByAdvocateId(String advocateId);

    @Query("{ 'status': ?0 }")
    public List<RJSCRegistrationProcess> findByStatus(boolean status);

    @Query("{ 'rjscId': ?0 }")
    public RJSCRegistrationProcess findByRjscId(String rjscId);
    
    public List<RJSCRegistrationProcess> findByRjscIdIn(List<String> rjscIds);

    @Query("{ 'steps': { $regex: ?0, $options: 'i' } }")
    public List<RJSCRegistrationProcess> findByStepsContainingIgnoreCase(String steps);

}