package com.example.demo700.Services.CaseServices;

import java.time.Instant;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.Model.CaseModels.CaseJudgment;

public interface CaseJudgmentService {

	public CaseJudgment addCaseJudgment(CaseJudgment caseJudgment, String userId, MultipartFile file);

	public CaseJudgment updateCaseJudgment(CaseJudgment aseJudgment, String userId, String caseJudgmentId, MultipartFile file);

	public CaseJudgment findByCaseId(String caseId);

	public List<CaseJudgment> findByResultContainingIgnoreCase(String result);

	public List<CaseJudgment> findByDateAfter(Instant date);

	public List<CaseJudgment> findByDateBefore(Instant date);

	public List<CaseJudgment> findAll();

	public CaseJudgment findById(String id);

	public boolean removeCaseJudgment(String id, String userId);

}
