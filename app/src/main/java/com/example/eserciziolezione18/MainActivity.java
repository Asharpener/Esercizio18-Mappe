package com.example.eserciziolezione18;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.Manifest;
import android.util.Log;

import com.google.android.gms.location.CurrentLocationRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.Task;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private  FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted: require permission
            requestPermission();

             } else {
            // Permission has already been granted
            Log.d("Permessi", "onCreate: ");
        }
    }

    private void onRequestPermissionsResult(Map<String, Boolean> result) {
        Log.d("Permessi", "chiamata funzione request permission");

        Boolean fineLocationGranted = result.getOrDefault(
                Manifest.permission.ACCESS_FINE_LOCATION, false
        );
        Boolean coarseLocationGranted = result.getOrDefault(
                Manifest.permission.ACCESS_COARSE_LOCATION, false
        );

        if (fineLocationGranted != null && fineLocationGranted) {
            //Precide location access granted
            Log.d("Permessi", "onRequestPermissionsResult: fineLocationGranted");
            requestPosition();
        } else if (coarseLocationGranted != null && coarseLocationGranted) {
            //Only coarse location access granted
            Log.d("Permessi", "onRequestPermissionsResult: coarseLocationGranted");
            requestPosition();
        } else {
            //No location access granted
            Log.d("Permessi", "onRequestPermissionsResult: no location access granted");
        }


    }

    private void requestPermission() {
        Log.d("Permessi", "requestPosition: richiesta permessi ");
        ActivityResultLauncher<String[]> locationPermissionRequest = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(),
                (result) -> onRequestPermissionsResult(result));
        locationPermissionRequest.launch(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION});

    }
    private void requestPosition() {
        CurrentLocationRequest clr = new
                CurrentLocationRequest.Builder().setPriority(Priority.PRIORITY_HIGH_ACCURACY).build();
        Log.d("Permessi", "requestPosition: richiesta posizione");
        @SuppressLint("MissingPermission") Task<Location> locationTask = fusedLocationClient.getCurrentLocation(clr, null);
        locationTask.addOnSuccessListener(
                location -> {
                    if (location != null) {
                        Log.d("Permessi", "onSuccess: " + location.toString());
                    } else {
                        Log.d("Permessi", "onSuccess: location is null");
                    }

                }
        );
    }


}