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
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitConfig
@SpringBootTest(classes = ForumServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Sql(scripts = {"/sql/ListResolvedPostReports/schema.sql", "/sql/ListResolvedPostReports/data.sql"})
@TestMethodOrder(MethodOrderer.MethodName.class)
public class ListResolvedPostReportsTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetResolvedPostReportsSuccess() throws Exception {
        mockMvc.perform(get("/post-reports/resolved")
                .param("page", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.reports").isArray())
                .andExpect(jsonPath("$.page").value(1));
    }

    @Test
    public void testGetResolvedPostReportsMissingPageParameter() throws Exception {
        mockMvc.perform(get("/post-reports/resolved"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.reports").isArray());
    }

    @Test
    public void testGetResolvedPostReportsNegativePageNumber() throws Exception {
        mockMvc.perform(get("/post-reports/resolved")
                .param("page", "-1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetResolvedPostReportsZeroPageNumber() throws Exception {
        mockMvc.perform(get("/post-reports/resolved")
                .param("page", "0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetResolvedPostReportsLargePageNumber() throws Exception {
        mockMvc.perform(get("/post-reports/resolved")
                .param("page", "1000000"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.reports").isArray());
    }

    @Test
    public void testGetResolvedPostReportsInvalidPageNumberString() throws Exception {
        mockMvc.perform(get("/post-reports/resolved")
                .param("page", "abc"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetResolvedPostReportsDecimalPageNumber() throws Exception {
        mockMvc.perform(get("/post-reports/resolved")
                .param("page", "1.5"))
                .andExpect(status().isBadRequest());
    }
}
