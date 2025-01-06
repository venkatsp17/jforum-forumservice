package com.example.forumservice.dto;

import com.example.forumservice.constant.ValidationMessageConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class PostReportDTO {

    private Long id;
    private Long postId;
    private Long userId;
    private Date reportDate;
    private String status;

    @NotBlank(message = ValidationMessageConstants.DESCRIPTION_REQUIRED)
    @Size(max = 1000, message = ValidationMessageConstants.DESCRIPTION_MAX_LENGTH)
    private String description;
}
