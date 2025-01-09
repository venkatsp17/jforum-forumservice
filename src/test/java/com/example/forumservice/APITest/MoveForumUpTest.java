package com.example.forumservice.APITest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.forumservice.ForumServiceApplication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ForumServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestMethodOrder(MethodOrderer.MethodName.class)
@Sql(scripts = {"/sql/MoveForumUp/schema.sql", "/sql/MoveForumUp/data.sql"})
public class MoveForumUpTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test1_MoveForumUpSuccess() throws Exception {
        // Move forum with ID 2 up
        mockMvc.perform(patch("/forums/{forumId}/moveUp", 2))
               .andExpect(status().isOk());
    }

    @Test
    public void test2_MoveForumUpForumNotFound() throws Exception {
        // Attempt to move a non-existent forum
        mockMvc.perform(patch("/forums/{forumId}/moveUp", 9999))
               .andExpect(status().isNotFound());
    }

    @Test
    public void test3_MoveForumUpAlreadyAtTop() throws Exception {
        // Attempt to move the top forum up
        mockMvc.perform(patch("/forums/{forumId}/moveUp", 1))
               .andExpect(status().isBadRequest());
    }

    @Test
    public void test4_MoveForumUpInvalidForumId() throws Exception {
        // Attempt to move a forum with invalid ID format
        mockMvc.perform(patch("/forums/invalidId/moveUp"))
               .andExpect(status().isBadRequest());
    }

    @Test
    public void test5_MoveForumUpNegativeForumId() throws Exception {
        // Attempt to move a forum with a negative ID
        mockMvc.perform(patch("/forums/{forumId}/moveUp", -1))
               .andExpect(status().isBadRequest());
    }
}
