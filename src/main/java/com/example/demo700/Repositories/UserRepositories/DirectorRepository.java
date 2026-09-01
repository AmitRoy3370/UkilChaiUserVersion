package com.example.demo700.Repositories.UserRepositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.UserModels.Director;

@Repository
public interface DirectorRepository extends MongoRepository<Director, String> {

    @Query("{ 'userId' : ?0 }")
    public List<Director> findByUserId(String userId);

    @Query("{ 'nid' : ?0 }")
    public List<Director> findByNid(String nid);

    @Query("{ 'position' : { $regex: ?0, $options: 'i' } }")
    public List<Director> findByPositionContainingIgnoreCase(String position);

    @Query("{ 'fullName' : { $regex: ?0, $options: 'i' } }")
    public List<Director> findByFullNameContainingIgnoreCase(String fullName);

    @Query("{ 'fatherName' : { $regex: ?0, $options: 'i' } }")
    public List<Director> findByFatherNameContainingIgnoreCase(String fatherName);

    @Query("{ 'motherName' : { $regex: ?0, $options: 'i' } }")
    public List<Director> findByMotherNameContainingIgnoreCase(String motherName);

    @Query("{ 'mobileNumber' : { $regex: ?0, $options: 'i' } }")
    public List<Director> findByMobileNumberContainingIgnoreCase(String mobileNumber);

    @Query("{ 'email' : { $regex: ?0, $options: 'i' } }")
    public List<Director> findByEmailContainingIgnoreCase(String email);

    @Query("{ 'nidNumber' : { $regex: ?0, $options: 'i' } }")
    public List<Director> findByNidNumberContainingIgnoreCase(String nidNumber);
}