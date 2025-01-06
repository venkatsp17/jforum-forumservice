package com.example.forumservice.client.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String displayName;
    private String email;
}
