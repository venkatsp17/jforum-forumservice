package com.example.forumservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryDTO {
    private Long id;
    private String name;
    private String description;
    private List<ForumDTO> forums;
}
