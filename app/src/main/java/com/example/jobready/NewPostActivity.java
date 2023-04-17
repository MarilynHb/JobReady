package com.example.jobready;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class NewPostActivity extends AppCompatActivity {
    private TextView tUsername;
    private EditText etPostContent;
    private String url = "http://10.0.2.2:80/JobReady/addPost.php";
    private Button btNewPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        tUsername = findViewById(R.id.profileUsername);
        etPostContent = findViewById(R.id.postContent);
        btNewPost = findViewById(R.id.newPostButton);

        Intent intentReceived = getIntent();
        Integer userId = intentReceived.getIntExtra("userId", 0);
        String username = intentReceived.getStringExtra("username");

        tUsername.setText(username);

        //region Navigation Bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.newPost);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()){
                    case R.id.newPost:
                        return true;
                    case R.id.home:
                        intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.putExtra("userId", userId);
                        intent.putExtra("username", username);
                        startActivity(intent);
                        return true;
                    case R.id.about:
                        intent = new Intent(getApplicationContext(), ProfileActivity.class);
                        intent.putExtra("userId", userId);
                        intent.putExtra("username", username);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });
        //endregion

        //region Send POST Request to Add new User on Click
        btNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String content = etPostContent.getText().toString().trim();
                final String id = Integer.toString(userId);
                if (content.isEmpty()) {
                    Toast.makeText(NewPostActivity.this, "Post is empty", Toast.LENGTH_SHORT).show();
                } else {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        int success = jsonObject.getInt("success");
                                        if (success == 1) {
                                            int userId = jsonObject.getInt("id");
                                            Toast.makeText(NewPostActivity.this, "Posted", Toast.LENGTH_SHORT).show();
                                            etPostContent.setText("");
                                            Intent intent = new Intent(NewPostActivity.this, HomeActivity.class);
                                            intent.putExtra("userId", userId);
                                            intent.putExtra("username", username);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(NewPostActivity.this, "Error Posting", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(NewPostActivity.this, "JSON Exception", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    error.printStackTrace();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("content", content);
                            params.put("userId", id);

                            return params;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(NewPostActivity.this);
                    requestQueue.add(stringRequest);
                    Toast.makeText(NewPostActivity.this, "Posted", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //endregion
    }
}
