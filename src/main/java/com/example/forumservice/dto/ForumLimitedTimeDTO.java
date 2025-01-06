package com.example.forumservice.dto;

import com.example.forumservice.constant.ValidationMessageConstants;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ForumLimitedTimeDTO {

    @NotNull(message = ValidationMessageConstants.FORUM_LIMITED_TIME_REQUIRED)
    @Min(value = 0, message = ValidationMessageConstants.FORUM_LIMITED_TIME_MUST_BE_NON_NEGATIVE)
    private Long limitedTime;
}
