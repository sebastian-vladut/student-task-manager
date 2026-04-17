package com.sebastian_vladut.student_task_manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sebastian_vladut.student_task_manager.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    // Accessing a protected endpoint without authentication → expect 403
    @Test
    void accessProtectedEndpoint() throws Exception {
        mockMvc.perform(get("/tasks/my"))
                .andExpect(status().isForbidden()); // check for the 403 status code
    }

    // User (ROLE_USER) trying to access admin route → expect 403
    @Test
    @WithMockUser(username = "student", roles = "USER")
    void accessProtectedEndpointWithRole() throws Exception {
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isForbidden());
    }

    // User registration endpoint → expect 200 OK / 201 Created
    @Test
    void userRegistration() throws Exception {
        User user = new User();
        user.setUsername("student");
        user.setPassword("password");
        user.setRole("ROLE_USER");

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
                )
                .andExpect(status().isCreated());
    }
}
