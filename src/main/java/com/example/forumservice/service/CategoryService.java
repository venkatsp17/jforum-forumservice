package com.example.forumservice.service;

import com.example.forumservice.dto.CategoryDTO;
import com.example.forumservice.dto.ForumDTO;
import com.example.forumservice.model.Category;
import com.example.forumservice.model.Forum;
import com.example.forumservice.repository.CategoryRepository;
import com.example.forumservice.constant.ErrorMessageConstants;
import com.example.forumservice.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> getAllCategories() {

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

        return categoryDTOs;
    }
}
