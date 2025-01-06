package com.example.forumservice.controller;

import com.example.forumservice.dto.ApiResponse;
import com.example.forumservice.dto.CategoryDTO;
import com.example.forumservice.service.CategoryService;
import com.example.forumservice.constant.HttpStatusConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryDTO>>> listCategories() {

        List<CategoryDTO> categories = categoryService.getAllCategories();
        ApiResponse<List<CategoryDTO>> response = new ApiResponse<>(
                HttpStatusConstants.OK.name(),
                categories,
                "Categories retrieved successfully."
        );
        return ResponseEntity.status(HttpStatusConstants.OK).body(response);
    }
}
