package com.example.demo700.Services.UserServices;

import java.util.List;

import com.example.demo700.DTOFiles.RegistrationProcessResponse;
import com.example.demo700.Model.UserModels.RegistrationProcess;

public interface RegistrationProcessService {

	public RegistrationProcess addRegistrationProcess(RegistrationProcess process, String userId);
	public RegistrationProcess updateRegistrationprocess(RegistrationProcess process, String userId, String id);
	public RegistrationProcess addSteps(String id, String step, String userId);
	
	public RegistrationProcessResponse findById(String id);
	public List<RegistrationProcessResponse> findAll();
	public List<RegistrationProcessResponse> findByCompanyId(String companyId);
	public List<RegistrationProcessResponse> findByAdvocateId(String advocateId);
	public List<RegistrationProcessResponse> findByUserId(String userId);
	public List<RegistrationProcessResponse> findByStatus(boolean status);
	public List<RegistrationProcessResponse> findByShareValuePerShareLte(double shareValuePerShare);
	public List<RegistrationProcessResponse> findByShareValuePerShareGte(double shareValuePerShare);
	
	public boolean deleteRegistrationProcess(String id, String userId);
}
