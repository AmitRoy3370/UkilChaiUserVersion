package com.example.demo700.Services.RJSCServices;

import java.util.List;

import com.example.demo700.Model.RJSCModels.RJSCRegistrationProcess;

public interface RJSCRegistrationProcessService {

	public RJSCRegistrationProcess addProcess(RJSCRegistrationProcess process, String userId);
	public RJSCRegistrationProcess updateProcess(RJSCRegistrationProcess process, String userId, String id);
	
	public RJSCRegistrationProcess findById(String id);
	public List<RJSCRegistrationProcess> findAll();
	public List<RJSCRegistrationProcess> findByUserId(String userId);
    public List<RJSCRegistrationProcess> findByAdvocateId(String advocateId);
    public List<RJSCRegistrationProcess> findByStatus(boolean status);
    public RJSCRegistrationProcess findByRjscId(String rjscId);
    public List<RJSCRegistrationProcess> findByRjscIdIn(List<String> rjscIds);
    public List<RJSCRegistrationProcess> findByStepsContainingIgnoreCase(String steps);
	
    public boolean delete(String id, String userId);
    
}
