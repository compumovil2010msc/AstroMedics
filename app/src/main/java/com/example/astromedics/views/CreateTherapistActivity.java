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
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.cloudinary.android.callback.ErrorInfo;
import com.example.astromedics.R;
import com.example.astromedics.helpers.ApplicationDateFormat;
import com.example.astromedics.helpers.ImageUploader;
import com.example.astromedics.model.EducationalFormation;
import com.example.astromedics.model.Therapist;
import com.example.astromedics.repository.Repository;
import com.example.astromedics.views.common.fragments.EducationalFormationCreation;
import com.example.astromedics.views.therapist.HomeTherapist;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateTherapistActivity extends AppCompatActivity {

    ConstraintLayout loader;
    private int index = 1;
    Map<Integer, EducationalFormationCreation> educationalFormationFragments;
    LinearLayout cardContainer;
    ScrollView scrollView;
    CardView calendarCardView;
    CalendarView calendarView;
    FragmentManager fragmentManager;
    EducationalFormationCreation.OnCalendarSelectionListener currentOnCalendarSelectionListener;
    FloatingActionButton registerButton;
    Button uploadButton;
    EditText emailEditText, password1EditText, password2EditText, fullNameEditText, identificationEditText, phoneNumberEditText, cellNumberEditText,
            addressEditText, priceEditText;
    ImageView profileImage;
    MaterialCheckBox emphasisSpeechTerapy, emphasisPhysiotherapy, emphasisPsychology;
    static final int IMAGE_PICKER_REQUEST = 7;
    static final int REQUEST_IMAGE_CAPTURE = 8;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                            Pattern.CASE_INSENSITIVE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_therapist);
        educationalFormationFragments = new HashMap<>();
        fragmentManager = getSupportFragmentManager();
        inflateViews();
        addEducationalFormation();
        addListeners();
    }

    private void inflateViews() {
        profileImage = findViewById(R.id.activity_create_profile_image);
        uploadButton = findViewById(R.id.activity_create_upload_image);
        cardContainer = findViewById(R.id.activity_create_therapist_container);
        scrollView = findViewById(R.id.activity_create_therapist_floating_scroll_view);
        calendarCardView = findViewById(R.id.activity_create_therapist_calendar_card_view);
        calendarView = findViewById(R.id.activity_create_therapist_calendar_view);
        registerButton = findViewById(R.id.activity_create_therapist_floating_action_button);
        emphasisSpeechTerapy = findViewById(R.id.activity_create_therapist_emphasis_speech_therapy);
        emphasisPhysiotherapy = findViewById(R.id.activity_create_therapist_emphasis_physiotherapy);
        emphasisPsychology = findViewById(R.id.activity_create_therapist_emphasis_psychology);

        emailEditText = findViewById(R.id.activity_create_therapist_email);
        password1EditText = findViewById(R.id.activity_create_therapist_password_1);
        password2EditText = findViewById(R.id.activity_create_therapist_password_2);
        fullNameEditText = findViewById(R.id.activity_create_therapist_full_name);
        identificationEditText = findViewById(R.id.activity_create_therapist_identification_number);
        phoneNumberEditText = findViewById(R.id.activity_create_therapist_phone_number);
        cellNumberEditText = findViewById(R.id.activity_create_therapist_cell_number);
        addressEditText = findViewById(R.id.activity_create_therapist_address);
        loader = findViewById(R.id.activity_create_therapist_loader);
        priceEditText = findViewById(R.id.activity_create_therapist_price);
    }

    private void addListeners() {
        registerForContextMenu(uploadButton);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openContextMenu(uploadButton);
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                Date pickedDate = new ApplicationDateFormat().createDate(year,
                                                                         month + 1,
                                                                         day);
                currentOnCalendarSelectionListener.onCalendarSelection(pickedDate);
                calendarCardView.setVisibility(View.INVISIBLE);
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
                            hasText(priceEditText) &&
                            hasImage(profileImage) &&
                            getEducationalFormation() != null &&
                            getEmphasis() != null &&
                            Integer.parseInt(identificationEditText.getText()
                                                                   .toString()) != 0 &&
                            Integer.parseInt(priceEditText.getText()
                                                          .toString()) != 0 &&
                            Long.parseLong(phoneNumberEditText.getText()
                                                              .toString()) != 0 &&
                            Long.parseLong(cellNumberEditText.getText()
                                                             .toString()) != 0) {
                        if (!password1EditText.getText()
                                              .toString()
                                              .equals(password2EditText.getText()
                                                                       .toString())) {
                            hideLoader();
                            Toast.makeText(getApplicationContext(),
                                           getString(R.string.activity_create_pacient_fill_invalid_pass),
                                           Toast.LENGTH_SHORT)
                                 .show();
                        } else if (!isValidEmail(emailEditText.getText()
                                                              .toString())) {
                            hideLoader();
                            Toast.makeText(getApplicationContext(),
                                           getString(R.string.activity_create_pacient_fill_invalid_email),
                                           Toast.LENGTH_SHORT)
                                 .show();
                        } else {
                            createUser();
                        }
                    } else {
                        hideLoader();
                        Toast.makeText(getApplicationContext(),
                                       getString(R.string.activity_create_pacient_fill_all_data),
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
                                            createTherapist();
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
                                                       getString(R.string.activity_create_pacient_info_register_failed),
                                                       Toast.LENGTH_LONG)
                                             .show();
                                    }
                                }
                            });
    }

    private void createTherapist() {
        ImageUploader.uploadAndGetURL(profileImage,
                                      new ImageUploader.URLListener() {
                                          @Override
                                          public void onURLObtained(String url) {
                                              try {
                                                  url = url.replace("http",
                                                                    "https");
                                                  Therapist therapist = new Therapist(Integer.valueOf(identificationEditText.getText()
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
                                                                                      Integer.valueOf(priceEditText.getText()
                                                                                                                   .toString()),
                                                                                      getEmphasis(),
                                                                                      getEducationalFormation(),
                                                                                      new ArrayList<>());

                                                  therapist = Repository.getInstance()
                                                                        .getTherapistRepository()
                                                                        .createTherapist(therapist);

                                                  if (therapist != null) {
                                                      Intent intent = new Intent(getApplicationContext(),
                                                                                 HomeTherapist.class);
                                                      startActivity(intent);
                                                  }
                                                  hideLoader();
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

    private void addEducationalFormation() {
        EducationalFormationCreation newEducationalFormation = new EducationalFormationCreation(index,
                                                                                                new EducationalFormationCreation.IEducationalFormationButtonListener() {

                                                                                                    @Override
                                                                                                    public void onNewButtonPressed(int i) {
                                                                                                        addEducationalFormation();
                                                                                                        scrollView.postDelayed(new Runnable() {
                                                                                                                                   @Override
                                                                                                                                   public void run() {
                                                                                                                                       scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                                                                                                                                   }
                                                                                                                               },
                                                                                                                               300);
                                                                                                    }

                                                                                                    @Override
                                                                                                    public void onCloseButtonPressed(int i) {
                                                                                                        removeEducationalFormation(i);
                                                                                                    }

                                                                                                    @Override
                                                                                                    public void onInitialCalendarSolitude(
                                                                                                            EducationalFormationCreation.OnCalendarSelectionListener onCalendarSelectionListener) {
                                                                                                        calendarView.setMinDate(0);
                                                                                                        calendarView.setMaxDate(Long.MAX_VALUE);
                                                                                                        calendarView.setMaxDate(new Date().getTime());
                                                                                                        calendarCardView.setVisibility(View.VISIBLE);
                                                                                                        currentOnCalendarSelectionListener = onCalendarSelectionListener;
                                                                                                    }

                                                                                                    @Override
                                                                                                    public void onFinalCalendarSolitude(
                                                                                                            Date initialDate,
                                                                                                            EducationalFormationCreation.OnCalendarSelectionListener onCalendarSelectionListener) {
                                                                                                        calendarView.setMinDate(0);
                                                                                                        calendarView.setMaxDate(Long.MAX_VALUE);
                                                                                                        calendarView.setMaxDate(new Date().getTime());
                                                                                                        calendarView.setMinDate(initialDate.getTime());
                                                                                                        calendarCardView.setVisibility(View.VISIBLE);
                                                                                                        currentOnCalendarSelectionListener = onCalendarSelectionListener;
                                                                                                    }
                                                                                                });

        educationalFormationFragments.put(new Integer(index),
                                          newEducationalFormation);
        fragmentManager.beginTransaction()
                       .add(cardContainer.getId(),
                            educationalFormationFragments.get(new Integer(index)),
                            String.valueOf(index))
                       .commit();
        index++;
    }

    private void removeEducationalFormation(int i) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager
                .findFragmentByTag(String.valueOf(i));
        if (fragment != null) {
            fragmentManager
                    .beginTransaction()
                    .remove(fragment)
                    .commit();
        }
        educationalFormationFragments.remove(new Integer(i));
    }

    private List<EducationalFormation> getEducationalFormation() throws Exception {
        List<EducationalFormation> returnable = new ArrayList<>();

        for (EducationalFormationCreation educationalFormationCreation : educationalFormationFragments.values()) {
            returnable.add(educationalFormationCreation.getEducationalFormation());
        }

        return returnable;
    }

    private List<Therapist.Emphasis> getEmphasis() throws Exception {
        List<Therapist.Emphasis> returnable = new ArrayList<>();

        if (emphasisSpeechTerapy.isChecked()) {
            returnable.add(Therapist.Emphasis.speech_therapy);
        }
        if (emphasisPhysiotherapy.isChecked()) {
            returnable.add(Therapist.Emphasis.physiotherapy);
        }
        if (emphasisPsychology.isChecked()) {
            returnable.add(Therapist.Emphasis.psychology);
        }

        if (returnable.size() == 0) {
            throw new Exception("Por favor seleccione un enfasis");
        }

        return returnable;
    }

    private void showLoader() {
        loader.setVisibility(View.VISIBLE);
        registerButton.setClickable(false);
    }

    private void hideLoader() {
        loader.setVisibility(View.GONE);
        registerButton.setClickable(true);
    }

    private boolean hasText(EditText editText) throws Exception {
        if (editText.getText() == null || editText.getText()
                                                  .toString()
                                                  .equals("")) {
            throw new Exception(getString(R.string.activity_create_pacient_fill_all_data));
        }
        return editText != null && !editText.getText()
                                            .toString()
                                            .equals("");
    }

    private boolean hasImage(ImageView imageView) throws Exception {
        if (imageView.getDrawable() == null) {
            throw new Exception(getString(R.string.activity_create_pacient_image));
        }
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
