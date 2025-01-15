package com.example.forumservice.service;

import com.example.forumservice.client.UserManagementClient;
import com.example.forumservice.client.dto.UserDTO;
import com.example.forumservice.dto.TopicDetailsDTO;
import com.example.forumservice.repository.TopicRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TopicService {

    private static final Logger logger = LoggerFactory.getLogger(TopicService.class); // Logger initialization
    
    private final TopicRepository topicRepository;
    private final UserManagementClient userClient; // A client to interact with the user microservice.

    public TopicService(TopicRepository topicRepository, UserManagementClient userClient) {
        this.topicRepository = topicRepository;
        this.userClient = userClient;
    }

    public List<TopicDetailsDTO> getTopicsByForumWithPostDetailsDTOs(Long forumId) {
        logger.debug("Fetching topics for forumId: {}", forumId); // Log the input parameter (forumId)
        System.out.println("Fetching topics for forumId: " + forumId); // Print the input parameter (forumId)
        List<Object[]> rawTopics = topicRepository.getTopicsWithPostDetailsByForumId(forumId);

        // Log the raw topics data fetched from the database
        logger.debug("Raw topics fetched: {}", rawTopics);

        // Process the raw data and map it to TopicDetailsDTO
        return rawTopics.stream().map(row -> {
            Long topicId = row[0] != null ? ((Number) row[0]).longValue() : -1L; // Ensure proper casting
            String subject = row[1] != null ? (String) row[1] : "Unknown Subject";
            Long postCount = row[2] != null ? ((Number) row[2]).longValue() : 0L; // Cast Integer to Long
            Long lastPostId = row[3] != null ? ((Number) row[3]).longValue() : -1L; // Cast Integer to Long
            Date lastPostDate = row[4] != null ? (Date) row[4] : new Date(0);
            Integer viewCount = row[5] != null ? ((Number) row[5]).intValue() : 0; // Ensure Integer casting

            // Log the extracted values for each topic
            logger.debug("Mapping raw row to TopicDetailsDTO: topicId={}, subject={}, postCount={}, lastPostId={}, lastPostDate={}, viewCount={}",
                topicId, subject, postCount, lastPostId, lastPostDate, viewCount);
        
            // Fetch the last author's username from the user microservice
            UserDTO lastAuthorUsername = userClient.getUsernameByPostId(lastPostId);
            if (lastAuthorUsername == null) {
                lastAuthorUsername = new UserDTO();
                lastAuthorUsername.setUsername("Unknown User");
            }

            // Log the last author's username
            logger.debug("Last post author username: {}", lastAuthorUsername.getUsername());

            return new TopicDetailsDTO(topicId, subject, postCount, lastPostDate.toString(), lastAuthorUsername, viewCount);
        }).toList();
    }
}
