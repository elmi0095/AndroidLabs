package com.example.lab_7;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> characterList;
    private ListView listView;
    private boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        characterList = new ArrayList<>();
        listView = findViewById(R.id.listView);
        isTablet = findViewById(R.id.frameLayout) != null; // Check if FrameLayout exists (tablet)

        new FetchCharacterDataTask().execute();

        // Set item click listener for the ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedCharacter = characterList.get(position);

                if (isTablet) {
                    // On tablet, replace FrameLayout with DetailsFragment directly
                    // Modify this line to pass the correct content to the DetailsFragment
                    // On tablet, replace FrameLayout with DetailsFragment directly
                    DetailsFragment detailsFragment = DetailsFragment.newInstance(selectedCharacter, null);

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frameLayout, detailsFragment)
                            .commit();
                } else {
                    // On phone, open EmptyActivity and pass data as extras
                    Intent intent = new Intent(MainActivity.this, EmptyActivity.class);
                    intent.putExtra("selectedCharacter", selectedCharacter);
                    // Add other necessary extras
                    startActivity(intent);
                }
            }
        });

    }

    private class FetchCharacterDataTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL("https://swapi.dev/api/people/?format=json");
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();

                Scanner scanner = new Scanner(in);
                scanner.useDelimiter("\\A");

                boolean hasInput = scanner.hasNext();
                if (hasInput) {
                    return scanner.next();
                } else {
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray characters = jsonObject.getJSONArray("results");
                    for (int i = 0; i < characters.length(); i++) {
                        JSONObject character = characters.getJSONObject(i);
                        String name = character.getString("name");
                        characterList.add(name);
                    }

                    // Create and set the adapter for the ListView
                    CharacterListAdapter adapter = new CharacterListAdapter(MainActivity.this, characterList);
                    listView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
