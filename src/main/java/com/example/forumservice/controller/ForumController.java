package com.example.forumservice.controller;

import com.example.forumservice.constant.ErrorMessageConstants;
import com.example.forumservice.constant.HttpStatusConstants;
import com.example.forumservice.dto.ApiResponse;
import com.example.forumservice.dto.ForumDTO;
import com.example.forumservice.dto.ForumLimitedTimeDTO;
import com.example.forumservice.dto.ForumListResponse;
import com.example.forumservice.dto.TopicDetailsDTO;
import com.example.forumservice.dto.ForumCountDTO;
import com.example.forumservice.exception.BadRequestException;
import com.example.forumservice.model.Forum;
import com.example.forumservice.model.ForumLimitedTime;
import com.example.forumservice.service.ForumService;
import com.example.forumservice.service.TopicService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forums")
public class ForumController {

    private final ForumService forumService;

    @Autowired
    private TopicService topicService;

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

    @PutMapping("/{forumId}")
    public ResponseEntity<ApiResponse<Forum>> updateForum(
            @PathVariable Long forumId,
            @Valid @RequestBody ForumDTO forumDTO) {

        if (forumId == null || forumId <= 0 || !forumId.equals(forumDTO.getId())) {
            throw new BadRequestException(ErrorMessageConstants.FORUM_ID_MUST_BE_VALID);
        }

        Forum updatedForum = forumService.updateForum(forumDTO);

        ApiResponse<Forum> response = new ApiResponse<>(
                HttpStatusConstants.OK.name(),
                updatedForum,
                "Forum updated successfully."
        );

        return ResponseEntity.status(HttpStatusConstants.OK).body(response);
    }

    @DeleteMapping("/{forumId}")
    public ResponseEntity<Void> deleteForum(@PathVariable Long forumId) {

        if (forumId == null || forumId <= 0) {
            throw new BadRequestException(ErrorMessageConstants.FORUM_ID_MUST_BE_VALID);
        }

        forumService.deleteForum(forumId);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{forumId}/moveUp")
    public ResponseEntity<ApiResponse<Forum>> moveForumUp(@PathVariable Long forumId) {

        if (forumId == null || forumId <= 0) {
            throw new BadRequestException(ErrorMessageConstants.FORUM_ID_MUST_BE_VALID);
        }

        Forum movedForum = forumService.moveForumUp(forumId);

        ApiResponse<Forum> response = new ApiResponse<>(
                HttpStatusConstants.OK.name(),
                movedForum,
                "Forum moved up successfully."
        );

        return ResponseEntity.status(HttpStatusConstants.OK).body(response);
    }

    @PatchMapping("/{forumId}/moveDown")
    public ResponseEntity<ApiResponse<Forum>> moveForumDown(@PathVariable Long forumId) {

        if (forumId == null || forumId <= 0) {
            throw new BadRequestException(ErrorMessageConstants.FORUM_ID_MUST_BE_VALID);
        }

        Forum movedForum = forumService.moveForumDown(forumId);

        ApiResponse<Forum> response = new ApiResponse<>(
                HttpStatusConstants.OK.name(),
                movedForum,
                "Forum moved down successfully."
        );

        return ResponseEntity.status(HttpStatusConstants.OK).body(response);
    }

    @GetMapping("/{forumId}/limitedTime")
    public ResponseEntity<ApiResponse<Long>> getForumLimitedTime(@PathVariable Long forumId) {

        if (forumId == null || forumId <= 0) {
            throw new BadRequestException(ErrorMessageConstants.FORUM_ID_MUST_BE_VALID);
        }

        long limitedTime = forumService.getForumLimitedTime(forumId);

        ApiResponse<Long> response = new ApiResponse<>(
                HttpStatusConstants.OK.name(),
                limitedTime,
                "Limited time retrieved successfully."
        );

        return ResponseEntity.status(HttpStatusConstants.OK).body(response);
    }

    @PostMapping("/{forumId}/limitedTime")
    public ResponseEntity<ApiResponse<ForumLimitedTime>> setForumLimitedTime(
            @PathVariable Long forumId,
            @Valid @RequestBody ForumLimitedTimeDTO forumLimitedTimeDTO) {

        if (forumId == null || forumId <= 0) {
            throw new BadRequestException(ErrorMessageConstants.FORUM_ID_MUST_BE_VALID);
        }

        ForumLimitedTime updatedForumLimitedTime = forumService.setForumLimitedTime(forumId, forumLimitedTimeDTO.getLimitedTime());

        ApiResponse<ForumLimitedTime> response = new ApiResponse<>(
                HttpStatusConstants.OK.name(),
                updatedForumLimitedTime,
                "Limited time set successfully."
        );

        return ResponseEntity.status(HttpStatusConstants.OK).body(response);
    }

    @GetMapping("/{forumId}/count")
    public ResponseEntity<ApiResponse<ForumCountDTO>> getForumCounts(@PathVariable Long forumId) {

        if (forumId == null || forumId <= 0) {
            throw new BadRequestException(ErrorMessageConstants.FORUM_ID_MUST_BE_VALID);
        }

        ForumCountDTO forumCountDTO = forumService.getForumCounts(forumId);

        ApiResponse<ForumCountDTO> response = new ApiResponse<>(
                HttpStatusConstants.OK.name(),
                forumCountDTO,
                "Counts retrieved successfully."
        );

        return ResponseEntity.status(HttpStatusConstants.OK).body(response);
    }

    @GetMapping("/{forumId}/topics")
    public ResponseEntity<ApiResponse<List<TopicDetailsDTO>>> getTopicsByForum(@PathVariable Long forumId) {
        if (forumId == null || forumId <= 0) {
            throw new BadRequestException(ErrorMessageConstants.FORUM_ID_MUST_BE_VALID);
        }
        List<TopicDetailsDTO> topics = topicService.getTopicsByForumWithPostDetailsDTOs(forumId);
        ApiResponse<List<TopicDetailsDTO>> response = new ApiResponse<>(
                HttpStatusConstants.OK.name(),
                topics,
                "Topics retrieved successfully."
        );
        return ResponseEntity.status(HttpStatusConstants.OK).body(response);
    }
}
