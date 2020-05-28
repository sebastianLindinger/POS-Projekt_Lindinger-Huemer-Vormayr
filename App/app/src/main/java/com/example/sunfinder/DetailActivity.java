package com.example.sunfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

public class DetailActivity extends AppCompatActivity implements OnViewFactsListener{

    private boolean showFacts = false;
    private Fragment_Facts fragment_facts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        fragment_facts = (Fragment_Facts)getSupportFragmentManager().findFragmentById(R.id.fragment_facts);
        showFacts = fragment_facts != null && fragment_facts.isInLayout();
    }
    @Override
    public void viewFactsClicked() {
        if (showFacts)fragment_facts.showinformation();
        else callFactsActivity();
    }
    private void callFactsActivity()
    {
        Intent intent = new Intent(this, FactsActivity.class);
        //Implement this --> intent.putExtra();
        startActivity(intent);
    }
}
