package com.example.forumservice.APITest;

import com.example.forumservice.ForumServiceApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ForumServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestMethodOrder(MethodOrderer.MethodName.class)
@Sql(scripts = {"/sql/ResolvePostReport/schema.sql", "/sql/ResolvePostReport/data.sql"})
public class ResolvePostReportTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test1_ResolvePostReport_Success() throws Exception {
        mockMvc.perform(put("/post-reports/1/resolve"))
                .andExpect(status().isOk());
    }

    @Test
    public void test2_ResolvePostReport_ReportNotFound() throws Exception {
        mockMvc.perform(put("/post-reports/99999/resolve"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test3_ResolvePostReport_InvalidReportId_NonInteger() throws Exception {
        mockMvc.perform(put("/post-reports/abc/resolve"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test4_ResolvePostReport_ReportIdZero() throws Exception {
        mockMvc.perform(put("/post-reports/0/resolve"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test5_ResolvePostReport_NegativeReportId() throws Exception {
        mockMvc.perform(put("/post-reports/-1/resolve"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test6_ResolvePostReport_MaxIntegerReportId() throws Exception {
        mockMvc.perform(put("/post-reports/" + Integer.MAX_VALUE + "/resolve"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test7_ResolvePostReport_ReportIdTooLarge() throws Exception {
        mockMvc.perform(put("/post-reports/999999999999999999/resolve"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test8_ResolvePostReport_ReportIdFloat() throws Exception {
        mockMvc.perform(put("/post-reports/1.5/resolve"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test9_ResolvePostReport_UnexpectedRequestBody() throws Exception {
        String requestBody = "{\"unexpectedField\":\"unexpectedValue\"}";
        mockMvc.perform(put("/post-reports/1/resolve")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test10_ResolvePostReport_MalformedJson() throws Exception {
        String malformedJson = "{\"unexpectedField\":\"unexpectedValue\"";
        mockMvc.perform(put("/post-reports/1/resolve")
                .contentType(MediaType.APPLICATION_JSON)
                .content(malformedJson))
                .andExpect(status().isBadRequest());
    }

}
