package com.example.demo.common;

import java.time.Instant;

/**
 * 统一响应结构。
 *
 * @param code    业务状态码，0 表示成功
 * @param message 提示信息
 * @param data    业务数据
 * @param <T>     业务数据类型
 */
public record ApiResponse<T>(int code, String message, T data, Instant timestamp) {

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(0, "ok", data, Instant.now());
    }

    public static <T> ApiResponse<T> fail(int code, String message) {
        return new ApiResponse<>(code, message, null, Instant.now());
    }
}
