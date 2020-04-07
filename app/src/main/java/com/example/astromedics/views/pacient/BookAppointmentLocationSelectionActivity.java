package com.example.astromedics.views.pacient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentActivity;

import com.example.astromedics.R;
import com.example.astromedics.helpers.ApplicationDateFormat;
import com.example.astromedics.model.Localization;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;

public class BookAppointmentLocationSelectionActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FloatingActionButton button;
    private Localization localization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment_location_selection);

        button = findViewById(R.id.confirmLocation);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),
                                           TherapistFilterActivity.class);
                intent.putExtra(TherapistFilterActivity.LOCATION,
                                localization);
                startActivity(intent);
            }
        });


        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(),
                              getString(R.string.api_key),
                              new ApplicationDateFormat().getLocale());
        }

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID,
                                                          Place.Field.NAME,
                                                          Place.Field.LAT_LNG));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                localization = new Localization(place.getLatLng().latitude,
                                                place.getLatLng().longitude,
                                                place.getName());
                LatLng selectedPosition = place.getLatLng();
                mMap.addMarker(new MarkerOptions().position(selectedPosition)
                                                  .title(localization.getName()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(selectedPosition));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
                button.setEnabled(true);
                button.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(Status status) {

            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.googleMap);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(4.624335,
                                                                 -74.063644)));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10.0f));
    }
}
