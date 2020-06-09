package com.example.sunfinder.DetailActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.sunfinder.DataAdministration.City;
import com.example.sunfinder.DataAdministration.DataStorage;
import com.example.sunfinder.FactsActivity.FactsActivity;
import com.example.sunfinder.FactsActivity.Fragment_Facts;
import com.example.sunfinder.FactsActivity.OnAddFactListener;
import com.example.sunfinder.MainActivity.Fragment_Start;
import com.example.sunfinder.R;

public class DetailActivity extends AppCompatActivity implements OnViewFactsListener, OnAddFactListener {

    private boolean showFacts = false;
    private Fragment_Facts fragment_facts;
    private Fragment_Detail fragment_detail;
    private static final String TAG = DetailActivity.class.getSimpleName();

    private City city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        fragment_facts = (Fragment_Facts)getSupportFragmentManager().findFragmentById(R.id.fragment_facts);
        fragment_detail = (Fragment_Detail) getSupportFragmentManager().findFragmentById(R.id.fragment3);

        showFacts = fragment_facts != null && fragment_facts.isInLayout();
        if(showFacts){
            viewFactsClicked();
            findViewById(R.id.button_Detail_ViewFacs).setVisibility(View.GONE);
        }

        city = (City) getIntent().getSerializableExtra("city");
        fragment_detail.setCity(city);
    }
    @Override
    public void viewFactsClicked() {
        Log.d(TAG, "vieFactsClicked: entered");
        if (showFacts){
            fragment_facts.showinformation();
        }
        else callFactsActivity();
    }
    private void callFactsActivity()
    {
        Intent intent = new Intent(this, FactsActivity.class);
        //Implement this --> intent.putExtra();
        startActivity(intent);
    }

    @Override
    public void addFactListener() {

    }
}
