package com.example.demo700.Controllers.UserControllers;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.web.bind.annotation.*;
import com.example.demo700.Services.UserServices.ImageService;
import com.mongodb.client.gridfs.model.GridFSFile;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    /**
     * Download / view image by id
     */
    @GetMapping("/download/{id}")
    public void downloadImage(@PathVariable String id, HttpServletResponse response) throws IOException {
        GridFSFile file = imageService.getFile(id);

        if (file == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found with id: " + id);
            return;
        }

        response.setContentType(file.getMetadata().getString("type"));
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getFilename() + "\"");

        try (InputStream stream = imageService.getStream(file)) {
            IOUtils.copy(stream, response.getOutputStream());
            response.flushBuffer();
        }
    }

    /**
     * Optional: Preview inline image instead of download
     */
    @GetMapping("/view/{id}")
    public void viewImage(@PathVariable String id, HttpServletResponse response) throws IOException {
        GridFSFile file = imageService.getFile(id);

        if (file == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found with id: " + id);
            return;
        }

        response.setContentType(file.getMetadata().getString("type"));
        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getFilename() + "\"");

        try (InputStream stream = imageService.getStream(file)) {
            IOUtils.copy(stream, response.getOutputStream());
            response.flushBuffer();
        }
    }
}
