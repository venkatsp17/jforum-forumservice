package com.example.forumservice.APITest;

import com.example.forumservice.ForumServiceApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.BeforeClass;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.HashMap;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ForumServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Sql(scripts = {"/sql/RegisterModerationLogEntry/schema.sql", "/sql/RegisterModerationLogEntry/data.sql"})
public class RegisterModerationLogEntryTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String ENDPOINT = "/moderation-log";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeClass
    public static void setUp() {
        // Any necessary setup before tests
    }

    // Success scenario: Valid request payload
    @Test
    public void test1_createModerationLogEntry_Success() throws Exception {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("log", "This is a test moderation log entry.");
        requestBody.put("date", "2023-10-16T12:00:00Z");
        requestBody.put("originalMessage", "Original message content.");
        requestBody.put("posterUser", "testUser");
        requestBody.put("postId", 1);
        requestBody.put("topicId", 1);

        String json = objectMapper.writeValueAsString(requestBody);

        mockMvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated()); // Expect 201 Created
    }

    // Failure scenario: Missing required field 'log'
    @Test
    public void test2_createModerationLogEntry_MissingRequiredField() throws Exception {
        Map<String, Object> requestBody = new HashMap<>();
        // 'log' field is missing
        requestBody.put("date", "2023-10-16T12:00:00Z");
        requestBody.put("originalMessage", "Original message content.");
        requestBody.put("posterUser", "testUser");
        requestBody.put("postId", 1);
        requestBody.put("topicId", 1);

        String json = objectMapper.writeValueAsString(requestBody);

        mockMvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest()); // Expect 400 Bad Request
    }

    // Failure scenario: Invalid data types
    @Test
    public void test3_createModerationLogEntry_InvalidDataTypes() throws Exception {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("log", 12345); // Should be a string
        requestBody.put("date", "invalid-date-format"); // Invalid date format
        requestBody.put("originalMessage", 67890); // Should be a string
        requestBody.put("posterUser", 123); // Should be a string
        requestBody.put("postId", "invalid-integer"); // Should be an integer
        requestBody.put("topicId", "invalid-integer"); // Should be an integer

        String json = objectMapper.writeValueAsString(requestBody);

        mockMvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest()); // Expect 400 Bad Request
    }

    // Failure scenario: Exceeding maximum string length
    @Test
    public void test4_createModerationLogEntry_ExceedMaxStringLength() throws Exception {
        // Assuming maximum length for 'log' is 10000 characters
        String longString = new String(new char[10001]).replace('\0', 'a');

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("log", longString);
        requestBody.put("date", "2023-10-16T12:00:00Z");
        requestBody.put("originalMessage", "Original message content.");
        requestBody.put("posterUser", "testUser");
        requestBody.put("postId", 1);
        requestBody.put("topicId", 1);

        String json = objectMapper.writeValueAsString(requestBody);

        mockMvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest()); // Expect 400 Bad Request
    }

    // Failure scenario: Missing request body
    @Test
    public void test5_createModerationLogEntry_MissingRequestBody() throws Exception {
        mockMvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()); // Expect 400 Bad Request
    }

    // Failure scenario: Malformed JSON
    @Test
    public void test6_createModerationLogEntry_MalformedJSON() throws Exception {
        String malformedJson = "{ \"log\": \"This is a test moderation log entry\", "; // Incomplete JSON

        mockMvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(malformedJson))
                .andExpect(status().isBadRequest()); // Expect 400 Bad Request
    }

    // Success scenario: Edge case with large payload
    @Test
    public void test7_createModerationLogEntry_LargePayload() throws Exception {
        // Create a large string but within acceptable limits
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            sb.append("a");
        }
        String largeString = sb.toString();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("log", largeString);
        requestBody.put("date", "2023-10-16T12:00:00Z");
        requestBody.put("originalMessage", largeString);
        requestBody.put("posterUser", largeString);
        requestBody.put("postId", Integer.MAX_VALUE);
        requestBody.put("topicId", Integer.MAX_VALUE);

        String json = objectMapper.writeValueAsString(requestBody);

        mockMvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated()); // Expect 201 Created
    }

    // Failure scenario: Invalid date format
    @Test
    public void test8_createModerationLogEntry_InvalidDateFormat() throws Exception {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("log", "This is a test moderation log entry.");
        requestBody.put("date", "16-10-2023 12:00:00"); // Incorrect format
        requestBody.put("originalMessage", "Original message content.");
        requestBody.put("posterUser", "testUser");
        requestBody.put("postId", 1);
        requestBody.put("topicId", 1);

        String json = objectMapper.writeValueAsString(requestBody);

        mockMvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest()); // Expect 400 Bad Request
    }

    // Failure scenario: Null values for non-required fields
    @Test
    public void test9_createModerationLogEntry_NullValues() throws Exception {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("log", "This is a test moderation log entry.");
        requestBody.put("date", null);
        requestBody.put("originalMessage", null);
        requestBody.put("posterUser", null);
        requestBody.put("postId", null);
        requestBody.put("topicId", null);

        String json = objectMapper.writeValueAsString(requestBody);

        mockMvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated()); // Expect 201 Created
    }

    // Failure scenario: Boundary value for integers
    @Test
    public void test10_createModerationLogEntry_BoundaryValuesForIntegers() throws Exception {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("log", "This is a test moderation log entry.");
        requestBody.put("date", "2023-10-16T12:00:00Z");
        requestBody.put("originalMessage", "Original message content.");
        requestBody.put("posterUser", "testUser");
        requestBody.put("postId", Integer.MAX_VALUE + 1); // Out of range
        requestBody.put("topicId", Integer.MIN_VALUE - 1); // Out of range

        String json = objectMapper.writeValueAsString(requestBody);

        mockMvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest()); // Expect 400 Bad Request
    }
}
