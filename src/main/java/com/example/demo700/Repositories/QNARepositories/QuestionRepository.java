package com.example.demo700.Repositories.QNARepositories;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo700.ENums.AdvocateSpeciality;
import com.example.demo700.Model.QNAModels.AskQuestion;

@Repository
public interface QuestionRepository extends MongoRepository<AskQuestion, String> {

    // Existing
    List<AskQuestion> findByUserId(String userId);
    List<AskQuestion> findByMessageContainingIgnoreCase(String keyWord);

    // ✅ NEW FEATURE 1: Find posts AFTER a time
    List<AskQuestion> findByPostTimeAfter(Instant time);

    // ✅ NEW FEATURE 2: Find posts BEFORE a time
    List<AskQuestion> findByPostTimeBefore(Instant time);

    // ⭐ OPTIONAL (Recommended): Find posts BETWEEN two times
    List<AskQuestion> findByPostTimeBetween(Instant start, Instant end);
    
    List<AskQuestion> findByQuestionType(AdvocateSpeciality questionType);
    
}
