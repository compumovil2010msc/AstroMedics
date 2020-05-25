package com.example.astromedics.views.common;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.example.astromedics.R;
import com.example.astromedics.helpers.ApplicationDateFormat;
import com.example.astromedics.model.dto.CurrentLocation;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.libraries.places.api.Places;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.EncodedPolyline;

import java.util.ArrayList;
import java.util.List;

public class LocationDisplayActivity extends FragmentActivity implements OnMapReadyCallback {
    public static String KEY = "key";
    String PATH_NOTIFICATIONS2 = "LocationNotifier2/";
    public static String key;

    private String targetName;
    private LatLng currentLocation, selectedLocation;
    private GoogleMap googleMap;

    private TextView locationNameTextView;
    private boolean indicationsEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_display);
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(),
                              getString(R.string.api_key),
                              new ApplicationDateFormat().getLocale());
        }
        inflateViews();
        addListeners();
    }

    private void inflateViews() {
        locationNameTextView = findViewById(R.id.book_appointment_location_display_name);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.googleMap);
        mapFragment.getMapAsync(this);
    }

    private void addListeners() {
        FirebaseDatabase database;
        DatabaseReference myRef;
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("LocationNotifier2/" + key);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CurrentLocation gettedcurrentLocation = dataSnapshot.getValue(CurrentLocation.class);
                if(gettedcurrentLocation != null){
                    locationNameTextView.setText(gettedcurrentLocation.getTargetLocation()
                                                                      .getName());

                    selectedLocation = new LatLng(gettedcurrentLocation.getTargetLocation()
                                                                       .getLatitude(),
                                                  gettedcurrentLocation.getTargetLocation()
                                                                       .getLongitude());
                    currentLocation = new LatLng(gettedcurrentLocation.getCurrentLocation()
                                                                      .getLatitude(),
                                                 gettedcurrentLocation.getCurrentLocation()
                                                                      .getLongitude());
                    refreshMap();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void refreshMap() {
        if (googleMap != null) {
            googleMap.clear();

            if (selectedLocation != null) {
                googleMap.addMarker(new MarkerOptions().position(selectedLocation)
                                                       .title(targetName));
            }
            if (currentLocation != null) {
                googleMap.addMarker(new MarkerOptions().position(currentLocation)
                                                       .title(getString(R.string.maps_current_location)));
            }

            if (currentLocation != null && selectedLocation != null) {
                refreshMapZoom(drawRoute());
            }
        }
    }

    private List<LatLng> drawRoute() {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(getString(R.string.api_key))
                .build();
        List<LatLng> path = new ArrayList();
        DirectionsApiRequest req = DirectionsApi.getDirections(context,
                                                               currentLocation.latitude + "," + currentLocation.longitude,
                                                               selectedLocation.latitude + "," + selectedLocation.longitude);
        try {
            DirectionsResult res = req.await();

            if (res.routes != null && res.routes.length > 0) {
                DirectionsRoute route = res.routes[0];

                if (route.legs != null) {
                    for (int i = 0; i < route.legs.length; i++) {
                        DirectionsLeg leg = route.legs[i];
                        if (leg.steps != null) {
                            for (int j = 0; j < leg.steps.length; j++) {
                                DirectionsStep step = leg.steps[j];
                                if (step.steps != null && step.steps.length > 0) {
                                    for (int k = 0; k < step.steps.length; k++) {
                                        DirectionsStep step1 = step.steps[k];
                                        EncodedPolyline points1 = step1.polyline;
                                        if (points1 != null) {
                                            List<com.google.maps.model.LatLng> coords1 = points1.decodePath();
                                            for (com.google.maps.model.LatLng coord1 : coords1) {
                                                path.add(new LatLng(coord1.lat,
                                                                    coord1.lng));
                                            }
                                        }
                                    }
                                } else {
                                    EncodedPolyline points = step.polyline;
                                    if (points != null) {
                                        //Decode polyline and add points to list of route coordinates
                                        List<com.google.maps.model.LatLng> coords = points.decodePath();
                                        for (com.google.maps.model.LatLng coord : coords) {
                                            path.add(new LatLng(coord.lat,
                                                                coord.lng));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {

        }

        if (path.size() > 0) {
            PolylineOptions opts = new PolylineOptions().addAll(path)
                                                        .color(Color.BLUE)
                                                        .width(5);
            googleMap.addPolyline(opts);
        }

        float totalDistance = calculateTotalDistance(path);
        return path;
    }

    private void refreshMapZoom(List<LatLng> pathPoints) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        for (int i = 0; i < pathPoints.size(); i++) {
            builder.include(pathPoints.get(i));
        }

        LatLngBounds bounds = builder.build();

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.25);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds,
                                                                        width,
                                                                        height,
                                                                        padding);

        googleMap.animateCamera(cameraUpdate);
    }

    public float calculateDistance(LatLng origin, LatLng destiny) {
        double earthRadius = 3958.75;
        double latDiff = Math.toRadians(destiny.latitude - origin.latitude);
        double lngDiff = Math.toRadians(destiny.longitude - origin.longitude);
        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2) +
                Math.cos(Math.toRadians(origin.latitude)) * Math.cos(Math.toRadians(destiny.latitude)) *
                        Math.sin(lngDiff / 2) * Math.sin(lngDiff / 2);
        double c = 2 * Math.atan2(Math.sqrt(a),
                                  Math.sqrt(1 - a));
        double distance = earthRadius * c;

        int meterConversion = 1609;

        return new Float(distance * meterConversion).floatValue();
    }

    public float calculateTotalDistance(List<LatLng> pathPoints) {
        float totalDistance = 0;

        for (int i = 0; i < pathPoints.size() - 1; i++) {
            totalDistance += calculateDistance(pathPoints.get(i),
                                               pathPoints.get(i + 1));
        }

        return totalDistance / 1000;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        refreshMap();
    }
}
