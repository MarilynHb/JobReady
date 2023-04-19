package com.example.jobready;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class EditProfileActivity extends AppCompatActivity {
    private EditText eUsername, eHeadline, eLocation, eAbout;
    private Button saveButton;
    private String url = "http://10.0.2.2:80/JobReady/editProfile.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);




        eUsername = findViewById(R.id.profileUsername);
            eHeadline = findViewById(R.id.profileHeadline);
            eLocation = findViewById(R.id.profileLocation);
            eAbout = findViewById(R.id.profileAbout);
            saveButton= findViewById(R.id.saveEdit);

            Intent intent = getIntent();
            String username = intent.getStringExtra("username");
            String location = intent.getStringExtra("location");
            String about = intent.getStringExtra("about");
            String headline = intent.getStringExtra("headline");


            eUsername.setText(username);
            eHeadline.setText(location);
            eLocation.setText(about);
            eAbout.setText(headline);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get the entered data from the EditText fields

                String username = eUsername.getText().toString().trim();
                String headline = eHeadline.getText().toString().trim();
                String location = eLocation.getText().toString().trim();
                String about = eAbout.getText().toString().trim();

                // create a new JSONObject to store the data
                JSONObject jsonObject = new JSONObject();

                try {
                    // put the values from the EditText fields into the JSON object
                    jsonObject.put("username", username);
                    jsonObject.put("headline", headline);
                    jsonObject.put("location", location);
                    jsonObject.put("about", about);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // create a new JsonObjectRequest to send the data to the API
                JsonObjectRequest request = new JsonObjectRequest(
                        Request.Method.POST, url, jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    int success = jsonObject.getInt("success");
                                    String message = jsonObject.getString("message");
                                    if (success == 1) {
                                        Toast.makeText(EditProfileActivity.this, message, Toast.LENGTH_SHORT).show();

                                    } else {
                                        Toast.makeText(EditProfileActivity.this, message, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(EditProfileActivity.this, "JSON Exception", Toast.LENGTH_SHORT).show();

                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // handle error response from the API
                                Log.e("API Error", error.toString());
                            }
                        }
                );

                // add the request to the Volley request queue
                Volley.newRequestQueue(getApplicationContext()).add(request);
            }
        });


        }
    }