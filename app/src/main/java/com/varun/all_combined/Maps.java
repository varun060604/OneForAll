package com.varun.all_combined;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class Maps extends AppCompatActivity {

    FusedLocationProviderClient client;
    SupportMapFragment supportMapFragment;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_maps);
        client = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(Maps.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        }
        else
        {
            ActivityCompat.requestPermissions(Maps.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }

    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    supportMapFragment.getMapAsync(new OnMapReadyCallback(){
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap){
                            mMap = googleMap;

                            LatLng mMyLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            MarkerOptions options = new MarkerOptions().position(mMyLocation).title("Current Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.location));
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mMyLocation, 14));
                            googleMap.addMarker(options);

                            LatLng mumbai = new LatLng(19.0760, 72.8777);
                            mMap.addMarker(new MarkerOptions().position(mumbai).title("Marker in Mumbai").icon(BitmapDescriptorFactory.fromResource(R.drawable.location)));

                            LatLng Delhi = new LatLng(28.7841, 77.1825);
                            mMap.addMarker(new MarkerOptions().position(Delhi).title("Marker in Delhi").icon(BitmapDescriptorFactory.fromResource(R.drawable.location)));

                            LatLng Kolkata = new LatLng(22.5726, 88.3639);
                            mMap.addMarker(new MarkerOptions().position(Kolkata).title("Marker in Kolkata").icon(BitmapDescriptorFactory.fromResource(R.drawable.location)));

                            LatLng Chennai = new LatLng(13.0827, 80.2707);
                            mMap.addMarker(new MarkerOptions().position(Chennai).title("Marker in Chennai").icon(BitmapDescriptorFactory.fromResource(R.drawable.location)));

                            LatLng Gujarat = new LatLng(22.2587, 71.1924);
                            mMap.addMarker(new MarkerOptions().position(Gujarat).title("Marker in Gujarat").icon(BitmapDescriptorFactory.fromResource(R.drawable.location)));

                        }
                    });
                }
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permission,@NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode,permission,grantResults);
        if(requestCode == 44){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getCurrentLocation();
            }
        }
    }
}
