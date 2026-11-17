package com.example.demo.controller;

import com.example.demo.common.ApiResponse;
import com.example.demo.dto.EchoRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 三个示例接口：
 * <ul>
 *     <li>GET  /api/hello  问候</li>
 *     <li>GET  /api/time   服务器时间</li>
 *     <li>POST /api/echo   内容回显</li>
 * </ul>
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final String applicationName;
    private final LocalDateTime startedAt = LocalDateTime.now();

    public ApiController(@Value("${spring.application.name:springboot-demo}") String applicationName) {
        this.applicationName = applicationName;
    }

    /**
     * 接口一：问候。不传 name 时默认为 World。
     */
    @GetMapping("/hello")
    public ApiResponse<Map<String, Object>> hello(
            @RequestParam(name = "name", defaultValue = "World") String name) {
        String trimmed = name.trim();
        String greeting = trimmed.isEmpty()
                ? "Hello, World!"
                : "Hello, " + trimmed + "!";

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("greeting", greeting);
        data.put("service", applicationName);
        return ApiResponse.ok(data);
    }

    /**
     * 接口二：返回服务器当前时间与时区信息。
     */
    @GetMapping("/time")
    public ApiResponse<Map<String, Object>> time() {
        ZonedDateTime now = ZonedDateTime.now();
        long uptimeSeconds = Duration.between(startedAt, LocalDateTime.now()).toSeconds();

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("currentTime", now.format(TIME_FORMATTER));
        data.put("iso8601", now.toString());
        data.put("zoneId", now.getZone().getId());
        data.put("epochMilli", now.toInstant().toEpochMilli());
        data.put("uptimeSeconds", uptimeSeconds);
        return ApiResponse.ok(data);
    }

    /**
     * 接口三：回显请求内容，可选转大写。
     */
    @PostMapping("/echo")
    public ApiResponse<Map<String, Object>> echo(@Valid @RequestBody EchoRequest request) {
        String origin = request.message();
        String result = request.shouldUpperCase() ? origin.toUpperCase(Locale.ROOT) : origin;

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("echo", result);
        data.put("length", result.length());
        data.put("upperCase", request.shouldUpperCase());
        return ApiResponse.ok(data);
    }
}
