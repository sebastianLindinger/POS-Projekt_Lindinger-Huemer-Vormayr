package com.example.sunfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnSunClickedListener{

    private static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onSunClicked(Double lon, Double lat) {
        Log.d(TAG, "onSunClicked: entered");
        Log.d(TAG, "lon="+lon+" lat="+lat);
        if(lon == null || lat == null)
        {
            Toast.makeText(this, "Sie müssen zuerst einen Ort angeben!", Toast.LENGTH_LONG).show();
        }
        else {
            callMasterActivity();
        }
    }
    private void callMasterActivity()
    {
        //MasterActivity aufrufen.
    }
}
