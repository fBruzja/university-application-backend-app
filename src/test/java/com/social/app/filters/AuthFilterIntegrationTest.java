package com.social.app.filters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthFilterIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    private static final String jwtToken = "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2NzUwMjkwNzAsImV4cCI6MTY3NTAzNjI3MCwidXNlcklkIjo4LCJlbWFpbCI6ImluZXNAaW5lcy5jb20iLCJmaXJzdE5hbWUiOiJJbmVzIiwibGFzdE5hbWUiOiJNZWNhaiJ9.D4PLCyClV4lbHTprGlH4Jqg55W69Mbhb8ykLg7VCWqo";

    @Test
    public void givenValidAuthorizationHeader_whenFilterIsApplied_thenOkIsReturned() throws Exception {
        mockMvc.perform(get("/api/courses").header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    public void givenValidAuthorizationHeader_whenFilterIsApplied_thenNotFoundIsReturned() throws Exception {
        mockMvc.perform(get("/protected-endpoint").header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNotFound());
    }
}
