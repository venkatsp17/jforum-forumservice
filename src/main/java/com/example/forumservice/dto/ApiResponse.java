package com.example.forumservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private String status;
    private T data;
    private String message;

    /**
     * For error response that doesn't have data
     * @param status
     * @param message
     */
    public ApiResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    /**
     * For success response that includes data
     * @param status
     * @param data
     * @param message
     */
    public ApiResponse(String status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
}
