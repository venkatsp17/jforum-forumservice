package com.example.forumservice.controller;

import com.example.forumservice.constant.HttpStatusConstants;
import com.example.forumservice.dto.ApiResponse;
import com.example.forumservice.dto.ForumDTO;
import com.example.forumservice.dto.ForumListResponse;
import com.example.forumservice.model.Forum;
import com.example.forumservice.service.ForumService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forums")
public class ForumController {

    private final ForumService forumService;

    public ForumController(ForumService forumService) {
        this.forumService = forumService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Forum>> addForum(@Valid @RequestBody ForumDTO forumDTO) {

        Forum createdForum = forumService.addForum(forumDTO);
        ApiResponse<Forum> response = new ApiResponse<>(
                HttpStatusConstants.CREATED.name(),
                createdForum,
                "Forum created successfully."
        );
        return ResponseEntity.status(HttpStatusConstants.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<ForumListResponse>> listForums() {

        ForumListResponse forumListResponse = forumService.getForums();
        ApiResponse<ForumListResponse> response = new ApiResponse<>(
                HttpStatusConstants.OK.name(),
                forumListResponse,
                "Forums retrieved successfully."
        );
        return ResponseEntity.status(HttpStatusConstants.OK).body(response);
    }
}
