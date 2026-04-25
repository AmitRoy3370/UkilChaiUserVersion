package com.example.demo700.Services.QNAServices;
import java.time.Instant;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.DTOFiles.QuestionResponse;
import com.example.demo700.ENums.AdvocateSpeciality;
import com.example.demo700.Model.QNAModels.AskQuestion;

public interface QuestionService {

	public AskQuestion AskQuestion(AskQuestion askQuestion, String userId, MultipartFile file);
	public List<QuestionResponse> seeAll();
	List<QuestionResponse> findByUserId(String userId);
    List<QuestionResponse> findByMessageContainingIgnoreCase(String keyWord);

    // ✅ NEW FEATURE 1: Find posts AFTER a time
    List<QuestionResponse> findByPostTimeAfter(Instant time);

    // ✅ NEW FEATURE 2: Find posts BEFORE a time
    List<QuestionResponse> findByPostTimeBefore(Instant time);

    // ⭐ OPTIONAL (Recommended): Find posts BETWEEN two times
    List<QuestionResponse> findByPostTimeBetween(Instant start, Instant end);
    
    List<QuestionResponse> findByQuestionType(AdvocateSpeciality questionType);
    
    public QuestionResponse findByQuestionId(String questionId);
    
    public AskQuestion updateQuestion(AskQuestion askQuestion, String userId, String questionId, MultipartFile file);
    public boolean removeQuestion(String userId, String questionId);
	
}
