package com.gumtree.api.deserializerobjects;

public class PostResponse {
    Integer id;
    String title;
    String body;
    Integer userId;

    public String getTitle() {
        return title;
    }


    public String getBody() {
        return body;
    }


    public Integer getUserId() {
        return userId;
    }

    public Integer getId() {
        return id;
    }
}
