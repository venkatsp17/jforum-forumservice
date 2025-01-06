package com.example.forumservice.dto;

import com.example.forumservice.constant.ValidationMessageConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ForumDTO {

    private Long id;

    @NotBlank(message = ValidationMessageConstants.FORUM_NAME_REQUIRED)
    private String name;

    @NotNull(message = ValidationMessageConstants.FORUM_CATEGORY_REQUIRED)
    private Long categoryId;

    private String description;
}
