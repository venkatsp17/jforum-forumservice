package com.example.forumservice.controller;

import com.example.forumservice.constant.HttpStatusConstants;
import com.example.forumservice.dto.ApiResponse;
import com.example.forumservice.dto.PostReportDTO;
import com.example.forumservice.model.PostReport;
import com.example.forumservice.service.PostService;
import com.example.forumservice.utils.ModelMapperUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/post-reports")
public class PostReportController {

    private final PostService postService;

    public PostReportController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/unresolved")
    public ResponseEntity<ApiResponse<List<PostReportDTO>>> listUnresolvedPostReports() {

        List<PostReport> unresolvedReports = postService.getUnresolvedPostReports();

        List<PostReportDTO> unresolvedReportsDTO = unresolvedReports.stream()
                .map(ModelMapperUtils::mapToPostReportDTO)
                .collect(Collectors.toList());

        ApiResponse<List<PostReportDTO>> response = new ApiResponse<>(
                HttpStatusConstants.OK.name(),
                unresolvedReportsDTO,
                "Unresolved post reports retrieved successfully."
        );

        return ResponseEntity.status(HttpStatusConstants.OK).body(response);
    }
}
