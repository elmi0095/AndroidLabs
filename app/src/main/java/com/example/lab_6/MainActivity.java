package com.example.lab_6;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ImageView catImageView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        catImageView = findViewById(R.id.catImageView);
        progressBar = findViewById(R.id.progressBar);

        new CatImages().execute();
    }

    private class CatImages extends AsyncTask<Void, Integer, Void> {

        private Bitmap currentCatImage;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                while (!isCancelled()) {
                    URL url = new URL("https://cataas.com/cat?json=true");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();

                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        InputStream stream = connection.getInputStream();
                        java.util.Scanner s = new java.util.Scanner(stream).useDelimiter("\\A");
                        String result = s.hasNext() ? s.next() : "";
                        JSONObject json = new JSONObject(result);

                        if (json.has("_id")) {
                            String catId = json.getString("_id");
                            Bitmap localCatImage = loadLocalImage(catId);
                            if (localCatImage != null) {
                                currentCatImage = localCatImage;
                            } else {
                                String imageUrl = json.getString("url");
                                String completeImageUrl = "https://cataas.com" + imageUrl;
                                URL catImageUrl = new URL(completeImageUrl);

                                HttpURLConnection imageConnection = (HttpURLConnection) catImageUrl.openConnection();
                                imageConnection.setDoInput(true);
                                imageConnection.connect();
                                InputStream input = imageConnection.getInputStream();
                                currentCatImage = BitmapFactory.decodeStream(input);

                                if (currentCatImage != null) {
                                    saveLocalImage(catId, currentCatImage);
                                }
                            }
                        }

                        for (int i = 0; i <= 100; i++) {
                            publishProgress(i);
                            Thread.sleep(30);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }



        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
            catImageView.setImageBitmap(currentCatImage);
        }

        private Bitmap loadLocalImage(String catId) {
            // Implement your logic to load a locally cached image by catId
            // Return null if the image does not exist locally
            return null;
        }

        private void saveLocalImage(String catId, Bitmap image) {
            // Implement your logic to save the image locally by catId
        }
    }
}
