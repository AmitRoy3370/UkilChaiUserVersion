package com.example.demo700.Services.CaseServices;

import java.time.Instant;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.DTOFiles.CaseJudgementResponse;
import com.example.demo700.Model.CaseModels.CaseJudgment;

public interface CaseJudgmentService {

	public CaseJudgment addCaseJudgment(CaseJudgment caseJudgment, String userId, MultipartFile file);

	public CaseJudgment updateCaseJudgment(CaseJudgment aseJudgment, String userId, String caseJudgmentId, MultipartFile file);

	public CaseJudgementResponse findByCaseId(String caseId);

	public List<CaseJudgementResponse> findByResultContainingIgnoreCase(String result);

	public List<CaseJudgementResponse> findByDateAfter(Instant date);

	public List<CaseJudgementResponse> findByDateBefore(Instant date);

	public List<CaseJudgementResponse> findAll();

	public CaseJudgementResponse findById(String id);

	public boolean removeCaseJudgment(String id, String userId);

}
