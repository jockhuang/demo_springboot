package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class MyResponse {
    public static final int SUCCESS = 0;
    public static final int FAILED = 1;
    private int code;
    private String message;
    private Object data;

    public MyResponse(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static MyResponse ok() {
        return new MyResponse(SUCCESS, "SUCCESS", null);
    }

    public static MyResponse ok(Object data) {
        return new MyResponse(SUCCESS, "SUCCESS", data);
    }

    public static MyResponse failed(String message) {
        return new MyResponse(FAILED, message, null);
    }
}
