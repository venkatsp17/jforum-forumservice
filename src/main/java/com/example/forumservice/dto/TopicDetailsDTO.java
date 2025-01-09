package com.example.forumservice.dto;

import com.example.forumservice.client.dto.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TopicDetailsDTO {
    private Long topicId;
    private String subject;
    private Long postCount;
    private String lastPostDate;
    private UserDTO lastAuthor;
    private Integer viewCount;
}
