package com.example.forumservice.APITest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import com.example.forumservice.ForumServiceApplication;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.http.MediaType;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ForumServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Sql(scripts={"/sql/ListPostReports/schema.sql", "/sql/ListPostReports/data.sql"})
public class ListPostReportsTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test1_ListUnresolvedPostReports_Success() throws Exception {
        mockMvc.perform(get("/post-reports/unresolved")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void test2_ListUnresolvedPostReports_InvalidMethod_NotAllowed() throws Exception {
        mockMvc.perform(post("/post-reports/unresolved")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void test3_ListUnresolvedPostReports_InvalidEndpoint_NotFound() throws Exception {
        mockMvc.perform(get("/post-reports/nonexistent")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
