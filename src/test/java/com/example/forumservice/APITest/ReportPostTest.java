package com.example.forumservice.APITest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.forumservice.ForumServiceApplication;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ForumServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.MethodName.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Sql(scripts = {"/sql/ReportPost/schema.sql", "/sql/ReportPost/data.sql"})
public class ReportPostTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void test1_reportPostSuccess() throws Exception {
        // Assume postId 1 exists in the test data
        int postId = 1;
        String description = "This post contains inappropriate content.";

        String requestBody = objectMapper.writeValueAsString(new ReportPostRequest(description));

        mockMvc.perform(MockMvcRequestBuilders.post("/posts/{postId}/report", postId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void test2_reportPostNotFound() throws Exception {
        // Assume postId 999 does not exist
        int postId = 999;
        String description = "This post does not exist.";

        String requestBody = objectMapper.writeValueAsString(new ReportPostRequest(description));

        mockMvc.perform(MockMvcRequestBuilders.post("/posts/{postId}/report", postId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void test3_reportPostMissingDescription() throws Exception {
        // postId that exists
        int postId = 1;

        String requestBody = "{}"; // Empty JSON object, missing 'description'

        mockMvc.perform(MockMvcRequestBuilders.post("/posts/{postId}/report", postId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void test4_reportPostInvalidDescriptionType() throws Exception {
        // postId that exists
        int postId = 1;

        // 'description' should be a string, but we provide an integer
        String requestBody = "{\"description\": 12345}";

        mockMvc.perform(MockMvcRequestBuilders.post("/posts/{postId}/report", postId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void test5_reportPostInvalidPostIdType() throws Exception {
        // postId is invalid type (non-integer)
        String postId = "abc";
        String description = "Invalid postId type.";

        String requestBody = objectMapper.writeValueAsString(new ReportPostRequest(description));

        mockMvc.perform(MockMvcRequestBuilders.post("/posts/{postId}/report", postId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void test6_reportPostExceedDescriptionLength() throws Exception {
        // postId that exists
        int postId = 1;

        // Generate a very long description string
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5000; i++) {
            sb.append("a");
        }
        String longDescription = sb.toString();

        String requestBody = objectMapper.writeValueAsString(new ReportPostRequest(longDescription));

        mockMvc.perform(MockMvcRequestBuilders.post("/posts/{postId}/report", postId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void test7_reportPostMalformedJSON() throws Exception {
        // postId that exists
        int postId = 1;

        // Malformed JSON
        String requestBody = "{description: 'Missing quotes around key'}";

        mockMvc.perform(MockMvcRequestBuilders.post("/posts/{postId}/report", postId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    // Helper class for request body
    static class ReportPostRequest {
        private String description;

        public ReportPostRequest() {}

        public ReportPostRequest(String description) {
            this.description = description;
        }

        // Getter and Setter
        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

}
