package com.example.forumservice.APITest;

import com.example.forumservice.ForumServiceApplication;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ForumServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
