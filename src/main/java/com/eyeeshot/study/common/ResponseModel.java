package com.eyeeshot.study.common;

import java.util.HashMap;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
public class ResponseModel {

    long startTime;
    private int status;
    private String message;
    private Object data;

    public ResponseModel() {
        reset();
    }

    public ResponseEntity<String> toResponse() {
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(toJson());
    }

    public String toJson() {

        long endTime = System.currentTimeMillis();

        HashMap<String, Object> result = new HashMap<>();

        result.put("status", status);
        result.put("message", message);

        if (data != null) {
            result.put("data", data);
        }

        result.put("function_time", (endTime - startTime));

        return Utils.convertObjectToJsonString(result);

    }

    public void reset() {
        this.status = 200;
        this.message = "Success";
        this.data = null;
        startTime = System.currentTimeMillis();
    }

}