package com.example.forumservice.APITest;

import com.example.forumservice.ForumServiceApplication;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith(SpringExtension.class)
@SpringJUnitConfig(classes = ForumServiceApplication.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestMethodOrder(MethodOrderer.MethodName.class)
@Sql(scripts = {"/sql/GetForumLimitedTime/schema.sql", "/sql/GetForumLimitedTime/data.sql"})
public class GetForumLimitedTimeTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetForumLimitedTime_Success() throws Exception {
        int validForumId = 1;

        mockMvc.perform(get("/forums/{forumId}/limitedTime", validForumId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetForumLimitedTime_ForumNotFound() throws Exception {
        int nonExistentForumId = 999;

        mockMvc.perform(get("/forums/{forumId}/limitedTime", nonExistentForumId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetForumLimitedTime_InvalidForumId_NonInteger() throws Exception {
        String invalidForumId = "abc";

        mockMvc.perform(get("/forums/{forumId}/limitedTime", invalidForumId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetForumLimitedTime_InvalidForumId_Negative() throws Exception {
        int negativeForumId = -1;

        mockMvc.perform(get("/forums/{forumId}/limitedTime", negativeForumId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
