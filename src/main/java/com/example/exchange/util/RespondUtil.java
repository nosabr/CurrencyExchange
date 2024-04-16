package com.example.exchange.util;

import com.example.exchange.dto.MessageDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class RespondUtil {
    private PrintWriter out;
    private final ObjectMapper objectMapper = new ObjectMapper();
    public void showError(HttpServletResponse resp, String message){
        try {
            resp.setContentType("application/json; charset=UTF-8");
            out = resp.getWriter();
            out.println(objectMapper.writeValueAsString(new MessageDTO(message)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> void showJSON(HttpServletResponse resp, T obj){
        try {
            resp.setContentType("application/json; charset=UTF-8");
            out = resp.getWriter();
            String out1 = objectMapper.writeValueAsString(obj);
            out.println(out1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
