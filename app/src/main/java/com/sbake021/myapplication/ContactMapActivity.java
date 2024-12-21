package com.sbake021.myapplication;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * Activity to display one or more contacts' locations on a Google Map.
 */
public class ContactMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap googleMap;
    private Contact singleContact;
    private ArrayList<Contact> contactList;

    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_map);

        // Initialize MapView
        mapView = findViewById(R.id.mapView);
        Bundle mapViewBundle = savedInstanceState != null ? savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY) : null;
        mapView.onCreate(mapViewBundle);

        // Check if intent has single contact or a list of contacts
        singleContact = getIntent().getParcelableExtra("CONTACT");
        contactList = getIntent().getParcelableArrayListExtra("CONTACT_LIST");

        if (singleContact == null && (contactList == null || contactList.isEmpty())) {
            Toast.makeText(this, "No contact information available to display on the map.", Toast.LENGTH_SHORT).show();
            finish(); // Exit the activity if no contact(s) are provided
            return;
        }

        // Initialize the map
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;

        if (singleContact != null) {
            // Display a single contact's location
            LatLng location = new LatLng(singleContact.getLatitude(), singleContact.getLongitude());
            googleMap.addMarker(new MarkerOptions()
                    .position(location)
                    .title(singleContact.getName())
                    .snippet(singleContact.getAddress()));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
        } else if (contactList != null) {
            // Display all contacts' locations
            LatLng firstLocation = null;
            for (Contact contact : contactList) {
                LatLng location = new LatLng(contact.getLatitude(), contact.getLongitude());
                googleMap.addMarker(new MarkerOptions()
                        .position(location)
                        .title(contact.getName())
                        .snippet(contact.getAddress()));
                if (firstLocation == null) {
                    firstLocation = location;
                }
            }
            if (firstLocation != null) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstLocation, 10));
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }
        mapView.onSaveInstanceState(mapViewBundle);
    }
}
