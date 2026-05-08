package com.example.demo700.Services.AdvocateServices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.DTOFiles.AdvocateResponse;
import com.example.demo700.DTOFiles.PostReactionResponse;
import com.example.demo700.DTOFiles.PostResponse;
import com.example.demo700.ENums.AdvocateSpeciality;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.AdvocateModels.Advocate;
import com.example.demo700.Model.AdvocateModels.AdvocatePost;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.AdvocateRepositories.AdvocateRepositories;
import com.example.demo700.Repositories.AdvocateRepositories.PostRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;
import com.example.demo700.Services.UserServices.PostReactionService;

@Service
public class AdvocatePostServiceImpl implements AdvocatePostService {

	@Autowired
	private PostRepository advocatePostRepository;

	@Autowired
	private AdvocateRepositories advocateRepository;

	@Autowired
	private AdvocateService advocateService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostContentService postContentService;

	@Autowired
	private PostReactionService postReactionService;

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
	public List<PostResponse> findByAdvocateId(String advocateId) {

		if (advocateId == null) {

			throw new NullPointerException("False request...");

		}

		List<AdvocatePost> list = advocatePostRepository.findByAdvocateId(advocateId);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such advocate exist at here...");

		}

		return getPostResponseFromPostList(list);
	}

	@Override
	public List<PostResponse> findByPostType(AdvocateSpeciality postType) {

		if (postType == null) {

			throw new NullPointerException("False request...");

		}

		List<AdvocatePost> list = advocatePostRepository.findByPostType(postType);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such advocate exist at here...");

		}

		return getPostResponseFromPostList(list);
	}

	@Override
	public List<PostResponse> findByAdvocateIdAndPostType(String advocateId, AdvocateSpeciality postType) {

		if (advocateId == null || postType == null) {

			throw new NullPointerException("False request...");

		}

		List<AdvocatePost> list = advocatePostRepository.findByAdvocateIdAndPostType(advocateId, postType);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such advocate exist at here...");

		}

		return getPostResponseFromPostList(list);
	}

	@Override
	public List<PostResponse> findByPostContentContainingIgnoreCase(String keyword) {

		if (keyword == null) {

			throw new NullPointerException("False request...");

		}

		List<AdvocatePost> list = advocatePostRepository.findByPostContentContainingIgnoreCase(keyword);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such advocate exist at here...");

		}

		return getPostResponseFromPostList(list);
	}

	@Override
	public List<PostResponse> findLatestPosts() {

		List<AdvocatePost> list = advocatePostRepository.findLatestPosts();

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such advocate exist at here...");

		}

		return getPostResponseFromPostList(list);
	}

	@Override
	public List<PostResponse> findByAttachmentIdIsNotNull() {

		List<AdvocatePost> list = advocatePostRepository.findByAttachmentIdIsNotNull();

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such advocate exist at here...");

		}

		return getPostResponseFromPostList(list);
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

				if(advocatePost.getAttachmentId() != null) {
					
					postContentService.delete(advocatePost.getAttachmentId());
					
				}
				
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
	public List<PostResponse> seeAll() {

		List<AdvocatePost> list = advocatePostRepository.findAll();

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such advocate post find at here...");

		}

		return getPostResponseFromPostList(list);
	}

	@Override
	public PostResponse searchPost(String postId) {

		if (postId == null) {

			throw new NullPointerException("False request...");

		}

		try {

			AdvocatePost post = advocatePostRepository.findById(postId).get();

			if (post == null) {

				throw new Exception();

			}

			return getPostResponseFromPost(post);

		} catch (Exception e) {

			throw new NoSuchElementException("No Such advocate find at here...");

		}

	}

	private ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	private PostResponse getPostResponseFromPost(AdvocatePost post) {

		List<AdvocatePost> list = new ArrayList<>();

		list.add(post);

		return getPostResponseFromPostList(list).get(0);

	}

	private List<PostResponse> getPostResponseFromPostList(List<AdvocatePost> posts) {

		List<PostResponse> responses = new ArrayList<>();

		List<String> allPostId = posts.stream().map(AdvocatePost::getId).collect(Collectors.toList());

		List<PostReactionResponse> reactions = postReactionService.findByAdvocateIdIn(allPostId);

		List<AdvocateResponse> users = advocateService.seeAllAdvocate();

		CompletableFuture<Map<String, AdvocateResponse>> userFuture = CompletableFuture.supplyAsync(() -> {

			Map<String, AdvocateResponse> map = new HashMap<>();

			if (users == null || users.isEmpty()) {
				return map;
			}

			return users.stream().filter(Objects::nonNull).filter(user -> user.getId() != null).collect(Collectors
					.toMap(AdvocateResponse::getId, Function.identity(), (existing, replacement) -> existing));

		}, executor);

		CompletableFuture<Map<String, List<PostReactionResponse>>> reactionFuture = CompletableFuture
				.supplyAsync(() -> {

					Map<String, List<PostReactionResponse>> map = new HashMap<>();

					if (reactions == null || reactions.isEmpty()) {
						return map;
					}

					return reactions.stream().filter(Objects::nonNull)
							.filter(postReaction -> postReaction.getAdvocatePostId() != null)
							.collect(Collectors.groupingBy(PostReactionResponse::getAdvocatePostId));

				}, executor);

		CompletableFuture.allOf(userFuture, reactionFuture).join();

		Map<String, AdvocateResponse> userMap = userFuture.join();
		Map<String, List<PostReactionResponse>> postReactionMap = reactionFuture.join();

		for (AdvocatePost post : posts) {

			try {

				PostResponse response = new PostResponse();

				response.setId(post.getId());
				response.setAdvocateId(post.getAdvocateId());
				response.setPostType(post.getPostType());

				try {

					response.setPostContent(post.getPostContent());

				} catch (Exception e) {

				}

				try {

					response.setAttachmentId(post.getAttachmentId());

				} catch (Exception e) {

				}

				try {

					response.setReactions(postReactionMap.get(post.getId()));

				} catch (Exception e) {

				}

				try {

					response.setAdvocateName(userMap.get(post.getAdvocateId()).getName());

				} catch (Exception e) {

				}

				responses.add(response);

			} catch (Exception e) {

			}

		}

		return responses;

	}

}
