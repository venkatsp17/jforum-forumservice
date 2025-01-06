package com.example.forumservice.APITest;

import com.example.forumservice.ForumServiceApplication;
import com.example.forumservice.model.ForumLimitedTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ForumServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Sql(scripts = {"/sql/SetForumLimitedTime/schema.sql", "/sql/SetForumLimitedTime/data.sql"})
public class SetForumLimitedTimeTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void test001_SetLimitedTime_Success() throws Exception {
        int forumId = 1;
        String url = "/forums/" + forumId + "/limitedTime";

        ForumLimitedTime forumLimitedTime = new ForumLimitedTime();
        forumLimitedTime.setLimitedTime(3600); // Set limited time to 3600 seconds

        String requestBody = objectMapper.writeValueAsString(forumLimitedTime);

        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    public void test002_SetLimitedTime_ForumNotFound() throws Exception {
        int forumId = 9999; // Assume this forumId does not exist
        String url = "/forums/" + forumId + "/limitedTime";

        ForumLimitedTime forumLimitedTime = new ForumLimitedTime();
        forumLimitedTime.setLimitedTime(3600);

        String requestBody = objectMapper.writeValueAsString(forumLimitedTime);

        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test003_SetLimitedTime_MissingRequestBody() throws Exception {
        int forumId = 1;
        String url = "/forums/" + forumId + "/limitedTime";

        // Missing request body
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test004_SetLimitedTime_MissingRequiredField() throws Exception {
        int forumId = 1;
        String url = "/forums/" + forumId + "/limitedTime";

        // Missing 'limitedTime' field
        String requestBody = "{}";

        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test005_SetLimitedTime_InvalidForumIdType() throws Exception {
        String forumId = "invalid"; // Invalid forumId type
        String url = "/forums/" + forumId + "/limitedTime";

        ForumLimitedTime forumLimitedTime = new ForumLimitedTime();
        forumLimitedTime.setLimitedTime(3600);

        String requestBody = objectMapper.writeValueAsString(forumLimitedTime);

        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test006_SetLimitedTime_InvalidLimitedTimeType() throws Exception {
        int forumId = 1;
        String url = "/forums/" + forumId + "/limitedTime";

        // Invalid 'limitedTime' type (string instead of integer)
        String requestBody = "{\"limitedTime\":\"invalid\"}";

        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test007_SetLimitedTime_MalformedJson() throws Exception {
        int forumId = 1;
        String url = "/forums/" + forumId + "/limitedTime";

        // Malformed JSON
        String requestBody = "{limitedTime:3600";

        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test008_SetLimitedTime_MaxLimitExceeded() throws Exception {
        int forumId = 1;
        String url = "/forums/" + forumId + "/limitedTime";

        ForumLimitedTime forumLimitedTime = new ForumLimitedTime();
        forumLimitedTime.setLimitedTime(Integer.MAX_VALUE + 1L); // Exceeding max integer value

        String requestBody = objectMapper.writeValueAsString(forumLimitedTime);

        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test009_SetLimitedTime_NegativeLimitedTime() throws Exception {
        int forumId = 1;
        String url = "/forums/" + forumId + "/limitedTime";

        ForumLimitedTime forumLimitedTime = new ForumLimitedTime();
        forumLimitedTime.setLimitedTime(-3600); // Negative limited time

        String requestBody = objectMapper.writeValueAsString(forumLimitedTime);

        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test010_SetLimitedTime_ZeroLimitedTime() throws Exception {
        int forumId = 1;
        String url = "/forums/" + forumId + "/limitedTime";

        ForumLimitedTime forumLimitedTime = new ForumLimitedTime();
        forumLimitedTime.setLimitedTime(0); // Zero limited time

        String requestBody = objectMapper.writeValueAsString(forumLimitedTime);

        // Assuming zero is acceptable and sets unlimited editing time
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk());
    }
}
