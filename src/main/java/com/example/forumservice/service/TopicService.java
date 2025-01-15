package com.example.forumservice.service;

import com.example.forumservice.client.UserManagementClient;
import com.example.forumservice.client.dto.UserDTO;
import com.example.forumservice.dto.TopicDetailsDTO;
import com.example.forumservice.repository.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final UserManagementClient userClient; // A client to interact with the user microservice.

    public TopicService(TopicRepository topicRepository, UserManagementClient userClient) {
        this.topicRepository = topicRepository;
        this.userClient = userClient;
    }

    public List<TopicDetailsDTO> getTopicsByForum(Long forumId) {
        List<Object[]> rawTopics = topicRepository.getTopicsWithPostDetails(forumId);

        System.out.println("Raw topics: " + rawTopics);

        return rawTopics.stream().map(row -> {
            Long topicId = row[0] != null ? ((Number) row[0]).longValue() : -1L; // Ensure proper casting
            String subject = row[1] != null ? (String) row[1] : "Unknown Subject";
            Long postCount = row[2] != null ? ((Number) row[2]).longValue() : 0L; // Cast Integer to Long
            Long lastPostId = row[3] != null ? ((Number) row[3]).longValue() : -1L; // Cast Integer to Long
            Date lastPostDate = row[4] != null ? (Date) row[4] : new Date(0);
            Integer viewCount = row[5] != null ? ((Number) row[5]).intValue() : 0; // Ensure Integer casting
        
            // Fetch the last author's username from the user microservice
            UserDTO lastAuthorUsername = userClient.getUsernameByPostId(lastPostId);
            if (lastAuthorUsername == null) {
                lastAuthorUsername = new UserDTO();
                lastAuthorUsername.setUsername("Unknown User");
            }
        
            return new TopicDetailsDTO(topicId, subject, postCount, lastPostDate.toString(), lastAuthorUsername, viewCount);
        }).toList();
        
    }
}
