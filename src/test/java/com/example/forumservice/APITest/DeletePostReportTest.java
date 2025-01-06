package com.example.forumservice.APITest;

import com.example.forumservice.ForumServiceApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ForumServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Sql(scripts = {"/sql/DeletePostReport/schema.sql", "/sql/DeletePostReport/data.sql"})
public class DeletePostReportTest {

    @Autowired
    private MockMvc mockMvc;

    // Test Case 1: Successfully delete an existing post report
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void test1_deleteExistingPostReport_success() throws Exception {
        int reportId = 1; // Assuming reportId 1 exists in data.sql

        mockMvc.perform(delete("/post-reports/{reportId}", reportId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    // Test Case 2: Attempt to delete a non-existent post report
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void test2_deleteNonExistentPostReport_notFound() throws Exception {
        int reportId = 9999; // Assuming reportId 9999 does not exist

        mockMvc.perform(delete("/post-reports/{reportId}", reportId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    // Test Case 3: Attempt to delete with invalid reportId (negative number)
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void test3_deletePostReport_invalidReportId_negativeNumber() throws Exception {
        int reportId = -1;

        mockMvc.perform(delete("/post-reports/{reportId}", reportId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    // Test Case 4: Attempt to delete with invalid reportId (zero)
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void test4_deletePostReport_invalidReportId_zero() throws Exception {
        int reportId = 0;

        mockMvc.perform(delete("/post-reports/{reportId}", reportId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    // Test Case 5: Attempt to delete with invalid reportId (non-integer)
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void test5_deletePostReport_invalidReportId_nonInteger() throws Exception {
        String reportId = "abc";

        mockMvc.perform(delete("/post-reports/{reportId}", reportId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    // Test Case 6: Attempt to delete without reportId parameter
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void test6_deletePostReport_missingReportId() throws Exception {
        mockMvc.perform(delete("/post-reports/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
    }

    // Test Case 7: Attempt to delete a report without authentication
    @Test
    public void test7_deletePostReport_unauthorized() throws Exception {
        int reportId = 1;

        mockMvc.perform(delete("/post-reports/{reportId}", reportId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    // Test Case 8: Attempt to delete a report with insufficient permissions
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void test8_deletePostReport_forbidden() throws Exception {
        int reportId = 1;

        mockMvc.perform(delete("/post-reports/{reportId}", reportId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}
