package com.example.jobready.model;

public class Post {
    String username, content;

    public Post(String username, String content){
        this.username = username;
        this.content = content;
    }

    public String getUsername(){
        return username;
    }
    public String getContent(){
        return content;
    }
}
