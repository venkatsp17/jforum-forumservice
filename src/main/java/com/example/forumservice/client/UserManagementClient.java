package com.example.forumservice.client;

import com.example.forumservice.client.dto.OnlineUserDTO;
import com.example.forumservice.client.dto.UserDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserManagementClient {

    public List<OnlineUserDTO> getOnlineUsers() {
        return new ArrayList<>();
    }

    public int getTotalRegisteredUsers() {
        return 0;
    }

    public int getTotalLoggedUsers() {
        return 0;
    }

    public int getTotalAnonymousUsers() {
        return 0;
    }

    public UserDTO getLastRegisteredUser() {
        return new UserDTO();
    }
}
