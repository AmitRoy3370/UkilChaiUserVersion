package com.example.demo700.Services.UserServices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo700.CyclicCleaner.Cleaner;
import com.example.demo700.DTOFiles.PostReactionResponse;
import com.example.demo700.ENums.PostReactions;
import com.example.demo700.Model.AdminModels.CenterAdmin;
import com.example.demo700.Model.AdvocateModels.AdvocatePost;
import com.example.demo700.Model.UserModels.PostReaction;
import com.example.demo700.Model.UserModels.User;
import com.example.demo700.Repositories.AdminRepositories.CenterAdminRepository;
import com.example.demo700.Repositories.AdvocateRepositories.PostRepository;
import com.example.demo700.Repositories.UserRepositories.PostReactionRepository;
import com.example.demo700.Repositories.UserRepositories.UserRepository;

@Service
public class PostReactionServiceImpl implements PostReactionService {

	@Autowired
	private PostReactionRepository postReactionRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostRepository advocatePostRepository;

	@Autowired
	private CenterAdminRepository centerAdminRepository;

	@Autowired
	private Cleaner cleaner;

	@Override
	public PostReaction addPostReaction(PostReaction postReaction, String userId) {

		if (postReaction == null || userId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here...");

		}

		try {

			AdvocatePost post = advocatePostRepository.findById(postReaction.getAdvocatePostId()).get();

			if (post == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such post find at here...");

		}

		postReaction = postReactionRepository.save(postReaction);

		if (postReaction == null) {

			throw new ArithmeticException("Data is not added at here....");

		}

		return postReaction;
	}

	@Override
	public PostReaction updatePostReaction(PostReaction postReaction, String userId, String postReactionId) {

		if (postReaction == null || userId == null || postReactionId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			PostReaction reaction = postReactionRepository.findById(postReactionId).get();

			if (reaction == null) {

				throw new Exception();

			}

			if (!reaction.getUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such post reaction find at here...");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here...");

		}

		try {

			AdvocatePost post = advocatePostRepository.findById(postReaction.getAdvocatePostId()).get();

			if (post == null) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such post find at here...");

		}

		postReaction.setId(postReactionId);

		postReaction = postReactionRepository.save(postReaction);

		if (postReaction == null) {

			throw new ArithmeticException("Data is not added at here....");

		}

		return postReaction;
	}

	@Override
	public List<PostReactionResponse> findByUserId(String userId) {

		if (userId == null) {

			throw new NullPointerException("False request....");

		}

		List<PostReaction> list = postReactionRepository.findByUserId(userId);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such reaction find at here...");

		}

		return getPostReactionResponseFromPostReactionList(list);
	}

	@Override
	public List<PostReactionResponse> findByAdvocatePostId(String advocatePostId) {

		if (advocatePostId == null) {

			throw new NullPointerException("False request....");

		}

		List<PostReaction> list = postReactionRepository.findByAdvocatePostId(advocatePostId);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such reaction find at here...");

		}

		return getPostReactionResponseFromPostReactionList(list);
	}

	@Override
	public List<PostReactionResponse> findByAdvocatePostIdAndPostReaction(String advocatePostId,
			PostReactions postReaction) {

		if (advocatePostId == null || postReaction == null) {

			throw new NullPointerException("False request....");

		}

		List<PostReaction> list = postReactionRepository.findByAdvocatePostIdAndPostReaction(advocatePostId,
				postReaction);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such reaction find at here...");

		}

		return getPostReactionResponseFromPostReactionList(list);
	}

	@Override
	public List<PostReactionResponse> findByCommentContainingIgnoreCase(String comment) {

		if (comment == null) {

			throw new NullPointerException("False request....");

		}

		List<PostReaction> list = postReactionRepository.findByCommentContainingIgnoreCase(comment);

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such reaction find at here...");

		}

		return getPostReactionResponseFromPostReactionList(list);
	}

	@Override
	public List<PostReactionResponse> findAll() {

		List<PostReaction> list = postReactionRepository.findAll();

		if (list.isEmpty()) {

			throw new NoSuchElementException("No such reaction find at here...");

		}

		return getPostReactionResponseFromPostReactionList(list);
	}

	@Override
	public PostReactionResponse findById(String id) {

		try {

			PostReaction postReaction = postReactionRepository.findById(id).get();

			if (postReaction == null) {

				throw new Exception();

			}

			return getPostReactionResponseFromPostReaction(postReaction);

		} catch (Exception e) {

			throw new NoSuchElementException("No such reaction find at here...");

		}

	}

	@Override
	public List<PostReactionResponse> findByAdvocateIdIn(List<String> advocatePostIds) {
		
		try {
			
			List<PostReaction> list = postReactionRepository.findByAdocatePostIdIn(advocatePostIds);
			
			if(list.isEmpty()) {
				
				throw new Exception();
				
			}
			
			return getPostReactionResponseFromPostReactionList(list);
			
		} catch(Exception e) {
			
			return new ArrayList<>();
			
		}
		
	}
	
	@Override
	public boolean removePostReaction(String id, String userId) {

		if (id == null || userId == null) {

			throw new NullPointerException("False request....");

		}

		try {

			User user = userRepository.findById(userId).get();

			if (user == null) {

				throw new Exception();

			}

			CenterAdmin centerAdmin = centerAdminRepository.findByUserId(user.getId());

			if (centerAdmin != null) {

				long count = postReactionRepository.count();

				cleaner.removePostReaction(id);

				return count != postReactionRepository.count();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such user find at here...");

		}

		try {

			PostReaction reaction = postReactionRepository.findById(id).get();

			if (reaction == null) {

				throw new Exception();

			}

			if (!reaction.getUserId().equals(userId)) {

				throw new Exception();

			}

		} catch (Exception e) {

			throw new NoSuchElementException("No such post reaction find at here...");

		}

		long count = postReactionRepository.count();

		cleaner.removePostReaction(id);

		return count != postReactionRepository.count();
	}

	ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	private PostReactionResponse getPostReactionResponseFromPostReaction(PostReaction reaction) {

		List<PostReaction> list = new ArrayList<>();

		list.add(reaction);

		return getPostReactionResponseFromPostReactionList(list).get(0);

	}

	private List<PostReactionResponse> getPostReactionResponseFromPostReactionList(List<PostReaction> reactions) {

		List<PostReactionResponse> responses = new ArrayList<>();

		List<User> users = userRepository.findAll();

		CompletableFuture<Map<String, User>> userFuture = CompletableFuture.supplyAsync(() -> users.isEmpty()
				? new HashMap<>()
				: users.stream().filter(Objects::nonNull).filter(user -> user.getName() != null).collect(
						Collectors.toMap(User::getId, Function.identity(), (existing, replacement) -> existing)),
				executor);

		CompletableFuture.allOf(userFuture).join();

		Map<String, User> userMap = userFuture.join();

		for (PostReaction reaction : reactions) {

			try {

				PostReactionResponse response = new PostReactionResponse();

				response.setId(reaction.getId());

				try {

					response.setUserId(reaction.getUserId());
					response.setUserName(userMap.get(reaction.getUserId()).getName());

				} catch (Exception e) {

				}

				try {

					response.setAdvocatePostId(reaction.getAdvocatePostId());

				} catch (Exception e) {

				}

				try {

					response.setComment(reaction.getComment());

				} catch (Exception e) {

				}

				try {

					response.setPostReaction(reaction.getPostReaction());

				} catch (Exception e) {

				}

				responses.add(response);

			} catch (Exception e) {

			}

		}

		return responses;

	}

}
