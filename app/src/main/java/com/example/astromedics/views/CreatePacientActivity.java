package com.example.astromedics.views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cloudinary.android.callback.ErrorInfo;
import com.example.astromedics.R;
import com.example.astromedics.helpers.ImageUploader;

public class CreatePacientActivity extends AppCompatActivity {

    ImageView profileImage;
    Button uploadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pacient);

        profileImage = findViewById(R.id.activity_create_profile_image);
        uploadButton = findViewById(R.id.activity_create_upload_image);

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ImageUploader.uploadAndGetURL(profileImage,
                                                  new ImageUploader.URLListener() {
                                                      @Override
                                                      public void onURLObtained(String url) {
                                                          Toast.makeText(getApplicationContext(),
                                                                         url,
                                                                         Toast.LENGTH_SHORT)
                                                               .show();
                                                      }

                                                      @Override
                                                      public void onError(ErrorInfo error) {
                                                          Toast.makeText(getApplicationContext(),
                                                                         error.getDescription(),
                                                                         Toast.LENGTH_SHORT)
                                                               .show();
                                                      }
                                                  });
                } catch (Exception ex) {

                }
            }
        });
    }
}
