package com.example.demo.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /api/hello 返回问候语")
    void hello() throws Exception {
        mockMvc.perform(get("/api/hello").param("name", "WorkBuddy"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.greeting").value("Hello, WorkBuddy!"));
    }

    @Test
    @DisplayName("GET /api/time 返回服务器时间")
    void time() throws Exception {
        mockMvc.perform(get("/api/time"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.currentTime").isNotEmpty())
                .andExpect(jsonPath("$.data.zoneId").isNotEmpty());
    }

    @Test
    @DisplayName("POST /api/echo 回显并支持转大写")
    void echo() throws Exception {
        mockMvc.perform(post("/api/echo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\":\"hello\",\"upper\":true}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.echo").value("HELLO"))
                .andExpect(jsonPath("$.data.length").value(5));
    }

    @Test
    @DisplayName("POST /api/echo 空内容返回 400")
    void echoValidation() throws Exception {
        mockMvc.perform(post("/api/echo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400));
    }
}
