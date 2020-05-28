package com.example.sunfinder.FactsActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;

import com.example.sunfinder.R;

public class FactsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facts);
        int orientation = getResources().getConfiguration().orientation;
        if(orientation!= Configuration.ORIENTATION_PORTRAIT)
        {
            finish();
            return;
        }
    }
}
