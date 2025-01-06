package com.example.forumservice.controller;

import com.example.forumservice.client.UserManagementClient;
import com.example.forumservice.client.dto.UserDTO;
import com.example.forumservice.constant.ErrorMessageConstants;
import com.example.forumservice.constant.HttpStatusConstants;
import com.example.forumservice.dto.ApiResponse;
import com.example.forumservice.dto.ModerationLogDTO;
import com.example.forumservice.exception.BadRequestException;
import com.example.forumservice.model.ModerationLog;
import com.example.forumservice.service.ModerationLogService;
import com.example.forumservice.utils.ConfigUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/moderation-log")
public class ModerationLogController {

    private final ModerationLogService moderationLogService;
    private final UserManagementClient userManagementClient;

    public ModerationLogController(ModerationLogService moderationLogService, UserManagementClient userManagementClient) {
        this.moderationLogService = moderationLogService;
        this.userManagementClient = userManagementClient;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ModerationLog>> registerModerationLogEntry(
            @Valid @RequestBody ModerationLogDTO moderationLogDTO,
            HttpServletRequest request) {

        String authToken = request.getHeader("X-auth-token");
        if (authToken == null || authToken.isEmpty()) {
            throw new BadRequestException(ErrorMessageConstants.AUTH_TOKEN_MISSING);
        }

        UserDTO currentUser = userManagementClient.getCurrentUser(authToken);
        if (currentUser == null) {
            throw new BadRequestException(ErrorMessageConstants.USER_NOT_FOUND);
        }

        boolean isModerationEnabled = ConfigUtils.isModerationLoggingEnabled();

        ModerationLog moderationLog = moderationLogService.registerModerationLog(moderationLogDTO, currentUser.getId(), isModerationEnabled);

        ApiResponse<ModerationLog> response = new ApiResponse<>(
                HttpStatusConstants.CREATED.name(),
                moderationLog,
                "Moderation log entry created successfully."
        );

        return ResponseEntity.status(HttpStatusConstants.CREATED).body(response);
    }
}
