package com.example.lab_7;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class EmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        String selectedCharacter = getIntent().getStringExtra("selectedCharacter");

        DetailsFragment detailsFragment = DetailsFragment.newInstance(selectedCharacter, null);

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.frameLayoutContainer, detailsFragment) // Replace with your FrameLayout ID
                .commit();
    }
}
