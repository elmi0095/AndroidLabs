package com.example.lab_3;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        TextView textViewWelcome = findViewById(R.id.textViewWelcome);

        // Get the user's name from the Intent extra sent by MainActivity
        String userName = getIntent().getStringExtra("user_name");

        if (userName != null && !userName.isEmpty()) {
            // Display a personalized welcome message
            String welcomeMessage = getString(R.string.welcome, userName);
            textViewWelcome.setText(welcomeMessage);
        }
    }

    public void onDontCallMeThat(View view) {
        // Set the result to 0 (RESULT_CANCELED) and return to the previous activity (MainActivity)
        setResult(RESULT_CANCELED);
        finish();
    }

    public void onThankYou(View view) {
        // Set the result to 1 (RESULT_OK) and return to the previous activity (MainActivity)
        setResult(RESULT_OK);
        finish();
    }
}
