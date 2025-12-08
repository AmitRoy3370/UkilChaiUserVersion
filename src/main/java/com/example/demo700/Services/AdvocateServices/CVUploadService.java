package com.example.demo700.Services.AdvocateServices;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.Utils.FileHexConverter;

import io.jsonwebtoken.io.IOException;

@Service
public class CVUploadService {

	public String uploadCv(MultipartFile file) throws java.io.IOException {

		if (file == null || file.isEmpty()) {
			throw new NullPointerException("CV file cannot be empty");
		}

		// Validate extension
		String fileName = file.getOriginalFilename().toLowerCase();
		if (!(fileName.endsWith(".pdf") || fileName.endsWith(".doc") || fileName.endsWith(".docx"))) {
			throw new ArithmeticException("Only PDF, DOC, DOCX files are allowed");
		}

		try {
			String hex = FileHexConverter.fileToHex(file.getInputStream());

			return hex;

		} catch (IOException e) {
			throw new RuntimeException("Failed to read CV", e);
		}
	}

	public byte[] getCv(String hexKey) {

		return FileHexConverter.hexToFile(hexKey);
	}

	public String updateCv(MultipartFile file) throws java.io.IOException {
		return uploadCv(file);
	}

}
