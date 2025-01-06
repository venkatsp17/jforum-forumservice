package com.example.forumservice.controller;

import com.example.forumservice.dto.ApiResponse;
import com.example.forumservice.dto.ForumDTO;
import com.example.forumservice.model.Forum;
import com.example.forumservice.service.ForumService;
import com.example.forumservice.constant.HttpStatusConstants;
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
}
