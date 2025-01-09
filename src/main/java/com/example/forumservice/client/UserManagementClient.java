package com.example.forumservice.client;

import com.example.forumservice.client.dto.OnlineUserDTO;
import com.example.forumservice.client.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UserManagementClient {

    public UserDTO getCurrentUser(String authToken) {
        // Mock implementation to simulate getting current user
        // In a real application, this would call the UserManagementService
        if ("valid-token".equals(authToken)) {
            UserDTO user = new UserDTO();
            user.setId(1L);
            user.setUsername("testuser");
            return user;
        } else {
            return null;
        }
    }

    public List<OnlineUserDTO> getOnlineUsers() {
        // TODO Auto-generated method stub
       // Simulated data for demonstration
        List<OnlineUserDTO> onlineUsers = new ArrayList<>();

        OnlineUserDTO user1 = new OnlineUserDTO();
        user1.setId(1L);
        user1.setUsername("Alice");

        OnlineUserDTO user2 = new OnlineUserDTO();
        user2.setId(2L);
        user2.setUsername("Bob");

        OnlineUserDTO user3 = new OnlineUserDTO();
        user3.setId(3L);
        user3.setUsername("Charlie");

        onlineUsers.add(user1);
        onlineUsers.add(user2);
        onlineUsers.add(user3);

        return onlineUsers;
    }

    public int getTotalRegisteredUsers() {
        // TODO Auto-generated method stub
        return 234;
    }

    public int getTotalLoggedUsers() {
        // TODO Auto-generated method stub
        return 12;
    }

    public UserDTO getLastRegisteredUser() {
        // Simulated data for demonstration
        UserDTO lastRegisteredUser = new UserDTO();
        lastRegisteredUser.setId(100L); // Replace with the ID of the last registered user
        lastRegisteredUser.setUsername("NewUser123"); // Replace with the username of the last registered user

        return lastRegisteredUser;
    }

    public UserDTO getUsernameByPostId(Long lastPostId) {
         // Simulated data for demonstration
         UserDTO lastRegisteredUser = new UserDTO();
         lastRegisteredUser.setId(100L); // Replace with the ID of the last registered user
         lastRegisteredUser.setUsername("NewUser123"); // Replace with the username of the last registered user

         return lastRegisteredUser;
    }

}
