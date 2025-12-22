package com.example.demo700.Services.UserServices;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.DTOFiles.JwtResponse;
import com.example.demo700.DTOFiles.LoginRequest;
import com.example.demo700.Model.UserModels.User;

public interface UserService {
	
	public JwtResponse addUser(User user, MultipartFile file);
	public List<User> seeAllUsers();
	public User updateUser(User user, String userId, MultipartFile file);
	public boolean deleteUser(String userId, String tryingToDelete);
	public User findByName(String name);
	public List<User> findByProfileImageId(String profileImageId);
	public JwtResponse LogIn(LoginRequest loginRequest);
	public User searchUser(String userId);

}
