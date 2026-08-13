package com.example.demo700.Services.UserServices;

import java.util.List;

import com.example.demo700.Model.UserModels.RegistrationProcess;

public interface RegistrationProcessService {

	public RegistrationProcess addRegistrationProcess(RegistrationProcess process, String userId);
	public RegistrationProcess updateRegistrationprocess(RegistrationProcess process, String userId, String id);
	public RegistrationProcess addSteps(String id, String step, String userId);
	
	public RegistrationProcess findById(String id);
	public List<RegistrationProcess> findAll();
	public List<RegistrationProcess> findByCompanyId(String companyId);
	public List<RegistrationProcess> findByAdvocateId(String advocateId);
	public List<RegistrationProcess> findByUserId(String userId);
	public List<RegistrationProcess> findByStatus(boolean status);
	public List<RegistrationProcess> findByShareValuePerShareLte(double shareValuePerShare);
	public List<RegistrationProcess> findByShareValuePerShareGte(double shareValuePerShare);
	
	public boolean deleteRegistrationProcess(String id, String userId);
}
