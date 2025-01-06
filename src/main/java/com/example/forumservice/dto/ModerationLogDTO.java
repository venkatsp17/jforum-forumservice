package com.example.forumservice.dto;

import com.example.forumservice.constant.ValidationMessageConstants;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;

@Data
public class ModerationLogDTO {

    @NotBlank(message = ValidationMessageConstants.MODERATION_LOG_REQUIRED)
    private String log;

    private Date date;

    private String originalMessage;

    private String posterUser;

    private Long postId;

    private Long topicId;
}
