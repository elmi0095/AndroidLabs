package com.example.myapplication;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private EditText editText;
    private Button pressMeButton;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.editText);
        pressMeButton = findViewById(R.id.pressMeButton);
        checkBox = findViewById(R.id.checkbox);

        pressMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newText = editText.getText().toString();
                textView.setText(newText);
                String toastMessage = getResources().getString(R.string.toast_message);
                Toast.makeText(MainActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String message = getResources().getString(R.string.checkbox_state, isChecked ? "on" : "off");
                Snackbar snackbar = Snackbar.make(buttonView, message, Snackbar.LENGTH_LONG);
                snackbar.setAction(getResources().getString(R.string.undo), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkBox.setChecked(!isChecked);
                    }
                });
                snackbar.show();
            }
        });
    }
}
