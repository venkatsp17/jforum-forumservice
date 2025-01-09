package com.example.forumservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class PostDTO {
    private Long id;
    private Long topicId;
    private Long forumId;
    private String content;
    private Date createdAt;
    private Long userId;
}
