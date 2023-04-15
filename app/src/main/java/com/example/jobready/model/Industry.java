package com.example.jobready.model;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Industry {
    public long id;
    public String description;

    public Industry(long id, String description){
        this.id = id;
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }
    public long getPosition(){
        return this.id;
    }
}
