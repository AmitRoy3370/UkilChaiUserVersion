package com.example.demo700.Repositories.ChatRepositories;

import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.ChatModels.ChatMessage;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
	
	List<ChatMessage> findBySenderAndReceiver(String sender, String receiver);
    List<ChatMessage> findByReceiverAndSender(String receiver, String sender);
    List<ChatMessage> findByReceiverOrSender(String receiver, String sender);

    List<ChatMessage> findBySender(String sender);
    List<ChatMessage> findByReceiver(String receiver);
    
 // Get only the latest message per conversation using aggregation
    @Aggregation(pipeline = {
        "{ $match: { $or: [ { sender: ?0, receiver: ?1 }, { sender: ?1, receiver: ?0 } ] } }",
        "{ $sort: { timeStamp: -1 } }",
        "{ $limit: 1 }"
    })
    ChatMessage findLatestMessageBetweenUsers(String userId1, String userId2);
    
    // Get all unique conversation partners with latest message timestamp
    @Aggregation(pipeline = {
        "{ $match: { $or: [ { sender: ?0 }, { receiver: ?0 } ] } }",
        "{ $sort: { timeStamp: -1 } }",
        "{ $group: { _id: { $cond: [ { $eq: ['$sender', ?0] }, '$receiver', '$sender' ] }, lastMessage: { $first: '$$ROOT' } } }"
    })
    List<ChatMessage> findAllConversationsWithLatestMessage(String userId);
    
}
