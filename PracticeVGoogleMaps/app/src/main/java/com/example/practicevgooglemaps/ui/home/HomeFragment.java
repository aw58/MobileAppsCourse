package com.example.practicevgooglemaps.ui.home;

import android.content.Intent;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.practicevgooglemaps.MainActivity;
import com.example.practicevgooglemaps.R;
import com.example.practicevgooglemaps.databinding.FragmentHomeBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.List;

public class HomeFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener {

    private FragmentHomeBinding binding;
    private GoogleMap map;
    private MapView mapView;
    private MarkerOptions current_location;
    private SearchView searchView;
    private FloatingActionButton floatingActionButton;
    private FusedLocationProviderClient fusedLocationClient;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mapView = root.findViewById(R.id.mapView);
        searchView = root.findViewById(R.id.search);
        floatingActionButton = root.findViewById(R.id.floating_action_button);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        createSearchViewListener();

        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Sending to maps", Toast.LENGTH_SHORT).show();
                //send the map marker to the default maps application on the phone
                String uri = String.format("geo:%s,%s?q=%s(%s)", current_location.getPosition().latitude, current_location.getPosition().longitude, current_location.getPosition().latitude + "," + current_location.getPosition().longitude, Uri.encode(current_location.getTitle()));

                // Create the intent
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));

                // Check if there is an app that can handle this intent
                //if (intent.resolveActivity(getPackageManager()) != null) {
                //    startActivity(intent);
                //}
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        map = googleMap;

        map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    map.setMyLocationEnabled(true);
                    getDeviceLocation();
                }
                else {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MainActivity.LOCATION_PERMISSION_REQUEST_CODE);
                }
            }
        });
    }

    public void getDeviceLocation() {
        try {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                fusedLocationClient.getLastLocation().addOnSuccessListener(requireActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                            current_location = new MarkerOptions().position(currentLatLng).title("You are here");
                            map.addMarker(current_location);
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15));
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    private void createSearchViewListener() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println("SEARCHING...");
                String locationName = searchView.getQuery().toString();
                List<Address> addressList = null;
                if (locationName != null || locationName.equals("")) {
                    Geocoder geocoder = new Geocoder(getContext());
                    try {
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
}