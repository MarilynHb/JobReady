package com.example.jobready;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jobready.model.Industry;
import com.example.jobready.model.IndustryAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Registration3Activity extends AppCompatActivity {

    private EditText etHeadline, etLocation, etAbout;
    private Spinner spinnerIndustries;
    private String selectedIndustry;
    private Integer typeId;
    ArrayList<String> industriesList = new ArrayList<>();
    ArrayAdapter<String> industryArrayAdapter;
    RequestQueue requestQueue;
    Button btCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_3);

        etHeadline = findViewById(R.id.headline);
        etAbout = findViewById(R.id.about);
        etLocation = findViewById(R.id.location);
        spinnerIndustries = findViewById(R.id.spinnerIndustries);
        btCreate = findViewById(R.id.createButton);

        //region Get Industries and Display in Spinner
        requestQueue = Volley.newRequestQueue(this);
        String urlIndustries = "http://10.0.2.2:80/JobReady/fetchIndustries.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, urlIndustries,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try
                        {
                            JSONArray jsonArray = response.getJSONArray("industries");
                            for(int i = 0; i<jsonArray.length();i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String industry = jsonObject.optString("description");
                                industriesList.add(industry);
                                industryArrayAdapter = new ArrayAdapter<>(Registration3Activity.this,
                                        android.R.layout.simple_spinner_dropdown_item, industriesList);
                                industryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerIndustries.setAdapter(industryArrayAdapter);
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

        //region Get Intent from Registration 2
        Intent intent = getIntent();
        String accountType = intent.getStringExtra("accountType");
        String email = intent.getStringExtra("email");
        String username = intent.getStringExtra("username");
        String password = intent.getStringExtra("password");
        //endregion

        //region Get Selected Industry on Selection
        spinnerIndustries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedIndustry = (String) adapterView.getItemAtPosition(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //endregion

        //region Send POST Request to Add new User on Click
        btCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String headline = etHeadline.getText().toString().trim();
                final String about = etAbout.getText().toString().trim();
                final String location = etLocation.getText().toString().trim();
                final String industry = selectedIndustry;
                final String typeId;
                switch(accountType){
                    case "Admin": typeId = "1";
                    break;
                    case "Student": typeId = "2";
                    break;
                    case "Instructor": typeId = "3";
                    break;
                    case "Company": typeId = "4";
                    break;
                    default: typeId = "";
                    break;
                };

                String url = "http://10.0.2.2:80/JobReady/addUser.php";

                if (headline.isEmpty() || about.isEmpty() || location.isEmpty()) {
                    Toast.makeText(Registration3Activity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        int success = jsonObject.getInt("success");

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
                            params.put("email", email);
                            params.put("password", password);
                            params.put("username", username);
                            params.put("type", typeId);
                            params.put("headline", headline);
                            params.put("about", about);
                            params.put("location", location);
                            params.put("industry", industry);

                            return params;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(Registration3Activity.this);
                    requestQueue.add(stringRequest);
                    Toast.makeText(Registration3Activity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                    launchLoginActivity3(view);
                }
            }
        });
        //endregion
    }

    //region Have an account -> Login
    public void launchLoginActivity3(View view) {
        Intent intent = new Intent(Registration3Activity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    //endregion
}
