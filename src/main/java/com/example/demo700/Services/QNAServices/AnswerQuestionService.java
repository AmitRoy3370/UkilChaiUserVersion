package com.example.demo700.Services.QNAServices;

import java.time.Instant;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.DTOFiles.AnswerResponse;
import com.example.demo700.Model.QNAModels.AnswerQuestion;

public interface AnswerQuestionService {

	public AnswerQuestion answer(AnswerQuestion answerQuestion, String userId, MultipartFile file);

	public AnswerQuestion updateAnswer(AnswerQuestion answerQuestion, String userID, String answerId, MultipartFile file);

	public List<AnswerResponse> findByAdvocateId(String advocateId);

	public List<AnswerResponse> findByQuestionId(String questionId);

	public List<AnswerResponse> findByMessageContainingIgnoreCase(String keyword);

	public List<AnswerResponse> findByTimeAfter(Instant time);

	public List<AnswerResponse> findByTimeBefore(Instant time);

	public List<AnswerResponse> findByTimeBetween(Instant startTime, Instant endTime);
	
	public List<AnswerResponse> findByQuestionIdIn(List<String> questionIds);
	
	public List<AnswerResponse> findAll();
	
	public AnswerResponse findByAnswerId(String answerId);
	
	public boolean deleteAnswer(String answerId, String userId);

}
