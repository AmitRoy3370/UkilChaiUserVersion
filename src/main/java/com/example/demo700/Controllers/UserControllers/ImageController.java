package com.example.demo700.Controllers.UserControllers;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo700.Services.UserServices.ImageService;
import com.mongodb.client.gridfs.model.GridFSFile;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/api/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    private final Path rootPath = Paths.get("Attachments");

    /**
     * অ্যাপ্লিকেশন স্টার্ট হওয়ার সময় ফোল্ডার না থাকলে স্বয়ংক্রিয়ভাবে তৈরি করবে
     */
    @PostConstruct
    public void init() {
        try {
            if (!Files.exists(rootPath)) {
                Files.createDirectories(rootPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Download image by id (Attachment)
     */
    @GetMapping("/download/{id}")
    public void downloadImage(@PathVariable String id, HttpServletResponse response) throws IOException {
        serveImage(id, response, "attachment");
    }

    /**
     * View/Preview image inline by id
     */
    @GetMapping("/view/{id}")
    public void viewImage(@PathVariable String id, HttpServletResponse response) throws IOException {
        serveImage(id, response, "inline");
    }

    /**
     * Common method to handle Disk Cache & MongoDB Fetching
     */
    private void serveImage(String id, HttpServletResponse response, String dispositionType) throws IOException {
        Path filePath = rootPath.resolve(id);
        File localFile = filePath.toFile();

        // 1. HTTP Cache Control (১৮০ দিনের জন্য ব্রাউজার/ডিভাইসে ক্যাশিং অন)
        response.setHeader("Cache-Control", "public, max-age=15552000");

        // =========================================================
        // CASE 1: Local Disk Cache - যদি লোকাল ডিস্কে ক্যাশ ফাইল থাকে
        // =========================================================
        if (localFile.exists()) {
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            response.setContentType(contentType);
            response.setHeader("Content-Disposition", dispositionType + "; filename=\"" + localFile.getName() + "\"");

            // সরাসরি ফাইল থেকে স্ট্রিম করে রেসপন্স আউটপুটে দিয়ে দেওয়া (Fastest!)
            try (InputStream is = new FileInputStream(localFile)) {
                IOUtils.copy(is, response.getOutputStream());
                response.flushBuffer();
            }
            return;
        }

        // =========================================================
        // CASE 2: Cache Miss - যদি লোকাল ডিস্কে না থাকে, DB থেকে আনা
        // =========================================================
        GridFSFile file = imageService.getFile(id);

        if (file == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found with id: " + id);
            return;
        }

        String mimeType = file.getMetadata() != null && file.getMetadata().get("type") != null
                ? file.getMetadata().getString("type")
                : "application/octet-stream";

        response.setContentType(mimeType);
        response.setHeader("Content-Disposition", dispositionType + "; filename=\"" + file.getFilename() + "\"");

        // MongoDB থেকে ফাইল রিড করা এবং একসাথে লোকাল ডিস্কে সেভ (Caching) ও রেসপন্সে পাঠানো
        try (InputStream stream = imageService.getStream(file)) {
            
            // Step 2.1: Local Disk-এ ফাইল রাইট/ক্যাশ করা
            Files.copy(stream, filePath, StandardCopyOption.REPLACE_EXISTING);

            // Step 2.2: নতুন ক্যাশ হওয়া ফাইল থেকে আউটপুট স্ট্রিম রিড করে পাঠিয়ে দেওয়া
            try (InputStream localStream = new FileInputStream(localFile)) {
                IOUtils.copy(localStream, response.getOutputStream());
                response.flushBuffer();
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error loading image stream");
        }
    }
}