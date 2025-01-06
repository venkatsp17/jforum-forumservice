package com.example.forumservice.APITest;

import com.example.forumservice.ForumServiceApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ForumServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@Sql(scripts = {"/sql/MoveForumDown/schema.sql", "/sql/MoveForumDown/data.sql"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MoveForumDownTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test01_MoveDown_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/forums/{forumId}/moveDown", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void test02_MoveDown_ForumNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/forums/{forumId}/moveDown", 999)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test03_MoveDown_AlreadyAtBottom() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/forums/{forumId}/moveDown", 3)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void test04_MoveDown_InvalidForumId_NonNumeric() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/forums/{forumId}/moveDown", "abc")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test05_MoveDown_InvalidForumId_Negative() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/forums/{forumId}/moveDown", -1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
