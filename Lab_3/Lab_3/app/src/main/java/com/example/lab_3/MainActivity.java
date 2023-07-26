package com.example.lab_3;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int NAME_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonNext = findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextName = findViewById(R.id.editTextName);
                String userName = editTextName.getText().toString().trim();

                Intent intent = new Intent(MainActivity.this, NameActivity.class);
                intent.putExtra("user_name", userName);
                startActivityForResult(intent, NAME_ACTIVITY_REQUEST_CODE);
            }
        });

        // Load user's name from SharedPreferences and put it in the EditText
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String userName = preferences.getString("user_name", "");
        EditText editTextName = findViewById(R.id.editTextName);
        editTextName.setText(userName);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Save the current value inside the EditText to SharedPreferences
        EditText editTextName = findViewById(R.id.editTextName);
        String userName = editTextName.getText().toString().trim();

        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user_name", userName);
        editor.apply();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NAME_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_CANCELED) {
                // The user wants to change their name (result code is 0)
                // You can perform any actions needed when the user cancels
            } else if (resultCode == RESULT_OK) {
                // The user is happy (result code is 1)
                finish(); // Close the app
            }
        }
    }
}
