package com.example.jobready;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Registration2Activity extends AppCompatActivity {
    private TextView accountType;
    private EditText etUsername, etEmail, etPassword;
    private Button btNext;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_2);

        etEmail = findViewById(R.id.email);
        etUsername = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);
        btNext = findViewById(R.id.nextButton);

        //Get Intent
        String accountType = getIntent().getStringExtra("accountType");

        btNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                String email = etEmail.getText().toString();
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                if (email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Registration2Activity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    //Send Intent
                    Intent intent = new Intent(Registration2Activity.this, Registration3Activity.class);
                    intent.putExtra("email", email);
                    intent.putExtra("username", username);
                    intent.putExtra("password", password);
                    intent.putExtra("accountType", accountType);
                    startActivity(intent);
                }
            }
        });
    }

    //region Have an account -> Login
    public void launchLoginActivity2(View view) {
        Intent intent = new Intent(Registration2Activity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    //endregion
}
