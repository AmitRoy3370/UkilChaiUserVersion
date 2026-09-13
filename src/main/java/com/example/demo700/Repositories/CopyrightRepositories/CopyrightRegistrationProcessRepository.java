package com.example.demo700.Repositories.CopyrightRepositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.CopyrightModels.CopyrightRegistrationProcess;

@Repository
public interface CopyrightRegistrationProcessRepository extends MongoRepository<CopyrightRegistrationProcess, String> {

    @Query("{ 'copyrightId': ?0 }")
    public CopyrightRegistrationProcess findByCopyrightId(String copyrightId);

    public List<CopyrightRegistrationProcess> findByCopyrightIdIn(List<String> copyrightId);

    @Query("{ 'userId': ?0 }")
    public List<CopyrightRegistrationProcess> findByUserId(String userId);

    @Query("{ 'advocateId': ?0 }")
    public List<CopyrightRegistrationProcess> findByAdvocateId(String advocateId);

    @Query("{ 'stpes': { $regex: ?0, $options: 'i' } }")
    public List<CopyrightRegistrationProcess> findByStpesContainingIgnoreCase(String stpes);

    @Query("{ 'status': ?0 }")
    public List<CopyrightRegistrationProcess> findByStatus(boolean status);

}
