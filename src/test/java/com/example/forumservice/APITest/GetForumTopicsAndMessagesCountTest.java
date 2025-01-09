package com.example.forumservice.APITest;

import com.example.forumservice.ForumServiceApplication;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = ForumServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Sql(scripts = {"/sql/GetForumTopicsAndMessagesCount/schema.sql", "/sql/GetForumTopicsAndMessagesCount/data.sql"})
public class GetForumTopicsAndMessagesCountTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void test01_getCounts_validForumId() throws Exception {
        // Test with a valid forumId that exists
        mockMvc.perform(get("/forums/1/count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.topicsCount").exists())
                .andExpect(jsonPath("$.messagesCount").exists());
    }

    @Test
    @Order(2)
    public void test02_getCounts_nonExistingForumId() throws Exception {
        // Test with a forumId that does not exist
        mockMvc.perform(get("/forums/9999/count"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(3)
    public void test03_getCounts_negativeForumId() throws Exception {
        // Test with a negative forumId
        mockMvc.perform(get("/forums/-1/count"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(4)
    public void test04_getCounts_invalidTypeForumId() throws Exception {
        // Test with an invalid type for forumId
        mockMvc.perform(get("/forums/abc/count"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(5)
    public void test05_getCounts_zeroForumId() throws Exception {
        // Test with a forumId of zero
        mockMvc.perform(get("/forums/0/count"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(6)
    public void test06_getCounts_maxIntegerForumId() throws Exception {
        // Test with the maximum integer value for forumId
        mockMvc.perform(get("/forums/" + Integer.MAX_VALUE + "/count"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(7)
    public void test07_getCounts_minIntegerForumId() throws Exception {
        // Test with the minimum integer value for forumId
        mockMvc.perform(get("/forums/" + Integer.MIN_VALUE + "/count"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(8)
    public void test08_getCounts_decimalForumId() throws Exception {
        // Test with a decimal value for forumId
        mockMvc.perform(get("/forums/1.5/count"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(9)
    public void test09_getCounts_specialCharactersForumId() throws Exception {
        // Test with special characters in forumId
        mockMvc.perform(get("/forums/@!#/count"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(10)
    public void test10_getCounts_missingForumId() throws Exception {
        // Test with missing forumId
        mockMvc.perform(get("/forums//count"))
                .andExpect(status().isNotFound());
    }
}
