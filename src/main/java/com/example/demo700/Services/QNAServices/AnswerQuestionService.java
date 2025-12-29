package com.example.demo700.Services.QNAServices;

import java.time.Instant;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.Model.QNAModels.AnswerQuestion;

public interface AnswerQuestionService {

	public AnswerQuestion answer(AnswerQuestion answerQuestion, String userId, MultipartFile file);

	public AnswerQuestion updateAnswer(AnswerQuestion answerQuestion, String userID, String answerId, MultipartFile file);

	public List<AnswerQuestion> findByAdvocateId(String advocateId);

	public List<AnswerQuestion> findByQuestionId(String questionId);

	public List<AnswerQuestion> findByMessageContainingIgnoreCase(String keyword);

	public List<AnswerQuestion> findByTimeAfter(Instant time);

	public List<AnswerQuestion> findByTimeBefore(Instant time);

	public List<AnswerQuestion> findByTimeBetween(Instant startTime, Instant endTime);
	
	public List<AnswerQuestion> findAll();
	
	public AnswerQuestion findByAnswerId(String answerId);
	
	public boolean deleteAnswer(String answerId, String userId);

}
