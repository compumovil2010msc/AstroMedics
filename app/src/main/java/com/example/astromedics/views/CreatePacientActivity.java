package com.example.astromedics.views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.cloudinary.android.callback.ErrorInfo;
import com.example.astromedics.R;
import com.example.astromedics.helpers.ImageUploader;
import com.example.astromedics.model.EvaluationQuestion;
import com.example.astromedics.model.MedicalConsultation;
import com.example.astromedics.model.MedicalRecord;
import com.example.astromedics.model.Pacient;
import com.example.astromedics.repository.Repository;
import com.example.astromedics.views.pacient.HomeUserActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreatePacientActivity extends AppCompatActivity {

    ConstraintLayout loader;
    ImageView profileImage;
    EditText emailEditText, password1EditText, password2EditText, fullNameEditText, identificationEditText, phoneNumberEditText, cellNumberEditText,
            addressEditText, heightEditText, weightEditText;
    Spinner bloodType1Spinner, bloodType2Spinner;
    Button uploadButton;
    FloatingActionButton registerButton;
    static final int IMAGE_PICKER_REQUEST = 7;
    static final int REQUEST_IMAGE_CAPTURE = 8;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                            Pattern.CASE_INSENSITIVE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pacient);
        inflateViews();
        addListeners();
    }

    public void inflateViews() {
        profileImage = findViewById(R.id.activity_create_profile_image);
        uploadButton = findViewById(R.id.activity_create_upload_image);
        emailEditText = findViewById(R.id.activity_create_pacient_email);
        password1EditText = findViewById(R.id.activity_create_pacient_password_1);
        password2EditText = findViewById(R.id.activity_create_pacient_password_2);
        fullNameEditText = findViewById(R.id.activity_create_pacient_full_name);
        identificationEditText = findViewById(R.id.activity_create_pacient_identification_number);
        phoneNumberEditText = findViewById(R.id.activity_create_pacient_phone_number);
        cellNumberEditText = findViewById(R.id.activity_create_pacient_cell_number);
        addressEditText = findViewById(R.id.activity_create_pacient_address);
        bloodType1Spinner = findViewById(R.id.activity_create_pacient_blood_type_1);
        bloodType2Spinner = findViewById(R.id.activity_create_pacient_blood_type_2);
        heightEditText = findViewById(R.id.activity_create_pacient_height);
        weightEditText = findViewById(R.id.activity_create_pacient_weight);
        registerButton = findViewById(R.id.activity_create_pacient_floating_action_button);
        loader = findViewById(R.id.activity_create_pacient_loader);
    }

    public void addListeners() {
        registerForContextMenu(uploadButton);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openContextMenu(uploadButton);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    showLoader();
                    if (hasText(emailEditText) &&
                            hasText(password1EditText) &&
                            hasText(password2EditText) &&
                            hasText(fullNameEditText) &&
                            hasText(identificationEditText) &&
                            hasText(phoneNumberEditText) &&
                            hasText(cellNumberEditText) &&
                            hasText(addressEditText) &&
                            hasText(emailEditText) &&
                            hasText(heightEditText) &&
                            hasText(weightEditText) &&
                            hasImage(profileImage) &&
                            Integer.parseInt(identificationEditText.getText()
                                                                   .toString()) != 0 &&
                            Long.parseLong(phoneNumberEditText.getText()
                                                              .toString()) != 0 &&
                            Long.parseLong(cellNumberEditText.getText()
                                                             .toString()) != 0 &&
                            Double.parseDouble(heightEditText.getText()
                                                             .toString()) != 0 &&
                            Double.parseDouble(weightEditText.getText()
                                                             .toString()) != 0) {
                        if (!password1EditText.getText()
                                              .toString()
                                              .equals(password2EditText.getText()
                                                                       .toString())) {
                            hideLoader();
                            Toast.makeText(getApplicationContext(),
                                           "Las contraseñas deben ser iguales",
                                           Toast.LENGTH_SHORT)
                                 .show();
                        } else if (!isValidEmail(emailEditText.getText()
                                                              .toString())) {
                            hideLoader();
                            Toast.makeText(getApplicationContext(),
                                           "Por favor ingrese un correo electronico valido",
                                           Toast.LENGTH_SHORT)
                                 .show();
                        } else {
                            createUser();
                        }
                    } else {
                        hideLoader();
                        Toast.makeText(getApplicationContext(),
                                       "Por favor rellene todos los campos",
                                       Toast.LENGTH_SHORT)
                             .show();
                    }
                } catch (Exception ex) {
                    hideLoader();
                    Toast.makeText(getApplicationContext(),
                                   ex.getMessage(),
                                   Toast.LENGTH_SHORT)
                         .show();
                }
            }
        });
    }

    private void createUser() {
        FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(emailEditText.getText()
                                                                 .toString(),
                                                    password1EditText.getText()
                                                                     .toString())
                    .addOnCompleteListener(
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        try {
                                            createPacient();
                                        } catch (Exception ex) {
                                            hideLoader();
                                            Toast.makeText(getApplicationContext(),
                                                           ex.getMessage(),
                                                           Toast.LENGTH_LONG)
                                                 .show();
                                        }
                                    } else {
                                        hideLoader();
                                        Toast.makeText(getApplicationContext(),
                                                       "El registro ha fallado",
                                                       Toast.LENGTH_LONG)
                                             .show();
                                    }
                                }
                            });
    }

    private void createPacient() {
        ImageUploader.uploadAndGetURL(profileImage,
                                      new ImageUploader.URLListener() {
                                          @Override
                                          public void onURLObtained(String url) {
                                              try {
                                                  url = url.replace("http",
                                                                    "https");
                                                  Pacient pacient = new Pacient(Integer.valueOf(identificationEditText.getText()
                                                                                                                      .toString()),
                                                                                fullNameEditText.getText()
                                                                                                .toString(),
                                                                                url,
                                                                                Long.valueOf(phoneNumberEditText.getText()
                                                                                                                .toString()),
                                                                                Long.valueOf(cellNumberEditText.getText()
                                                                                                               .toString()),
                                                                                addressEditText.getText()
                                                                                               .toString(),
                                                                                emailEditText.getText()
                                                                                             .toString(),
                                                                                new Date(),
                                                                                new MedicalRecord(bloodType1Spinner.getSelectedItem()
                                                                                                                   .toString() + bloodType2Spinner.getSelectedItem()
                                                                                                                                                  .toString(),
                                                                                                  Double.valueOf(weightEditText.getText()
                                                                                                                               .toString()),
                                                                                                  Double.valueOf(heightEditText.getText()
                                                                                                                               .toString()),
                                                                                                  new ArrayList<EvaluationQuestion>()),
                                                                                new ArrayList<MedicalConsultation>());

                                                  pacient = Repository.getInstance()
                                                                      .getPacientRepository()
                                                                      .createPacient(pacient);

                                                  if (pacient != null) {
                                                      Intent intent = new Intent(getApplicationContext(),
                                                                                 HomeUserActivity.class);
                                                      startActivity(intent);
                                                  }
                                              } catch (Exception ex) {
                                                  hideLoader();
                                                  Toast.makeText(getApplicationContext(),
                                                                 ex.getMessage(),
                                                                 Toast.LENGTH_LONG)
                                                       .show();
                                              }
                                          }

                                          @Override
                                          public void onError(ErrorInfo error) {
                                              hideLoader();
                                              Toast.makeText(getApplicationContext(),
                                                             error.getDescription(),
                                                             Toast.LENGTH_SHORT)
                                                   .show();
                                          }
                                      });
    }

    private void showLoader() {
        loader.setVisibility(View.VISIBLE);
        registerButton.setClickable(false);
    }

    private void hideLoader() {
        loader.setVisibility(View.GONE);
        registerButton.setClickable(true);
    }

    private boolean hasText(EditText editText) {
        return editText != null && !editText.getText()
                                            .equals("");
    }

    private boolean hasImage(ImageView imageView) {
        return imageView.getDrawable() != null;
    }

    public static boolean isValidEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu,
                                  v,
                                  menuInfo);
        getMenuInflater().inflate(R.menu.menu_select_image,
                                  menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_use_photo_gallery:
                openGallery();
                break;
            case R.id.menu_take_photo:
                takePicture();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent,
                                   REQUEST_IMAGE_CAPTURE);
        }
    }

    private void openGallery() {
        Intent pickImage = new Intent(Intent.ACTION_PICK);
        pickImage.setType("image/*");
        startActivityForResult(pickImage,
                               IMAGE_PICKER_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            profileImage.setImageBitmap(imageBitmap);
        } else if (requestCode == IMAGE_PICKER_REQUEST && resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                profileImage.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode,
                               resultCode,
                               data);
    }
}
