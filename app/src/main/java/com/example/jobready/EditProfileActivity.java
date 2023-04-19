package com.example.jobready;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {
    private TextView tUsername;
    private EditText eEmail, eHeadline, eLocation, eAbout;
    private Spinner spinnerIndustries;
    private String selectedIndustry;
    ArrayList<String> industriesList = new ArrayList<>();
    ArrayAdapter<String> industryArrayAdapter;
    RequestQueue requestQueue;
    private Button btSave, btCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        tUsername = findViewById(R.id.profileUsername);
        eHeadline = findViewById(R.id.profileHeadline);
        eLocation = findViewById(R.id.profileLocation);
        eAbout = findViewById(R.id.profileAbout);
        btSave = findViewById(R.id.saveEdit);
        btCancel= findViewById(R.id.cancelEdit);

        Intent intent = getIntent();
        Integer userId = intent.getIntExtra("userId", 0);
        String username = intent.getStringExtra("username");
        //String email = intent.getStringExtra("email");
        String headline = intent.getStringExtra("headline");
        String location = intent.getStringExtra("location");
        //String industry = intent.getStringExtra("industry");
        String about = intent.getStringExtra("about");


        tUsername.setText(username);
        //eEmail.setText(email);
        eHeadline.setText(headline);
        eLocation.setText(location);
        eAbout.setText(about);


        //region Get Industries and Display in Spinner
        /*requestQueue = Volley.newRequestQueue(this);
        String urlIndustries = "http://10.0.2.2:80/JobReady/fetchIndustries.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, urlIndustries, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("industries");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String industry = jsonObject.optString("description");
                                industriesList.add(industry);
                                industryArrayAdapter = new ArrayAdapter<>(EditProfileActivity.this,
                                        android.R.layout.simple_spinner_dropdown_item, industriesList);
                                industryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerIndustries.setAdapter(industryArrayAdapter);
                            }
                        } catch (JSONException ex) {
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
        requestQueue.add(jsonObjectRequest);*/
        //endregion

        //region Get Selected Industry on Selection
        /*spinnerIndustries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedIndustry = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/
        //endregion


        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                intent.putExtra("userId", userId);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //final String email = eEmail.getText().toString().trim();
                final String headline = eHeadline.getText().toString().trim();
                final String location = eLocation.getText().toString().trim();
                final String about = eAbout.getText().toString().trim();
                ///final String industry = selectedIndustry;

                String url = "http://10.0.2.2:80/JobReady/editProfile.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);

                                    if(jsonObject.getInt("success") == 1) {
                                        Toast.makeText(EditProfileActivity.this, "Account Saved Successfully", Toast.LENGTH_SHORT).show();
                                        launchProfileActivity(view, userId, username);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
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
                        //params.put("email", email);
                        params.put("userId", userId.toString());
                        params.put("headline", headline);
                        params.put("about", about);
                        params.put("location", location);
                        //params.put("industry", industry);
                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(EditProfileActivity.this);
                requestQueue.add(stringRequest);

            }
        });
        //endregion
    }

    //region Have an account -> Login
    public void launchProfileActivity(View view, Integer userId, String username) {
        Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
        intent.putExtra("userId", userId);
        intent.putExtra("username", username);
        startActivity(intent);
        finish();
    }
    //endregion
}