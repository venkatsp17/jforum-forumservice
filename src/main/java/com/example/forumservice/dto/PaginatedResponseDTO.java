package com.example.forumservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaginatedResponseDTO<T> {
    private int page;
    private List<T> reports;
}
