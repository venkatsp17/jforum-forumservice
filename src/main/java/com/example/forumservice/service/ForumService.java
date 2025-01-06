package com.example.forumservice.service;

import com.example.forumservice.dto.ForumDTO;
import com.example.forumservice.exception.BadRequestException;
import com.example.forumservice.exception.ResourceNotFoundException;
import com.example.forumservice.model.Category;
import com.example.forumservice.model.Forum;
import com.example.forumservice.repository.CategoryRepository;
import com.example.forumservice.repository.ForumRepository;
import com.example.forumservice.constant.ErrorMessageConstants;
import org.springframework.stereotype.Service;

@Service
public class ForumService {

    private final ForumRepository forumRepository;
    private final CategoryRepository categoryRepository;

    public ForumService(ForumRepository forumRepository, CategoryRepository categoryRepository) {
        this.forumRepository = forumRepository;
        this.categoryRepository = categoryRepository;
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
}
