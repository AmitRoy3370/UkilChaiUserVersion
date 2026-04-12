package com.example.demo700.Services.AdvocateServices;

import java.io.InputStream;
import java.util.NoSuchElementException;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.Services.UserServices.ImageService;
import com.example.demo700.Utils.FileHexConverter;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;

import io.jsonwebtoken.io.IOException;

@Service
public class CVUploadService {

	@Autowired
	private GridFsTemplate gridFsTemplate;

	// UPLOAD
	public String upload(MultipartFile file) throws IOException, java.io.IOException {
		// null চেক
		if (file == null || file.isEmpty()) {
			throw new IllegalArgumentException("File cannot be null or empty");
		}

		// ১ম পদ্ধতি: Original filename থেকে এক্সটেনশন বের করা
		String originalFilename = file.getOriginalFilename();
		String contentType = file.getContentType();

		// এক্সটেনশন বের করার ফাংশন
		String extension = getFileExtension(originalFilename);

		// ২য় পদ্ধতি: Content-Type চেক করা (ব্যাকআপ)
		boolean isValidExtension = false;

		// এক্সটেনশন চেক
		if (extension != null && !extension.isEmpty()) {
			extension = extension.toLowerCase();
			if (extension.equals(".pdf") || extension.equals(".doc") || extension.equals(".docx")) {
				isValidExtension = true;
			}
		}

		// এক্সটেনশন না পেলে content-type চেক করুন
		if (!isValidExtension && contentType != null) {
			if (contentType.equals("application/pdf") || contentType.equals("application/msword")
					|| contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
				isValidExtension = true;

				// Content-type অনুযায়ী এক্সটেনশন সেট করুন
				if (contentType.equals("application/pdf")) {
					extension = ".pdf";
				} else if (contentType.equals("application/msword")) {
					extension = ".doc";
				} else {
					extension = ".docx";
				}
			}
		}

		// এখনও ভ্যালিড না হলে error দিন
		if (!isValidExtension) {
			throw new IllegalArgumentException("Invalid file type. Only PDF, DOC, DOCX files are allowed. "
					+ "Filename: " + originalFilename + ", Content-Type: " + contentType);
		}

		// ফাইলের নাম ঠিক করা (যদি original filename না থাকে)
		String finalFilename = originalFilename;
		if (finalFilename == null || finalFilename.isEmpty()) {
			finalFilename = "document" + extension;
		} else if (!finalFilename.toLowerCase().endsWith(extension)) {
			// filename এ এক্সটেনশন না থাকলে যোগ করে দিন
			finalFilename = finalFilename + extension;
		}

		// মেটাডাটা তৈরি
		DBObject meta = new BasicDBObject();
		meta.put("type", file.getContentType());
		meta.put("size", file.getSize());
		meta.put("extension", extension);
		meta.put("originalFilename", originalFilename);

		// স্টোর করুন
		ObjectId id = gridFsTemplate.store(file.getInputStream(), finalFilename, file.getContentType(), meta);

		return id.toHexString();
	}

	// হেল্পার মেথড: ফাইল থেকে এক্সটেনশন বের করা
	private String getFileExtension(String filename) {
		if (filename == null || filename.isEmpty()) {
			return null;
		}

		int lastDotIndex = filename.lastIndexOf(".");
		if (lastDotIndex == -1 || lastDotIndex == filename.length() - 1) {
			return null; // কোন এক্সটেনশন নেই
		}

		return filename.substring(lastDotIndex); // .pdf, .doc etc.
	}

	private ObjectId parseObjectId(String id) {
		if (!ObjectId.isValid(id)) {
			throw new IllegalArgumentException("Invalid attachment id");
		}
		return new ObjectId(id);
	}

	// GET FILE
	public GridFSFile getFile(String id) {
		return gridFsTemplate.findOne(new Query(Criteria.where("_id").is(parseObjectId(id))));
	}

	// GET STREAM
	public InputStream getStream(GridFSFile file) throws IllegalStateException, IOException, java.io.IOException {
		return gridFsTemplate.getResource(file).getInputStream();
	}

	// DELETE
	public void delete(String id) {
		gridFsTemplate.delete(new org.springframework.data.mongodb.core.query.Query(
				org.springframework.data.mongodb.core.query.Criteria.where("_id").is(parseObjectId(id))));
	}

	public boolean attachmentExists(String id) {
		try {
			return gridFsTemplate.findOne(new Query(Criteria.where("_id").is(parseObjectId(id)))) != null;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	// UPDATE = delete + new upload
	public String update(String oldId, MultipartFile newFile) throws IOException, java.io.IOException {

		try {

			delete(oldId);

		} catch (Exception e) {

		}

		return upload(newFile);
	}

}
