package com.example.practicevgooglemaps;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.SquareCap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_maps);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment_container_view);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;


        map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                /*
                map.setMinZoomPreference(12);
                LatLng ny = new LatLng(40.7143528, -74.0059731);
                map.moveCamera(CameraUpdateFactory.newLatLng(ny));
                */
                createPolyLinesOnMap();
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    private void createPolyLinesOnMap(){
        LatLng sydney = new LatLng(-34, 151);
        LatLng tokyo = new LatLng(35.67, 139.65);
        LatLng singapore = new LatLng(1.35, 103.81);

        map.addMarker(new MarkerOptions().position(sydney).title("Sydney, Australia"));
        map.addMarker(new MarkerOptions().position(singapore).title("Singapore, Singapore"));
        map.addMarker(new MarkerOptions().position(tokyo).title("Tokyo, Japan"));

        PolylineOptions polylineOptions = new PolylineOptions()
                .width(15)
                .color(Color.RED)
                .startCap(new SquareCap())
                .endCap(new SquareCap());
        polylineOptions.add(sydney, tokyo, singapore);
        map.addPolyline(polylineOptions);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(tokyo, 2));



    }
}