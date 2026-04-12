package com.example.demo700.Services.AdvocateServices;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.Services.UserServices.ImageService;
import com.example.demo700.Utils.FileHexConverter;
import com.mongodb.client.gridfs.model.GridFSFile;

import io.jsonwebtoken.io.IOException;

@Service
public class CVUploadService {

	@Autowired
	private ImageService cvUploaderService;

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
			String hex = cvUploaderService.upload(file);

			return hex;

		} catch (IOException e) {
			throw new RuntimeException("Failed to read CV", e);
		}
	}

	public GridFSFile getCv(String hexKey) {

		if (cvUploaderService.attachmentExists(hexKey)) {

			return cvUploaderService.getFile(hexKey);

		} else {

			throw new NoSuchElementException("No such cv exist at here...");

		}
	}

	public String updateCv(MultipartFile file, String hexKey) throws java.io.IOException {

		if (file == null || file.isEmpty()) {
			throw new NullPointerException("CV file cannot be empty");
		}

		// Validate extension
		String fileName = file.getOriginalFilename().toLowerCase();
		if (!(fileName.endsWith(".pdf") || fileName.endsWith(".doc") || fileName.endsWith(".docx"))) {
			throw new ArithmeticException("Only PDF, DOC, DOCX files are allowed");
		}

		if (!cvUploaderService.attachmentExists(hexKey)) {

			throw new NoSuchElementException("No such cv exist at here...");

		}

		cvUploaderService.delete(hexKey);

		String hex = cvUploaderService.upload(file);

		return hex;
	}

	public boolean deleteCV(String hex) {

		if (!cvUploaderService.attachmentExists(hex)) {

			throw new NoSuchElementException("No such cv exist at here...");

		}

		cvUploaderService.delete(hex);

		return cvUploaderService.attachmentExists(hex);

	}

}
