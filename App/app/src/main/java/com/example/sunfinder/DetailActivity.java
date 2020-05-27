package com.example.sunfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        int orientation = getResources().getConfiguration().orientation;
        if(orientation!= Configuration.ORIENTATION_PORTRAIT)
        {
            finish();
            return;
        }
    }
}
