package edu.sp.spgryphons;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class addEventMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker mLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        float zoom = 17;
        // Add a marker to default location in SP and move camera
        LatLng defLoc = new LatLng(1.3116252, 103.774457);
        mLocation = mMap.addMarker(new MarkerOptions().position(defLoc).title("Default Location").draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defLoc,zoom));
        setMapLongClick(mMap);
    }

    public void confirmLoc(View v) {
        String[] cfmCoord = {Double.toString(mLocation.getPosition().latitude),Double.toString(mLocation.getPosition().longitude)};
        Log.d("tag","MARKER POSITION: "+cfmCoord[0]+","+cfmCoord[1]);
        Toast.makeText(getApplicationContext(),"Location with lat:"+cfmCoord[0]+" & long:"+cfmCoord[1]+
                " has been set.", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this,addEvent.class);
        i.putExtra("coords",cfmCoord);
        startActivity(i);
    }

    private void setMapLongClick(final GoogleMap map) {
        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                mMap.clear();
                mLocation = map.addMarker(new MarkerOptions().position(latLng));
            }
        });
    }
}
