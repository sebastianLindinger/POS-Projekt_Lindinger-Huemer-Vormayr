package com.example.sunfinder.MainActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.sunfinder.FactsActivity.FactsActivity;
import com.example.sunfinder.R;

import java.time.LocalDate;

import static android.content.Context.LOCATION_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;


public class Fragment_Start extends Fragment implements View.OnClickListener {

    private static final String TAG = Fragment_Start.class.getSimpleName();
    static final int LOCATION_PERMISSION_REQUEST_CODE = 5421;

    private EditText editText_town;
    private EditText editText_plz;
    Button button_useGPS;
    private ImageButton button_findSun;

    private OnSunClickedListener mListener;

    private double lon = 0.0;
    private double lat = 0.0;

    boolean useGPS = false;
    LocationListener locationListener;
    LocationManager locationManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: entered");

        View view = inflater.inflate(R.layout.fragment_fragment__start, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {
        Log.d(TAG, "initializeViews: entered");
        editText_town = view.findViewById(R.id.editText_Start_Town);
        editText_plz = view.findViewById(R.id.editText_Start_PLZ);
        button_useGPS = view.findViewById(R.id.button_Start_UseGPS);
        button_findSun = view.findViewById(R.id.button_Start_FindSun);
        button_useGPS.setOnClickListener(this);
        button_findSun.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSunClickedListener) {
            mListener = (OnSunClickedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == button_findSun.getId()) {
            mListener.onSunClicked(lon, lat, editText_town.getText().toString(), editText_plz.getText().toString());
        } else if (v.getId() == button_useGPS.getId()) useGPS();
    }

    public void useGPS() {
        if (!useGPS) checkPermissionGPS();
        else {
            useGPS = false;
            button_useGPS.setText("STANDORT PER GPS ERMITTELN");
        }
    }

    public void checkPermissionGPS() {
        String permission = Manifest.permission.ACCESS_FINE_LOCATION;
        if (ActivityCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
            //ask user
            ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            //permission granted
            useGPS = true;
            button_useGPS.setText("STANDORT PER EINGABE ERMITTELN");
            createLocationListener();
        }
    }

    public void createLocationListener() {
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
                    lat = location.getLatitude();
                    lon = location.getLongitude();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    1000,
                    0,
                    locationListener);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
}

