package com.example.astromedics.views.pacient;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.astromedics.R;
import com.example.astromedics.helpers.ApplicationDateFormat;
import com.example.astromedics.helpers.PermissionHandler;
import com.example.astromedics.model.Localization;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

public class BookAppointmentLocationDisplayActivity extends FragmentActivity implements OnMapReadyCallback {
    public static String LOCATION = "localization";
    private PermissionHandler permissionHandler = new PermissionHandler(this);
    private Localization localization;
    private LatLng currentLocation, selectedLocation;
    private GoogleMap googleMap;
    private FloatingActionButton showIndicationsButton;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    private TextView locationNameTevtView;
    private boolean indicationsEnabled = false;
    protected static final int MY_LOCATION_PERMISSION = 503;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment_location_display);
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(),
                              getString(R.string.api_key),
                              new ApplicationDateFormat().getLocale());
        }
        obtainObjects();
        inflateViews();
        setViewsValues();
        setListeners();
        initViews();
    }

    private void initViews() {
        if (permissionHandler.checkIfPermissionsGrantedInclusive(new String[]{Manifest.permission.ACCESS_FINE_LOCATION})) {
            showIndicationsButton.setEnabled(true);
            showIndicationsButton.setVisibility(View.VISIBLE);
        } else {
            ActivityCompat.requestPermissions(this,
                                              new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                              MY_LOCATION_PERMISSION);
        }
    }

    private void obtainObjects() {
        localization = (Localization) getIntent().getSerializableExtra(LOCATION);
    }

    private void inflateViews() {
        showIndicationsButton = findViewById(R.id.book_appointment_location_display_show_indications);
        locationNameTevtView = findViewById(R.id.book_appointment_location_display_name);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.googleMap);
        mapFragment.getMapAsync(this);
    }

    private void setViewsValues() {
        locationNameTevtView.setText(localization.getName());
    }

    private void setListeners() {
        showIndicationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeLocationElements();
                indicationsEnabled = true;
                refreshMap();
            }
        });
    }


    private void initializeLocationElements() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = createLocationRequest();
        locationCallback = createLocationCallback();
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,
                                                           locationCallback,
                                                           null);
        LocationManager lm = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        try {
            if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                new AlertDialog.Builder(this)
                        .setTitle(R.string.please_enable_gps)
                        .setMessage(R.string.gps_network_not_enabled)
                        .setPositiveButton(R.string.open_location_settings,
                                           new DialogInterface.OnClickListener() {
                                               @Override
                                               public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                                   Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                   getApplicationContext().startActivity(intent);
                                               }
                                           })
                        .setNegativeButton(R.string.cancel_open_location_settings,
                                           null)
                        .show();
            }
        } catch (Exception ex) {
            Log.e("", ex.getMessage());
        }

    }

    private LocationRequest createLocationRequest() {
        LocationRequest auxiliarLocationRequest = new LocationRequest();
        auxiliarLocationRequest.setInterval(1000);
        auxiliarLocationRequest.setFastestInterval(1000);
        auxiliarLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return auxiliarLocationRequest;
    }

    private LocationCallback createLocationCallback() {
        return new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    setCurrentLocation(new LatLng(location.getLatitude(),
                                                  location.getLongitude()));
                }
            }
        };
    }

    private void setCurrentLocation(LatLng location) {
        if (location != null && (currentLocation == null || calculateDistance(location,
                                                                              currentLocation) > 10)) {
            currentLocation = location;
            refreshMap();
        }
    }

    private void refreshMap() {
        if (googleMap != null) {
            googleMap.clear();

            googleMap.addMarker(new MarkerOptions().position(selectedLocation)
                                                   .title(localization.getName()));

            if (currentLocation != null && indicationsEnabled) {
                googleMap.addMarker(new MarkerOptions().position(currentLocation)
                                                       .title(getString(R.string.maps_current_location)));
            }

            if (currentLocation != null && indicationsEnabled) {
                refreshMapZoom(drawRoute());
            } else {
                ArrayList<LatLng> auxiliar = new ArrayList<LatLng>();
                auxiliar.add(selectedLocation);
                refreshMapZoom(auxiliar);
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
        selectedLocation = new LatLng(localization.getLatitude(),
                                      localization.getLongitude());
        refreshMap();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_LOCATION_PERMISSION:
                if (permissionHandler.checkIfPermissionsGrantedInclusive(new String[]{Manifest.permission.ACCESS_FINE_LOCATION})) {
                    initViews();
                }
                break;
        }
    }
}
