package com.example.demo700.Repositories.QNARepositories;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo700.ENums.AdvocateSpeciality;
import com.example.demo700.Model.QNAModels.AskQuestion;

@Repository
public interface QuestionRepository extends MongoRepository<AskQuestion, String> {

	// Existing
	List<AskQuestion> findByUserId(String userId);

	Page<AskQuestion> findByUserId(String userId, Pageable pageable);

	List<AskQuestion> findByMessageContainingIgnoreCase(String keyWord);

	Page<AskQuestion> findByMessageContainingIgnoreCase(String keyWord, Pageable pageable);

	// ✅ NEW FEATURE 1: Find posts AFTER a time
	List<AskQuestion> findByPostTimeAfter(Instant time);

	Page<AskQuestion> findByPostTimeAfter(Instant time, Pageable pageable);

	// ✅ NEW FEATURE 2: Find posts BEFORE a time
	List<AskQuestion> findByPostTimeBefore(Instant time);

	Page<AskQuestion> findByPostTimeBefore(Instant time, Pageable pageable);

	// ⭐ OPTIONAL (Recommended): Find posts BETWEEN two times
	List<AskQuestion> findByPostTimeBetween(Instant start, Instant end);

	Page<AskQuestion> findByPostTimeBetween(Instant start, Instant end, Pageable pageable);

	List<AskQuestion> findByQuestionType(AdvocateSpeciality questionType);

	Page<AskQuestion> findByQuestionType(AdvocateSpeciality questionType, Pageable pageable);

}
