package com.example.demo700.Services.AdminServices;

import java.util.List;

import com.example.demo700.Model.AdminModels.Admin;

public interface AdminService {

	public Admin addAdmin(Admin admin, String userId);

	public List<Admin> seeAll();

	public Admin findByUserId(String userId);

	public List<Admin> findByAdvocateSpecialityContainingIgnoreCase(String advocateSpeciality);

	public Admin updateAdmin(Admin updatedAdmin, String userId, String adminId);

	public boolean deleteAdmin(String adminId, String userId);

}
