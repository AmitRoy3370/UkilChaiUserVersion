package com.example.demo700.Services.AdvocateServices;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.ENums.AdvocateSpeciality;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.AdvocateModels.Advocate;
import com.example.demo700.Model.AdvocateModels.AdvocatePost;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateRepositories;
import com.example.demo700.Repositories.AdvocateRepositories.PostRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;

@Service
public class AdvocatePostServiceImpl implements AdvocatePostService {

	@Autowired
	private PostRepository advocatePostRepository;

	@Autowired
	private AdvocateRepositories advocateRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostContentService postContentService;

	@Autowired
	private CenterAdminRepository centralAdminRepository;

	@Autowired
	private Cleaner cleaner;

	@Override
	public AdvocatePost uploadPost(AdvocatePost advocatePost, String userId, MultipartFile file) {

		if (advocatePost == null || userId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			Advocate advocate = advocateRepository.findByUserId(userId);

			if (advocate == null) {

				throw new Exception();

			}

			if (!advocate.getId().equals(advocatePost.getAdvocateId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here..");

		}

		try {

			if (file != null && !file.isEmpty()) {

				String attachmentId = postContentService.upload(file);

				advocatePost.setAttachmentId(attachmentId);

			}

		} catch (Exception e) {

			throw new ArithmeticException("Failed to upload this file...");

		}

		advocatePost = advocatePostRepository.save(advocatePost);

		if (advocatePost == null) {

			throw new ArithmeticException("post is not uploaded...");

		}

		return advocatePost;
	}

	@Override
	public List<AdvocatePost> findByAdvocateId(String advocateId) {

		if (advocateId == null) {

			throw new NullPointerException("False request...");

		}

		List<AdvocatePost> list = advocatePostRepository.findByAdvocateId(advocateId);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such advocate exist at here...");

		}

		return list;
	}

	@Override
	public List<AdvocatePost> findByPostType(AdvocateSpeciality postType) {

		if (postType == null) {

			throw new NullPointerException("False request...");

		}

		List<AdvocatePost> list = advocatePostRepository.findByPostType(postType);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such advocate exist at here...");

		}

		return list;
	}

	@Override
	public List<AdvocatePost> findByAdvocateIdAndPostType(String advocateId, AdvocateSpeciality postType) {

		if (advocateId == null || postType == null) {

			throw new NullPointerException("False request...");

		}

		List<AdvocatePost> list = advocatePostRepository.findByAdvocateIdAndPostType(advocateId, postType);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such advocate exist at here...");

		}

		return list;
	}

	@Override
	public List<AdvocatePost> findByPostContentContainingIgnoreCase(String keyword) {

		if (keyword == null) {

			throw new NullPointerException("False request...");

		}

		List<AdvocatePost> list = advocatePostRepository.findByPostContentContainingIgnoreCase(keyword);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such advocate exist at here...");

		}

		return list;
	}

	@Override
	public List<AdvocatePost> findLatestPosts() {

		List<AdvocatePost> list = advocatePostRepository.findLatestPosts();

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such advocate exist at here...");

		}

		return list;
	}

	@Override
	public List<AdvocatePost> findByAttachmentIdIsNotNull() {

		List<AdvocatePost> list = advocatePostRepository.findByAttachmentIdIsNotNull();

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such advocate exist at here...");

		}

		return list;
	}

	@Override
	public AdvocatePost updateAdvocatePost(String postId, String userId, AdvocatePost advocatePost,
			MultipartFile file) {

		if (advocatePost == null || userId == null || postId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			AdvocatePost post = advocatePostRepository.findById(postId).get();

			if (post == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such post available at here..");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			Advocate advocate = advocateRepository.findByUserId(userId);

			if (advocate == null) {

				throw new Exception();

			}

			if (!advocate.getId().equals(advocatePost.getAdvocateId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here..");

		}

		try {

			if (file != null && !file.isEmpty()) {

				String attachmentId = postContentService.upload(file);

				advocatePost.setAttachmentId(attachmentId);

			}

		} catch (Exception e) {

			throw new ArithmeticException("Failed to upload this file...");

		}

		advocatePost.setId(postId);

		advocatePost = advocatePostRepository.save(advocatePost);

		if (advocatePost == null) {

			throw new ArithmeticException("post is not uploaded...");

		}

		return advocatePost;
	}

	@Override
	public boolean deleteAdvocatePost(String postId, String userId) {

		if (postId == null || userId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			AdvocatePost post = advocatePostRepository.findById(postId).get();

			if (post == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such post available at here..");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			try {

				CenterAdmin centerAdmin = centralAdminRepository.findByUserId(user.getId());

				if (centerAdmin != null) {

					long count = advocatePostRepository.count();

					cleaner.removePost(postId);

					return count != advocatePostRepository.count();

				}

			} catch (Exception e) {

			}

			Advocate advocate = advocateRepository.findByUserId(user.getId());

			if (advocate == null) {

				throw new Exception();

			}

			AdvocatePost post = advocatePostRepository.findById(postId).get();

			if (post == null) {

				throw new Exception();

			}

			if (!post.getAdvocateId().equals(advocate.getId())) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new ArithmeticException("You can delete only yout post...");

		}

		long count = advocatePostRepository.count();

		cleaner.removePost(postId);

		return count != advocatePostRepository.count();
	}

	@Override
	public List<AdvocatePost> seeAll() {

		List<AdvocatePost> list = advocatePostRepository.findAll();

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such advocate post find at here...");

		}

		return list;
	}

	@Override
	public AdvocatePost searchPost(String postId) {
		
		if(postId == null) {
			
			throw new NullPointerException("False request...");
			
		}
		
		try {
			
			AdvocatePost post = advocatePostRepository.findById(postId).get();
			
			if(post == null) {
				
				throw new Exception();
				
			}
			
			return post;
			
		} catch(Exception e) {
			
			throw new NoSuchElementException("No Such advocate find at here...");
			
		}
		
	}

}
