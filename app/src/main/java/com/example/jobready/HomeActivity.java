package com.example.jobready;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jobready.adapter.PostAdapter;
import com.example.jobready.model.Post;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    private EditText etSearch;
    private ListView listPostView;
    private List<Post> postList;
    private Integer currentUserId;
    private String currentUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        listPostView = findViewById(R.id.postList);
        postList = new ArrayList<>();

        Intent intentReceived = getIntent();
        currentUserId = intentReceived.getIntExtra("userId", 0);
        currentUsername = intentReceived.getStringExtra("username");

        //region Navigation Bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()){
                    case R.id.home:
                        return true;
                    case R.id.about:
                        intent = new Intent(HomeActivity.this, ProfileActivity.class);
                        intent.putExtra("userId", currentUserId);
                        intent.putExtra("username", currentUsername);
                        startActivity(intent);
                        return true;
                    case R.id.newPost:
                        intent = new Intent(HomeActivity.this, NewPostActivity.class);
                        intent.putExtra("userId", currentUserId);
                        intent.putExtra("username", currentUsername);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });
        //endregion

        //region Search Bar onEnter()
        etSearch = (EditText) findViewById(R.id.search);

        etSearch.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    RequestQueue searchRequestQueue = Volley.newRequestQueue(HomeActivity.this);
                    final String searchUsername = etSearch.getText().toString().trim();
                    String searchUrl = "http://10.0.2.2:80/JobReady/searchUser.php";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, searchUrl,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        int success = jsonObject.getInt("success");
                                        if (success == 1) {
                                            int userId = jsonObject.getInt("id");
                                            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                                            intent.putExtra("profileId", userId);
                                            intent.putExtra("userId", currentUserId);
                                            intent.putExtra("username", currentUsername);
                                            Toast.makeText(HomeActivity.this, "Account Found", Toast.LENGTH_SHORT).show();
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(HomeActivity.this, "Account Not Found", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(HomeActivity.this, "JSON Exception", Toast.LENGTH_SHORT).show();

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
                            params.put("username", searchUsername);
                            return params;
                        }
                    };

                    searchRequestQueue.add(stringRequest);
                    return true;

                    //Toast.makeText(HomeActivity.this, etSearch.getText(), Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        //endregion

        //region Get All Posts
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String urlPosts = "http://10.0.2.2:80/JobReady/fetchPosts.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, urlPosts,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try
                        {
                            JSONArray jsonArray = response.getJSONArray("posts");
                            for(int i = 0; i<jsonArray.length();i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String username = jsonObject.optString("username");
                                String content = jsonObject.optString("content");
                                String headline = jsonObject.optString("headline");

                                Post post = new Post(username, content,headline);
                                postList.add(post);
                                PostAdapter postAdapter = new PostAdapter(postList, HomeActivity.this);
                                listPostView.setAdapter(postAdapter);
                            }
                        }catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
        //endregion

        //region List Post

        //endregion

    }


}
