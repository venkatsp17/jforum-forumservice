package com.example.forumservice.controller;

import com.example.forumservice.client.UserManagementClient;
import com.example.forumservice.client.dto.UserDTO;
import com.example.forumservice.constant.ErrorMessageConstants;
import com.example.forumservice.constant.HttpStatusConstants;
import com.example.forumservice.dto.ApiResponse;
import com.example.forumservice.dto.PostReportDTO;
import com.example.forumservice.exception.BadRequestException;
import com.example.forumservice.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final UserManagementClient userManagementClient;

    public PostController(PostService postService, UserManagementClient userManagementClient) {
        this.postService = postService;
        this.userManagementClient = userManagementClient;
    }

    @PostMapping("/{postId}/report")
    public ResponseEntity<ApiResponse<Object>> reportPost(
            @PathVariable Long postId,
            @Valid @RequestBody PostReportDTO postReportDTO,
            HttpServletRequest request) {

        if (postId == null || postId <= 0) {
            throw new BadRequestException(ErrorMessageConstants.INVALID_POST_ID);
        }

        String authToken = request.getHeader("X-auth-token");
        if (authToken == null || authToken.isEmpty()) {
            throw new BadRequestException(ErrorMessageConstants.AUTH_TOKEN_MISSING);
        }

        UserDTO currentUser = userManagementClient.getCurrentUser(authToken);
        if (currentUser == null) {
            throw new BadRequestException(ErrorMessageConstants.USER_NOT_FOUND);
        }

        postService.reportPost(postId, postReportDTO.getDescription(), currentUser.getId());

        ApiResponse<Object> response = new ApiResponse<>(
                HttpStatusConstants.CREATED.name(),
                "Post reported successfully."
        );

        return ResponseEntity.status(HttpStatusConstants.CREATED).body(response);
    }
}
