package com.example.sunfinder.DetailActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.sunfinder.FactsActivity.FactsActivity;
import com.example.sunfinder.FactsActivity.Fragment_Facts;
import com.example.sunfinder.FactsActivity.OnAddFactListener;
import com.example.sunfinder.MainActivity.Fragment_Start;
import com.example.sunfinder.R;

public class DetailActivity extends AppCompatActivity implements OnViewFactsListener, OnAddFactListenerd {

    private boolean showFacts = false;
    private Fragment_Facts fragment_facts;
    private static final String TAG = DetailActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        fragment_facts = (Fragment_Facts)getSupportFragmentManager().findFragmentById(R.id.fragment_facts);
        showFacts = fragment_facts != null && fragment_facts.isInLayout();
        if(showFacts){
            viewFactsClicked();
            findViewById(R.id.button_Detail_ViewFacs).setVisibility(View.GONE);
        }
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
}
