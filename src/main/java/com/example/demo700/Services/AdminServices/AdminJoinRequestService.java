package com.example.demo700.Services.AdminServices;

import java.util.List;

import com.example.demo700.Model.AdminModels.Admin;
import com.example.demo700.Model.AdminModels.AdminJoinRequest;

public interface AdminJoinRequestService {

	public AdminJoinRequest addAdmin(AdminJoinRequest admin, String userId);

	public List<AdminJoinRequest> seeAll();

	public AdminJoinRequest findByUserId(String userId);

	public List<AdminJoinRequest> findByAdvocateSpecialityContainingIgnoreCase(String advocateSpeciality);

	public AdminJoinRequest updateAdmin(AdminJoinRequest updatedAdmin, String userId, String adminId);

	public boolean deleteAdmin(String adminId, String userId);
	
	public Admin handleAdminJoinRequest(String userId, String adminJoinRequestId, String response);

}
