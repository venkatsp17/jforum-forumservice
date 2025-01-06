package com.example.forumservice.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MostUsersEverOnlineDTO {
    private int count;
    private Date timestamp;
}
