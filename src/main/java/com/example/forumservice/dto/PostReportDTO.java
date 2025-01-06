package com.example.forumservice.dto;

import com.example.forumservice.constant.ValidationMessageConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PostReportDTO {

    @NotBlank(message = ValidationMessageConstants.DESCRIPTION_REQUIRED)
    @Size(max = 1000, message = ValidationMessageConstants.DESCRIPTION_MAX_LENGTH)
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
