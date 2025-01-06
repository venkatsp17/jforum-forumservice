package com.example.forumservice.service;

import com.example.forumservice.client.UserManagementClient;
import com.example.forumservice.client.dto.OnlineUserDTO;
import com.example.forumservice.client.dto.UserDTO;
import com.example.forumservice.constant.ApplicationConstants;
import com.example.forumservice.constant.ErrorMessageConstants;
import com.example.forumservice.dto.CategoryDTO;
import com.example.forumservice.dto.ForumDTO;
import com.example.forumservice.dto.ForumListResponse;
import com.example.forumservice.dto.MostUsersEverOnlineDTO;
import com.example.forumservice.exception.BadRequestException;
import com.example.forumservice.exception.ResourceNotFoundException;
import com.example.forumservice.model.Category;
import com.example.forumservice.model.Forum;
import com.example.forumservice.repository.CategoryRepository;
import com.example.forumservice.repository.ForumRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ForumService {

    private final ForumRepository forumRepository;
    private final CategoryRepository categoryRepository;
    private final UserManagementClient userManagementClient;

    public ForumService(ForumRepository forumRepository, CategoryRepository categoryRepository,
                        UserManagementClient userManagementClient) {
        this.forumRepository = forumRepository;
        this.categoryRepository = categoryRepository;
        this.userManagementClient = userManagementClient;
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
                return forumDTO;
            }).collect(Collectors.toList());
            categoryDTO.setForums(forumDTOs);
            return categoryDTO;
        }).collect(Collectors.toList());

        List<OnlineUserDTO> onlineUsers = userManagementClient.getOnlineUsers();
        int totalRegisteredUsers = userManagementClient.getTotalRegisteredUsers();
        int totalMessages = forumRepository.getTotalMessages();
        int totalLoggedUsers = userManagementClient.getTotalLoggedUsers();
        int totalAnonymousUsers = userManagementClient.getTotalAnonymousUsers();
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
}
