package com.example.jobready.model;

public class Post {
    String username, content, headline;

    public Post(String username, String content, String headline){
        this.username = username;
        this.content = content;
        this.headline = headline;
    }

    public String getUsername(){
        return username;
    }
    public String getContent(){
        return content;
    }
    public String getHeadline(){
        return headline;
    }

}
