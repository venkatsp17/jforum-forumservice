package com.example.forumservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForumCountDTO {
    private long topicCount;
    private long messageCount;
}

