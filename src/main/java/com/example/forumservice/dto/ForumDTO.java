package com.example.forumservice.dto;

import com.example.forumservice.constant.ValidationMessageConstants;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ForumDTO {

    private Long id;

    @NotBlank(message = ValidationMessageConstants.FORUM_NAME_REQUIRED)
    @Size(max = 255, message = ValidationMessageConstants.FORUM_NAME_MAX_LENGTH)
    private String name;

    @NotNull(message = ValidationMessageConstants.FORUM_CATEGORY_REQUIRED)
    private Long categoryId;

    private String description;

    private Boolean moderated;

    private Boolean allowAnonymous;

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

    public Boolean getModerated() {
        return moderated;
    }

    public void setModerated(Boolean moderated) {
        this.moderated = moderated;
    }

    public Boolean getAllowAnonymous() {
        return allowAnonymous;
    }

    public void setAllowAnonymous(Boolean allowAnonymous) {
        this.allowAnonymous = allowAnonymous;
    }
}
