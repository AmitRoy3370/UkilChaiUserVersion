package com.example.demo700.Repositories.QNARepositories;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.QNAModels.AnswerQuestion;

@Repository
public interface AnswerRepository extends MongoRepository<AnswerQuestion, String> {

	List<AnswerQuestion> findByAdvocateId(String advocateId);
	
	List<AnswerQuestion> findByQuestionId(String questionId);
	
	List<AnswerQuestion> findByMessageContainingIgnoreCase(String keyword);
	
	List<AnswerQuestion> findByTimeAfter(Instant time);
	List<AnswerQuestion> findByTimeBefore(Instant time);
	List<AnswerQuestion> findByTimeBetween(Instant startTime, Instant endTime);
	
}
