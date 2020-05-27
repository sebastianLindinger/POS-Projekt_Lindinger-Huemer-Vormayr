package com.example.sunfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MasterAcitvity extends AppCompatActivity implements OnTownSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
    }

    @Override
    public void townSelected(int position) {
        callDetailActivity();
    }
    private void callDetailActivity()
    {
        Intent intent = new Intent(this, DetailActivity.class);
        //Implement this --> intent.putExtra();
        startActivity(intent);
    }
}
