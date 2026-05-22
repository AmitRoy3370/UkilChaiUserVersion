package com.example.demo700.Services.AdminServices;

import java.util.List;

import com.example.demo700.DTOFiles.AdminDTO;
import com.example.demo700.Model.AdminModels.Admin;

public interface AdminService {

	public Admin addAdmin(Admin admin, String userId);

	public List<AdminDTO> seeAll();

	public AdminDTO findByUserId(String userId);

	public List<AdminDTO> findByAdvocateSpecialityContainingIgnoreCase(String advocateSpeciality);

	public Admin updateAdmin(Admin updatedAdmin, String userId, String adminId);

	public List<AdminDTO> seeAll(List<String> list);
	
	public boolean deleteAdmin(String adminId, String userId);

}
