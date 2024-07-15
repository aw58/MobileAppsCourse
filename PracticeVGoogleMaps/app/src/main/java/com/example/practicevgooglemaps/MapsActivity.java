package com.example.practicevgooglemaps;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

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

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap map;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_maps);

        searchView = findViewById(R.id.searchView);
        createSearchViewListener();

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
        //switch (v.getId()){
            //
        //}
    }

    private void createSearchViewListener(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println("SEARCHING...");
                String locationName = searchView.getQuery().toString();
                List<Address> addressList = null;
                if(locationName != null || locationName.equals("")){
                    Geocoder geocoder = new Geocoder(MapsActivity.this);
                    try{
                        addressList = geocoder.getFromLocationName(locationName, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    map.addMarker(new MarkerOptions().position(latLng).title(locationName));
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
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