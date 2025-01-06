package com.example.forumservice.dto;

import com.example.forumservice.client.dto.OnlineUserDTO;
import com.example.forumservice.client.dto.UserDTO;
import lombok.Data;

import java.util.List;

@Data
public class ForumListResponse {

    private List<CategoryDTO> categories;
    private List<OnlineUserDTO> onlineUsers;
    private int totalRegisteredUsers;
    private int totalMessages;
    private int totalLoggedUsers;
    private int totalAnonymousUsers;
    private UserDTO lastRegisteredUser;
    private int postsPerPage;
    private MostUsersEverOnlineDTO mostUsersEverOnline;
}
