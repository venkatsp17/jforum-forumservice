package com.example.forumservice.controller;

import com.example.forumservice.client.UserManagementClient;
import com.example.forumservice.client.dto.UserDTO;
import com.example.forumservice.constant.ApplicationConstants;
import com.example.forumservice.constant.ErrorMessageConstants;
import com.example.forumservice.constant.HttpStatusConstants;
import com.example.forumservice.dto.ApiResponse;
import com.example.forumservice.dto.LastPostDTO;
import com.example.forumservice.dto.PostDTO;
import com.example.forumservice.dto.PostReportDTO;
import com.example.forumservice.exception.BadRequestException;
import com.example.forumservice.exception.ResourceNotFoundException;
import com.example.forumservice.model.Post;
import com.example.forumservice.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.List;

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

        String authToken = request.getHeader(ApplicationConstants.AUTH_HEADER_NAME);
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

    @GetMapping("/last")
    public ResponseEntity<ApiResponse<LastPostDTO>> getLastPost(
            @RequestParam("id") Long id,
            @RequestParam("isTopic") Boolean isTopic) {

        if (id == null || id <= 0) {
            throw new BadRequestException(ErrorMessageConstants.INVALID_ID);
        }

        if (isTopic == null) {
            throw new BadRequestException(ErrorMessageConstants.INVALID_IS_TOPIC);
        }

        Post lastPost;
        if (isTopic) {
            lastPost = postService.getLastPostByTopicId(id);
        } else {
            lastPost = postService.getLastPostByForumId(id);
        }

        if (lastPost == null) {
            throw new ResourceNotFoundException(ErrorMessageConstants.LAST_POST_NOT_FOUND);
        }

        LastPostDTO lastPostDTO = new LastPostDTO();
        lastPostDTO.setPostId(lastPost.getId());
        lastPostDTO.setContent(lastPost.getContent());
        lastPostDTO.setCreatedAt(lastPost.getCreatedAt());
        lastPostDTO.setUserId(lastPost.getUserId());

        ApiResponse<LastPostDTO> response = new ApiResponse<>(
                HttpStatusConstants.OK.name(),
                lastPostDTO,
                "Last post retrieved successfully."
        );

        return ResponseEntity.status(HttpStatusConstants.OK).body(response);
    }

    @GetMapping("/topic/{topicId}")
    public ResponseEntity<ApiResponse<List<PostDTO>>> getPostsByTopic(@PathVariable Long topicId) {
        List<PostDTO> posts = postService.getPostsByTopic(topicId);
        ApiResponse<List<PostDTO>> response = new ApiResponse<>(
                "OK",
                posts,
                "Posts retrieved successfully."
        );
        return ResponseEntity.ok(response);
    }
}
