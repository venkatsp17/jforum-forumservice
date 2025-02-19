package com.example.forumservice.service;

import com.example.forumservice.client.UserManagementClient;
import com.example.forumservice.client.dto.OnlineUserDTO;
import com.example.forumservice.client.dto.UserDTO;
import com.example.forumservice.constant.ApplicationConstants;
import com.example.forumservice.constant.ErrorMessageConstants;
import com.example.forumservice.constant.ValidationMessageConstants;
import com.example.forumservice.dto.CategoryDTO;
import com.example.forumservice.dto.ForumDTO;
import com.example.forumservice.dto.ForumListResponse;
import com.example.forumservice.dto.ForumCountDTO;
import com.example.forumservice.dto.MostUsersEverOnlineDTO;
import com.example.forumservice.exception.BadRequestException;
import com.example.forumservice.exception.ResourceNotFoundException;
import com.example.forumservice.model.Category;
import com.example.forumservice.model.Forum;
import com.example.forumservice.model.ForumLimitedTime;
import com.example.forumservice.repository.CategoryRepository;
import com.example.forumservice.repository.ForumLimitedTimeRepository;
import com.example.forumservice.repository.ForumRepository;
import com.example.forumservice.utils.DateUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ForumService {

    private final ForumRepository forumRepository;
    private final CategoryRepository categoryRepository;
    private final UserManagementClient userManagementClient;
    private final ForumLimitedTimeRepository forumLimitedTimeRepository;

    public ForumService(ForumRepository forumRepository,
                        CategoryRepository categoryRepository,
                        UserManagementClient userManagementClient,
                        ForumLimitedTimeRepository forumLimitedTimeRepository) {
        this.forumRepository = forumRepository;
        this.categoryRepository = categoryRepository;
        this.userManagementClient = userManagementClient;
        this.forumLimitedTimeRepository = forumLimitedTimeRepository;
    }

    public Forum addForum(ForumDTO forumDTO) {

        if (forumDTO == null) {
            throw new BadRequestException(ErrorMessageConstants.FORUM_CANNOT_BE_NULL);
        }

        if (forumDTO.getId() != null && forumDTO.getId() > 0) {
            throw new BadRequestException(ErrorMessageConstants.FORUM_ID_MUST_BE_ZERO);
        }

        Category category = categoryRepository.findById(forumDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstants.CATEGORY_NOT_FOUND));

        Forum forum = new Forum();
        forum.setName(forumDTO.getName());
        forum.setDescription(forumDTO.getDescription());
        forum.setCategory(category);

        Forum savedForum = forumRepository.save(forum);

        return savedForum;
    }

    public Forum updateForum(ForumDTO forumDTO) {

        if (forumDTO == null) {
            throw new BadRequestException(ErrorMessageConstants.FORUM_CANNOT_BE_NULL);
        }

        if (forumDTO.getId() == null || forumDTO.getId() <= 0) {
            throw new BadRequestException(ErrorMessageConstants.FORUM_ID_MUST_BE_VALID);
        }

        Forum existingForum = forumRepository.findById(forumDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstants.FORUM_NOT_FOUND));

        if (forumDTO.getName() == null || forumDTO.getName().isEmpty()) {
            throw new BadRequestException(ValidationMessageConstants.FORUM_NAME_REQUIRED);
        }

        if (forumDTO.getCategoryId() == null || forumDTO.getCategoryId() <= 0) {
            throw new BadRequestException(ValidationMessageConstants.FORUM_CATEGORY_REQUIRED);
        }

        Category category = categoryRepository.findById(forumDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstants.CATEGORY_NOT_FOUND));

        existingForum.setName(forumDTO.getName());
        existingForum.setDescription(forumDTO.getDescription());
        existingForum.setModerated(forumDTO.getModerated() != null ? forumDTO.getModerated() : existingForum.isModerated());
        existingForum.setAllowAnonymousPosts(forumDTO.getAllowAnonymous() != null ? forumDTO.getAllowAnonymous() : existingForum.isAllowAnonymousPosts());
        existingForum.setCategory(category);

        Forum updatedForum = forumRepository.save(existingForum);

        return updatedForum;
    }

    public ForumListResponse getForums() {

        List<Category> categories = categoryRepository.findAll();

        List<CategoryDTO> categoryDTOs = categories.stream().map(category -> {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(category.getId());
            categoryDTO.setName(category.getName());
            categoryDTO.setDescription(category.getDescription());
            List<ForumDTO> forumDTOs = category.getForums().stream().map(forum -> {
                ForumDTO forumDTO = new ForumDTO();
                forumDTO.setId(forum.getId());
                forumDTO.setName(forum.getName());
                forumDTO.setDescription(forum.getDescription());
                forumDTO.setCategoryId(category.getId());
                forumDTO.setModerated(forum.isModerated());
                forumDTO.setAllowAnonymous(forum.isAllowAnonymousPosts());
                return forumDTO;
            }).collect(Collectors.toList());
            categoryDTO.setForums(forumDTOs);
            return categoryDTO;
        }).collect(Collectors.toList());

        List<OnlineUserDTO> onlineUsers = userManagementClient.getOnlineUsers();
        int totalRegisteredUsers = userManagementClient.getTotalRegisteredUsers();
        int totalMessages = forumRepository.getTotalMessages();
        int totalLoggedUsers = userManagementClient.getTotalLoggedUsers();
        int totalAnonymousUsers = userManagementClient.getTotalLoggedUsers();
        UserDTO lastRegisteredUser = userManagementClient.getLastRegisteredUser();
        int postsPerPage = ApplicationConstants.POSTS_PER_PAGE;

        MostUsersEverOnlineDTO mostUsersEverOnline = new MostUsersEverOnlineDTO();
        mostUsersEverOnline.setCount(0);
        mostUsersEverOnline.setTimestamp(DateUtils.getCurrentTimestamp());

        ForumListResponse response = new ForumListResponse();
        response.setCategories(categoryDTOs);
        response.setOnlineUsers(onlineUsers);
        response.setTotalRegisteredUsers(totalRegisteredUsers);
        response.setTotalMessages(totalMessages);
        response.setTotalLoggedUsers(totalLoggedUsers);
        response.setTotalAnonymousUsers(totalAnonymousUsers);
        response.setLastRegisteredUser(lastRegisteredUser);
        response.setPostsPerPage(postsPerPage);
        response.setMostUsersEverOnline(mostUsersEverOnline);

        return response;
    }

    public void deleteForum(Long forumId) {

        Forum forum = forumRepository.findById(forumId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstants.FORUM_NOT_FOUND));

        forumRepository.delete(forum);
    }

    public Forum moveForumUp(Long forumId) {

        Forum forumToMove = forumRepository.findById(forumId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstants.FORUM_NOT_FOUND));

        Category category = forumToMove.getCategory();

        List<Forum> forumsInCategory = forumRepository.findByCategoryOrderByDisplayOrderAsc(category);

        int index = forumsInCategory.indexOf(forumToMove);

        if (index > 0) {
            Forum previousForum = forumsInCategory.get(index - 1);

            int tempOrder = forumToMove.getDisplayOrder();
            forumToMove.setDisplayOrder(previousForum.getDisplayOrder());
            previousForum.setDisplayOrder(tempOrder);

            forumRepository.save(forumToMove);
            forumRepository.save(previousForum);

            return forumToMove;
        } else {
            throw new BadRequestException(ErrorMessageConstants.FORUM_ALREADY_AT_TOP);
        }
    }

    public Forum moveForumDown(Long forumId) {

        Forum forumToMove = forumRepository.findById(forumId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstants.FORUM_NOT_FOUND));

        Category category = forumToMove.getCategory();

        List<Forum> forumsInCategory = forumRepository.findByCategoryOrderByDisplayOrderAsc(category);

        int index = forumsInCategory.indexOf(forumToMove);

        if (index < forumsInCategory.size() - 1) {
            Forum nextForum = forumsInCategory.get(index + 1);

            int tempOrder = forumToMove.getDisplayOrder();
            forumToMove.setDisplayOrder(nextForum.getDisplayOrder());
            nextForum.setDisplayOrder(tempOrder);

            forumRepository.save(forumToMove);
            forumRepository.save(nextForum);
        } else {
            throw new BadRequestException(ErrorMessageConstants.FORUM_ALREADY_AT_BOTTOM);
        }

        return forumToMove;
    }

    public long getForumLimitedTime(Long forumId) {

        Forum forum = forumRepository.findById(forumId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstants.FORUM_NOT_FOUND));

        Optional<ForumLimitedTime> forumLimitedTimeOptional = forumLimitedTimeRepository.findByForum(forum);

        return forumLimitedTimeOptional.map(ForumLimitedTime::getLimitedTime).orElse(0L);
    }

    public ForumLimitedTime setForumLimitedTime(Long forumId, Long limitedTime) {

        if (forumId == null || forumId <= 0) {
            throw new BadRequestException(ErrorMessageConstants.FORUM_ID_MUST_BE_VALID);
        }

        if (limitedTime == null) {
            throw new BadRequestException(ValidationMessageConstants.FORUM_LIMITED_TIME_REQUIRED);
        }

        if (limitedTime < 0) {
            throw new BadRequestException(ValidationMessageConstants.FORUM_LIMITED_TIME_MUST_BE_NON_NEGATIVE);
        }

        Forum forum = forumRepository.findById(forumId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstants.FORUM_NOT_FOUND));

        Optional<ForumLimitedTime> forumLimitedTimeOptional = forumLimitedTimeRepository.findByForum(forum);

        ForumLimitedTime forumLimitedTime;

        if (forumLimitedTimeOptional.isPresent()) {
            forumLimitedTime = forumLimitedTimeOptional.get();
            forumLimitedTime.setLimitedTime(limitedTime);
        } else {
            forumLimitedTime = new ForumLimitedTime();
            forumLimitedTime.setForum(forum);
            forumLimitedTime.setLimitedTime(limitedTime);
        }

        ForumLimitedTime savedForumLimitedTime = forumLimitedTimeRepository.save(forumLimitedTime);

        return savedForumLimitedTime;
    }

    public ForumCountDTO getForumCounts(Long forumId) {

        Forum forum = forumRepository.findById(forumId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstants.FORUM_NOT_FOUND));

        long topicCount = forumRepository.countTopicsByForumId(forumId);
        long messageCount = forumRepository.countMessagesByForumId(forumId);

        ForumCountDTO forumCountDTO = new ForumCountDTO(topicCount, messageCount);

        return forumCountDTO;
    }
}
