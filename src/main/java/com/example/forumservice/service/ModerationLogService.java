package com.example.forumservice.service;

import com.example.forumservice.constant.ErrorMessageConstants;
import com.example.forumservice.dto.ModerationLogDTO;
import com.example.forumservice.exception.BadRequestException;
import com.example.forumservice.model.ModerationLog;
import com.example.forumservice.model.User;
import com.example.forumservice.repository.ModerationLogRepository;
import com.example.forumservice.repository.UserRepository;
import com.example.forumservice.utils.ModelMapperUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ModerationLogService {

    private final ModerationLogRepository moderationLogRepository;
    private final UserRepository userRepository;

    public ModerationLogService(ModerationLogRepository moderationLogRepository, UserRepository userRepository) {
        this.moderationLogRepository = moderationLogRepository;
        this.userRepository = userRepository;
    }

    public ModerationLog registerModerationLog(ModerationLogDTO moderationLogDTO, Long userId, boolean isModerationEnabled) {

        if (!isModerationEnabled) {
            throw new BadRequestException(ErrorMessageConstants.MODERATION_LOGGING_DISABLED);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException(ErrorMessageConstants.USER_NOT_FOUND));

        ModerationLog moderationLog = new ModerationLog();
        moderationLog.setLog(moderationLogDTO.getLog());
        moderationLog.setDate(moderationLogDTO.getDate() != null ? moderationLogDTO.getDate() : new Date());
        moderationLog.setOriginalMessage(moderationLogDTO.getOriginalMessage());
        moderationLog.setPosterUser(moderationLogDTO.getPosterUser());
        moderationLog.setPostId(moderationLogDTO.getPostId());
        moderationLog.setTopicId(moderationLogDTO.getTopicId());
        moderationLog.setUser(user);

        return moderationLogRepository.save(moderationLog);
    }
}
