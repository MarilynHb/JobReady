package com.example.jobready;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private TextView tUsername, tHeadline, tLocation, tAbout;
    private String username, location, about;


    private String url = "http://10.0.2.2:80/JobReady/getUserDetails.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tUsername = findViewById(R.id.profileUsername);
        tHeadline = findViewById(R.id.profileHeadline);
        tLocation = findViewById(R.id.profileLocation);
        tAbout = findViewById(R.id.profileAbout);

        Intent intent = getIntent();
        Integer userId = intent.getIntExtra("userId", 0);

        getUserDetails(userId);
    }

    public void getUserDetails(Integer userId){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String username = jsonObject.getString("username");
                            String location = jsonObject.getString("location");
                            String about = jsonObject.getString("about");
                            String headline = jsonObject.getString("headline");


                            tUsername.setText(username);
                            tLocation.setText(location);
                            tHeadline.setText(headline);
                            tAbout.setText(about);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ProfileActivity.this, "JSON Exception", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(ProfileActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", userId.toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(ProfileActivity.this);
        requestQueue.add(stringRequest);
    }
}