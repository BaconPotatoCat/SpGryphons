package edu.sp.spgryphons;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class eventMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private final int REQUEST_LOCATION_PERMISSION = 1;
    private final float zoom = 17;
    Button b;
    private Location loc;
    private FusedLocationProviderClient fusedLoca;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fusedLoca = LocationServices.getFusedLocationProviderClient(this);
        b = findViewById(R.id.getLocation);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLoc();
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        String[] coords = getIntent().getStringArrayExtra("coords");
        LatLng coor = new LatLng(Double.parseDouble(coords[0]),Double.parseDouble(coords[1]));
        mMap.addMarker(new MarkerOptions().position(coor).title("Event Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coor,zoom));
    }

    public void getLoc() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } else {
            Log.d("TAG","getLocation: Permissions granted");
            fusedLoca.getLastLocation().addOnSuccessListener(
                    new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location!=null) {
                                loc = location;
                                Log.d("tag","Latitude: "+loc.getLatitude()+", Longitude: "+loc.getLongitude());
                                LatLng currLoc = new LatLng(loc.getLatitude(),loc.getLongitude());
                                mMap.addMarker(new MarkerOptions().position(currLoc).title("Current Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currLoc,zoom));
                            } else {
                                Log.d("tag","Failed to retrieve location");
                            }
                        }
                    }
            );
        }
    }

    @Override
    public void onRequestPermissionsResult (int requestCode,
                                            @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                //If permission is granted, get location
                //Else show toast
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLoc();
                } else {
                    Toast.makeText(this, R.string.location_permission_denied,
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
