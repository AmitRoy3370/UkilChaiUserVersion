package com.example.demo700.Controllers.UserControllers;

import org.springframework.http.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.DTOFiles.JwtResponse;
import com.example.demo700.DTOFiles.LoginRequest;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Services.UserServices.UserService;

@RestController
@RequestMapping("/api/auth")
public class UserAuthenticationController {

	@Autowired
	private UserService userService;

	@PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> addUser(@RequestPart("name") String name, @RequestPart("password") String password, @RequestPart("profileImageId") String profileImageId,
			@RequestPart(value = "file", required = false) MultipartFile file) {

		try {

			User user = new User(name, password, profileImageId);

			JwtResponse jwtResponse = userService.addUser(user, file);

			if (jwtResponse == null) {
				return ResponseEntity.status(500).body("User could not be added.");
			}

			return ResponseEntity.status(201).body(jwtResponse);

		} catch (Exception e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> logIn(@RequestBody LoginRequest loginRequest) {

		try {

			JwtResponse jwtResponse = userService.LogIn(loginRequest);

			if (jwtResponse == null) {

				return ResponseEntity.status(500).body("failed to log in...");

			}

			return ResponseEntity.status(200).body(jwtResponse);

		} catch (Exception e) {

			return ResponseEntity.status(400).body(e.getMessage());

		}

	}

}
