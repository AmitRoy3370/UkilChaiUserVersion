package com.example.demo700.Services.CopyrightServices;

import java.util.List;

import com.example.demo700.Model.CopyrightModels.CopyrightRegistrationProcess;

public interface CopyrightRegistrationProcessService {

	public CopyrightRegistrationProcess addRegistrationProcess(CopyrightRegistrationProcess process, String userId);
	public CopyrightRegistrationProcess updateRegistrationProcess(CopyrightRegistrationProcess process, String userId, String id);
	
	public CopyrightRegistrationProcess findById(String id);
	public List<CopyrightRegistrationProcess> findAll();
	public CopyrightRegistrationProcess findByCopyrightId(String copyrightId);
    public List<CopyrightRegistrationProcess> findByUserId(String userId);
    public List<CopyrightRegistrationProcess> findByAdvocateId(String advocateId);
    public List<CopyrightRegistrationProcess> findByStpesContainingIgnoreCase(String stpes);
    public List<CopyrightRegistrationProcess> findByStatus(boolean status);
	
    public boolean deleteProcess(String id, String userId);
    
}
