package com.example.forumservice.dto;

import lombok.Data;

import java.util.Date;

@Data
public class LastPostDTO {

    private Long postId;
    private String content;
    private Long userId;
    private Date createdAt;
}
