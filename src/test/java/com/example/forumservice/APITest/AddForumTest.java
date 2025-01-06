package com.example.forumservice.APITest;

import com.example.forumservice.ForumServiceApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ForumServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Sql(scripts = {"/sql/AddForum/schema.sql", "/sql/AddForum/data.sql"})
public class AddForumTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test1_successfulForumCreation() throws Exception {
        String forumJson = "{ \"name\": \"New Forum\", \"category\": \"General\", \"description\": \"A new general discussion forum\" }";

        mockMvc.perform(MockMvcRequestBuilders.post("/forums")
                .contentType(MediaType.APPLICATION_JSON)
                .content(forumJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void test2_forumIsNull() throws Exception {
        String forumJson = "";

        mockMvc.perform(MockMvcRequestBuilders.post("/forums")
                .contentType(MediaType.APPLICATION_JSON)
                .content(forumJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test3_missingName() throws Exception {
        String forumJson = "{ \"category\": \"General\", \"description\": \"A forum without a name\" }";

        mockMvc.perform(MockMvcRequestBuilders.post("/forums")
                .contentType(MediaType.APPLICATION_JSON)
                .content(forumJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test4_missingCategory() throws Exception {
        String forumJson = "{ \"name\": \"Nameless Forum\", \"description\": \"A forum without a category\" }";

        mockMvc.perform(MockMvcRequestBuilders.post("/forums")
                .contentType(MediaType.APPLICATION_JSON)
                .content(forumJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test5_forumIdGreaterThanZero() throws Exception {
        String forumJson = "{ \"id\": 1, \"name\": \"Invalid Forum\", \"category\": \"General\", \"description\": \"Forum with id greater than zero\" }";

        mockMvc.perform(MockMvcRequestBuilders.post("/forums")
                .contentType(MediaType.APPLICATION_JSON)
                .content(forumJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test6_nameExceedsMaxLength() throws Exception {
        String longName = new String(new char[101]).replace('\0', 'a');
        String forumJson = "{ \"name\": \"" + longName + "\", \"category\": \"General\", \"description\": \"Forum with name exceeding max length\" }";

        mockMvc.perform(MockMvcRequestBuilders.post("/forums")
                .contentType(MediaType.APPLICATION_JSON)
                .content(forumJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test7_invalidDataType() throws Exception {
        String forumJson = "{ \"name\": 12345, \"category\": \"General\", \"description\": \"Forum with invalid data type for name\" }";

        mockMvc.perform(MockMvcRequestBuilders.post("/forums")
                .contentType(MediaType.APPLICATION_JSON)
                .content(forumJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test8_malformedJson() throws Exception {
        String forumJson = "{ \"name\": \"Invalid Forum\", \"category\" \"General\", \"description\": \"Missing colon after category\" }";

        mockMvc.perform(MockMvcRequestBuilders.post("/forums")
                .contentType(MediaType.APPLICATION_JSON)
                .content(forumJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test9_largePayload() throws Exception {
        String longDescription = new String(new char[1001]).replace('\0', 'a');
        String forumJson = "{ \"name\": \"Large Payload Forum\", \"category\": \"General\", \"description\": \"" + longDescription + "\" }";

        mockMvc.perform(MockMvcRequestBuilders.post("/forums")
                .contentType(MediaType.APPLICATION_JSON)
                .content(forumJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test10_deepNestedJson() throws Exception {
        String forumJson = "{ \"name\": \"Nested Forum\", \"category\": \"General\", \"description\": \"\", \"extra\": { \"subExtra\": { \"value\": \"Should not be here\" } } }";

        mockMvc.perform(MockMvcRequestBuilders.post("/forums")
                .contentType(MediaType.APPLICATION_JSON)
                .content(forumJson))
                .andExpect(status().isBadRequest());
    }

}
