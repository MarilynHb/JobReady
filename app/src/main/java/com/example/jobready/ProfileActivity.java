package com.example.jobready;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private TextView tUsername, tHeadline, tLocation, tAbout;

    private Button editProfileButton;


    private String url = "http://10.0.2.2:80/JobReady/getUserDetails.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tUsername = findViewById(R.id.profileUsername);
        tHeadline = findViewById(R.id.profileHeadline);
        tLocation = findViewById(R.id.profileLocation);
        tAbout = findViewById(R.id.profileAbout);
        editProfileButton = findViewById(R.id.editProfileButton);




        Intent intent = getIntent();
        Integer userId = intent.getIntExtra("userId", 0);
        Integer profileId = intent.getIntExtra("profileId", 0);
        String currentUsername = intent.getStringExtra("username");
        if(profileId != 0){
            getUserDetails(profileId);
        }
        else {
            getUserDetails(userId);
        }
        //region Navigation Bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.about);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()){
                    case R.id.about:
                        return true;
                    case R.id.home:
                        intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.putExtra("userId", userId);
                        intent.putExtra("username", currentUsername);
                        startActivity(intent);
                        return true;
                    case R.id.newPost:
                        intent = new Intent(getApplicationContext(), NewPostActivity.class);
                        intent.putExtra("userId", userId);
                        intent.putExtra("username", currentUsername);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });
        //endregion
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


                            editProfileButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                                    intent.putExtra("Username", username);
                                    intent.putExtra("Location", location);
                                    intent.putExtra("About", about);
                                    intent.putExtra("Headline", headline);
                                    startActivity(intent);
                                }
                            });

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
