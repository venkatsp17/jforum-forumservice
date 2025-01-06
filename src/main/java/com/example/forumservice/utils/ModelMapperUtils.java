package com.example.forumservice.utils;

import com.example.forumservice.dto.PostReportDTO;
import com.example.forumservice.model.PostReport;

public class ModelMapperUtils {

    public static PostReportDTO mapToPostReportDTO(PostReport postReport) {
        PostReportDTO dto = new PostReportDTO();
        dto.setId(postReport.getId());
        dto.setPostId(postReport.getPost().getId());
        dto.setUserId(postReport.getUserId());
        dto.setReportDate(postReport.getReportDate());
        dto.setDescription(postReport.getDescription());
        dto.setStatus(postReport.getStatus().name());
        return dto;
    }
}
