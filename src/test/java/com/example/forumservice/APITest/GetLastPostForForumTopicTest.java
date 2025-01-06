package com.example.forumservice.APITest;

import com.example.forumservice.ForumServiceApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ForumServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Sql(scripts = {"/sql/GetLastPostForForumTopic/schema.sql", "/sql/GetLastPostForForumTopic/data.sql"})
public class GetLastPostForForumTopicTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test1_GetLastPostForTopic_Success() throws Exception {
        mockMvc.perform(get("/posts/last")
                .param("id", "1")
                .param("isTopic", "true")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void test2_GetLastPostForForum_Success() throws Exception {
        mockMvc.perform(get("/posts/last")
                .param("id", "1")
                .param("isTopic", "false")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void test3_MissingIdParameter() throws Exception {
        mockMvc.perform(get("/posts/last")
                .param("isTopic", "true")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test4_MissingIsTopicParameter() throws Exception {
        mockMvc.perform(get("/posts/last")
                .param("id", "1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test5_InvalidIdParameter_NonInteger() throws Exception {
        mockMvc.perform(get("/posts/last")
                .param("id", "abc")
                .param("isTopic", "true")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test6_InvalidIsTopicParameter_NonBoolean() throws Exception {
        mockMvc.perform(get("/posts/last")
                .param("id", "1")
                .param("isTopic", "notBoolean")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test7_NegativeIdParameter() throws Exception {
        mockMvc.perform(get("/posts/last")
                .param("id", "-1")
                .param("isTopic", "true")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test8_ZeroIdParameter() throws Exception {
        mockMvc.perform(get("/posts/last")
                .param("id", "0")
                .param("isTopic", "true")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test9_LargeIdParameter() throws Exception {
        mockMvc.perform(get("/posts/last")
                .param("id", "999999999")
                .param("isTopic", "true")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test10_InvalidBooleanParameter() throws Exception {
        mockMvc.perform(get("/posts/last")
                .param("id", "1")
                .param("isTopic", "123")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test11_MalformedRequest() throws Exception {
        mockMvc.perform(get("/posts/last?")
                .param("id", "1")
                .param("isTopic", "true")
                .param("extra", "value")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void test12_MissingAllParameters() throws Exception {
        mockMvc.perform(get("/posts/last")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
