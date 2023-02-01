package com.cognizant.FEMS.model;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class CommentDTO {
    private String username;
    private String body;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public CommentDTO(String username, String body) {
        this.username = username;
        this.body = body;
    }
}
