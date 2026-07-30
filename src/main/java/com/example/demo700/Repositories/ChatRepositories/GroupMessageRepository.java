package com.example.demo700.Repositories.ChatRepositories;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.ChatModels.GroupMessage;

@Repository
public interface GroupMessageRepository extends MongoRepository<GroupMessage, String> {

	// একটি গ্রুপের সব মেসেজ সময় অনুযায়ী সাজানো
	List<GroupMessage> findByGroupIdOrderByTimestampAsc(String groupId);

	// পেজিনেশন সহ মেসেজ আনা (বড় গ্রুপের জন্য)
	@Query("{ 'groupId' : ?0 }")
	List<GroupMessage> findMessagesByGroupIdWithPagination(String groupId, Pageable pageable);

	@Query("{ 'groupId' : ?0 }")
	List<GroupMessage> findMessagesByGroupId(String groupId);

	// একটি গ্রুপের আনরিড মেসেজ কাউন্ট (যে ইউজার পড়েনি)
	@Query("{ 'groupId' : ?0, 'readBy' : { $ne : ?1 } }")
	List<GroupMessage> findUnreadMessagesByGroupId(String groupId, String userId);

	// নির্দিষ্ট সেন্ডারের মেসেজ খোঁজা
	List<GroupMessage> findByGroupIdAndSenderId(String groupId, String senderId);
}