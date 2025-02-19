package com.example.forumservice.APITest;

import com.example.forumservice.ForumServiceApplication;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ForumServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestMethodOrder(MethodOrderer.MethodName.class)
@Sql(scripts = {"/sql/DeleteForum/schema.sql", "/sql/DeleteForum/data.sql"})
public class DeleteForumTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test1DeleteExistingForum() throws Exception {
        mockMvc.perform(delete("/forums/{forumId}", 1))
            .andExpect(status().isNoContent());
    }

    @Test
    public void test2DeleteNonExistingForum() throws Exception {
        mockMvc.perform(delete("/forums/{forumId}", 9999))
            .andExpect(status().isNotFound());
    }

    @Test
    public void test3DeleteWithInvalidForumId() throws Exception {
        mockMvc.perform(delete("/forums/{forumId}", "abc"))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void test4DeleteWithNegativeForumId() throws Exception {
        mockMvc.perform(delete("/forums/{forumId}", -1))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void test5DeleteWithExcessivelyLargeForumId() throws Exception {
        mockMvc.perform(delete("/forums/{forumId}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }
}
