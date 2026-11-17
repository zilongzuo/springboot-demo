package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 回显接口的请求体。
 *
 * @param message 需要回显的内容
 * @param upper   是否将内容转为大写，默认 false
 */
public record EchoRequest(
        @NotBlank(message = "message 不能为空")
        @Size(max = 500, message = "message 长度不能超过 500 个字符")
        String message,

        Boolean upper
) {

    public boolean shouldUpperCase() {
        return Boolean.TRUE.equals(upper);
    }
}
