package com.example.demo700.Services.QNAServices;
import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.DTOFiles.QuestionListResponseDTO;
import com.example.demo700.DTOFiles.QuestionResponse;
import com.example.demo700.ENums.AdvocateSpeciality;
import com.example.demo700.Model.QNAModels.AskQuestion;

public interface QuestionService {

	public AskQuestion AskQuestion(AskQuestion askQuestion, String userId, MultipartFile file);
	
	public List<QuestionResponse> seeAll();
	
	public QuestionListResponseDTO seeAll(Pageable pageable);
	
	List<QuestionResponse> findByUserId(String userId);
	
	QuestionListResponseDTO findByUserId(String userId, Pageable pageable);
	
    List<QuestionResponse> findByMessageContainingIgnoreCase(String keyWord);

    QuestionListResponseDTO findByMessageContainingIgnoreCase(String keyWord, Pageable pageable);
    
    // ✅ NEW FEATURE 1: Find posts AFTER a time
    List<QuestionResponse> findByPostTimeAfter(Instant time);
    
    QuestionListResponseDTO findByPostTimeAfter(Instant time, Pageable pageable);

    // ✅ NEW FEATURE 2: Find posts BEFORE a time
    List<QuestionResponse> findByPostTimeBefore(Instant time);
    
    QuestionListResponseDTO findByPostTimeBefore(Instant time, Pageable pageable);

    // ⭐ OPTIONAL (Recommended): Find posts BETWEEN two times
    List<QuestionResponse> findByPostTimeBetween(Instant start, Instant end);
    
    QuestionListResponseDTO findByPostTimeBetween(Instant start, Instant end, Pageable pageable);
    
    List<QuestionResponse> findByQuestionType(AdvocateSpeciality questionType);
    
    QuestionListResponseDTO findByQuestionType(AdvocateSpeciality questionType, Pageable pageable);
    
    public QuestionResponse findByQuestionId(String questionId);
    
    public AskQuestion updateQuestion(AskQuestion askQuestion, String userId, String questionId, MultipartFile file);
    public boolean removeQuestion(String userId, String questionId);
	
}
