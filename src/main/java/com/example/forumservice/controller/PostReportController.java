package com.example.forumservice.controller;

import com.example.forumservice.client.UserManagementClient;
import com.example.forumservice.client.dto.UserDTO;
import com.example.forumservice.constant.ErrorMessageConstants;
import com.example.forumservice.constant.HttpStatusConstants;
import com.example.forumservice.dto.ApiResponse;
import com.example.forumservice.dto.PaginatedResponseDTO;
import com.example.forumservice.dto.PostReportDTO;
import com.example.forumservice.exception.BadRequestException;
import com.example.forumservice.exception.ForbiddenException;
import com.example.forumservice.exception.ResourceNotFoundException;
import com.example.forumservice.model.PostReport;
import com.example.forumservice.service.PostService;
import com.example.forumservice.utils.ModelMapperUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/post-reports")
public class PostReportController {

    private final PostService postService;
    private final UserManagementClient userManagementClient;

    public PostReportController(PostService postService, UserManagementClient userManagementClient) {
        this.postService = postService;
        this.userManagementClient = userManagementClient;
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

    @GetMapping("/resolved")
    public ResponseEntity<ApiResponse<PaginatedResponseDTO<PostReportDTO>>> listResolvedPostReports(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {

        if (page == null || page <= 0) {
            throw new BadRequestException(ErrorMessageConstants.INVALID_PAGE_NUMBER);
        }

        PaginatedResponseDTO<PostReportDTO> paginatedReports = postService.getPaginatedResolvedPostReports(page);

        ApiResponse<PaginatedResponseDTO<PostReportDTO>> response = new ApiResponse<>(
                HttpStatusConstants.OK.name(),
                paginatedReports,
                "Resolved post reports retrieved successfully."
        );

        return ResponseEntity.status(HttpStatusConstants.OK).body(response);
    }

    @PutMapping("/{reportId}/resolve")
    public ResponseEntity<ApiResponse<Object>> resolvePostReport(
            @PathVariable Long reportId,
            HttpServletRequest request) {

        if (reportId == null || reportId <= 0) {
            throw new BadRequestException(ErrorMessageConstants.INVALID_REPORT_ID);
        }

        String authToken = request.getHeader("X-auth-token");
        if (authToken == null || authToken.isEmpty()) {
            throw new BadRequestException(ErrorMessageConstants.AUTH_TOKEN_MISSING);
        }

        UserDTO currentUser = userManagementClient.getCurrentUser(authToken);
        if (currentUser == null) {
            throw new BadRequestException(ErrorMessageConstants.USER_NOT_FOUND);
        }

        postService.resolvePostReport(reportId, currentUser.getId());

        ApiResponse<Object> response = new ApiResponse<>(
                HttpStatusConstants.OK.name(),
                "Post report resolved successfully."
        );

        return ResponseEntity.status(HttpStatusConstants.OK).body(response);
    }

    @DeleteMapping("/{reportId}")
    public ResponseEntity<Void> deletePostReport(
            @PathVariable Long reportId,
            HttpServletRequest request) {

        if (reportId == null || reportId <= 0) {
            throw new BadRequestException(ErrorMessageConstants.INVALID_REPORT_ID);
        }

        String authToken = request.getHeader("X-auth-token");
        if (authToken == null || authToken.isEmpty()) {
            throw new BadRequestException(ErrorMessageConstants.AUTH_TOKEN_MISSING);
        }

        UserDTO currentUser = userManagementClient.getCurrentUser(authToken);
        if (currentUser == null) {
            throw new BadRequestException(ErrorMessageConstants.USER_NOT_FOUND);
        }

        // if (!currentUser.getRoles().contains("ADMIN")) {
        //     throw new ForbiddenException(ErrorMessageConstants.USER_CANNOT_MANIPULATE_REPORT);
        // }

        postService.deletePostReport(reportId);

        return ResponseEntity.noContent().build();
    }
}
