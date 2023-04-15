package com.example.jobready;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Registration1Activity extends AppCompatActivity {

    private RadioGroup accountTypeRadioGroup;
    private Button nextButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_1);

        accountTypeRadioGroup = findViewById(R.id.accountTypeRadioGroup);
        nextButton = findViewById(R.id.nextButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radioButtonId = accountTypeRadioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(radioButtonId);
                String radioText = radioButton.getText().toString();

                if (radioText.isEmpty()) {
                    Toast.makeText(Registration1Activity.this, "Please select your account type", Toast.LENGTH_SHORT).show();
                } else {

                //Send Intent
                Intent intent = new Intent(Registration1Activity.this, Registration2Activity.class);
                intent.putExtra("accountType", radioText);
                startActivity(intent);
                }
            }
            });
    }

    //region Have an account -> Login
    public void launchLoginActivity1(View view) {
        Intent intent = new Intent(Registration1Activity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    //endregion
}
