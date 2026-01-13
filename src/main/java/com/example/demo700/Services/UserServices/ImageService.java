package com.example.demo700.Services.UserServices;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class ImageService {

	@Autowired
	private GridFsTemplate gridFsTemplate;

	// UPLOAD
	public String upload(MultipartFile file) throws IOException {

		DBObject meta = new BasicDBObject();
		meta.put("type", file.getContentType());
		meta.put("size", file.getSize());

		ObjectId id = gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType(),
				meta);

		return id.toHexString();
	}

	// GET FILE
	public GridFSFile getFile(String id) {
		return gridFsTemplate.findOne(new org.springframework.data.mongodb.core.query.Query(
				org.springframework.data.mongodb.core.query.Criteria.where("_id").is(new ObjectId(id))));
	}

	// GET STREAM
	public InputStream getStream(GridFSFile file) throws IllegalStateException, IOException {
		return gridFsTemplate.getResource(file).getInputStream();
	}

	// DELETE
	public void delete(String id) {
		gridFsTemplate.delete(new org.springframework.data.mongodb.core.query.Query(
				org.springframework.data.mongodb.core.query.Criteria.where("_id").is(new ObjectId(id))));
	}

	// UPDATE = delete + new upload
	public String update(String oldId, MultipartFile newFile) throws IOException {

		try {

			delete(oldId);

		} catch (Exception e) {

		}

		return upload(newFile);
	}
}