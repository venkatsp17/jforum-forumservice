package com.example.forumservice.APITest;

import com.example.forumservice.ForumServiceApplication;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;
import java.util.HashMap;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ForumServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestMethodOrder(MethodOrderer.MethodName.class)
@Sql(scripts = {"/sql/UpdateForum/schema.sql", "/sql/UpdateForum/data.sql"})
public class UpdateForumTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void test1_updateExistingForumSuccessfully() throws Exception {
        Map<String, Object> forum = new HashMap<>();
        forum.put("id", 1);
        forum.put("name", "Updated Forum Name");
        forum.put("description", "Updated description");
        forum.put("moderated", true);
        forum.put("allowAnonymous", false);
        forum.put("category", 2);

        String forumJson = objectMapper.writeValueAsString(forum);

        mockMvc.perform(put("/forums/{forumId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(forumJson))
            .andExpect(status().isOk());
    }

    @Test
    public void test2_updateNonExistingForum() throws Exception {
        Map<String, Object> forum = new HashMap<>();
        forum.put("id", 9999);
        forum.put("name", "Non-existing Forum");
        forum.put("description", "Description");
        forum.put("moderated", false);
        forum.put("allowAnonymous", true);
        forum.put("category", 1);

        String forumJson = objectMapper.writeValueAsString(forum);

        mockMvc.perform(put("/forums/{forumId}", 9999)
                .contentType(MediaType.APPLICATION_JSON)
                .content(forumJson))
            .andExpect(status().isNotFound());
    }

    @Test
    public void test3_updateForum_MissingName() throws Exception {
        Map<String, Object> forum = new HashMap<>();
        forum.put("id", 1);
        // Missing 'name'
        forum.put("description", "Description");
        forum.put("moderated", false);
        forum.put("allowAnonymous", true);
        forum.put("category", 1);

        String forumJson = objectMapper.writeValueAsString(forum);

        mockMvc.perform(put("/forums/{forumId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(forumJson))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void test4_updateForum_MissingCategory() throws Exception {
        Map<String, Object> forum = new HashMap<>();
        forum.put("id", 1);
        forum.put("name", "Forum Name");
        forum.put("description", "Description");
        forum.put("moderated", false);
        forum.put("allowAnonymous", true);
        // Missing 'category'

        String forumJson = objectMapper.writeValueAsString(forum);

        mockMvc.perform(put("/forums/{forumId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(forumJson))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void test5_updateForum_InvalidForumIdZero() throws Exception {
        Map<String, Object> forum = new HashMap<>();
        forum.put("id", 0);
        forum.put("name", "Forum Name");
        forum.put("description", "Description");
        forum.put("moderated", false);
        forum.put("allowAnonymous", true);
        forum.put("category", 1);

        String forumJson = objectMapper.writeValueAsString(forum);

        mockMvc.perform(put("/forums/{forumId}", 0)
                .contentType(MediaType.APPLICATION_JSON)
                .content(forumJson))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void test6_updateForum_NegativeForumId() throws Exception {
        Map<String, Object> forum = new HashMap<>();
        forum.put("id", -1);
        forum.put("name", "Forum Name");
        forum.put("description", "Description");
        forum.put("moderated", false);
        forum.put("allowAnonymous", true);
        forum.put("category", 1);

        String forumJson = objectMapper.writeValueAsString(forum);

        mockMvc.perform(put("/forums/{forumId}", -1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(forumJson))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void test7_updateForum_ExceedingNameLength() throws Exception {
        String longName = new String(new char[256]).replace('\0', 'a');
        Map<String, Object> forum = new HashMap<>();
        forum.put("id", 1);
        forum.put("name", longName);
        forum.put("description", "Description");
        forum.put("moderated", false);
        forum.put("allowAnonymous", true);
        forum.put("category", 1);

        String forumJson = objectMapper.writeValueAsString(forum);

        mockMvc.perform(put("/forums/{forumId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(forumJson))
            .andExpect(status().isBadRequest());
    }
}
