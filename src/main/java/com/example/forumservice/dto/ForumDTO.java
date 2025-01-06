package com.example.forumservice.dto;

import com.example.forumservice.constant.ValidationMessageConstants;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ForumDTO {

    private Long id;

    @NotBlank(message = ValidationMessageConstants.FORUM_NAME_REQUIRED)
    private String name;

    @NotNull(message = ValidationMessageConstants.FORUM_CATEGORY_REQUIRED)
    private Long categoryId;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
