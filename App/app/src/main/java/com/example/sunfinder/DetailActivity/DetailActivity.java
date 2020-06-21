package com.example.sunfinder.DetailActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.sunfinder.DataAdministration.City;
import com.example.sunfinder.DataAdministration.DataStorage;
import com.example.sunfinder.FactsActivity.FactsActivity;
import com.example.sunfinder.FactsActivity.Fragment_Facts;
import com.example.sunfinder.FactsActivity.OnAddFactListener;
import com.example.sunfinder.MainActivity.Fragment_Start;
import com.example.sunfinder.R;

import java.util.ArrayList;

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
        fragment_facts = (Fragment_Facts) getSupportFragmentManager().findFragmentById(R.id.fragment_facts);
        fragment_detail = (Fragment_Detail) getSupportFragmentManager().findFragmentById(R.id.fragment_detail);

        city = (City) getIntent().getSerializableExtra("city");
        fragment_detail.setCity(city);
        fragment_detail.setTextViews();

        showFacts = fragment_facts != null && fragment_facts.isInLayout();
        if (showFacts) {
            viewFactsClicked();
            findViewById(R.id.button_Detail_ViewFacs).setVisibility(View.GONE);
        }
    }

    @Override
    public void viewFactsClicked() {
        Log.d(TAG, "vieFactsClicked: entered");
        Log.d(TAG, city.getName());

        if (showFacts) fragment_facts.showInformation(city);
        else callFactsActivity();
    }

    private void callFactsActivity() {
        Intent intent = new Intent(this, FactsActivity.class);
        intent.putExtra("city", city);
        startActivity(intent);
    }

    @Override
    public void addFactListener() {
        final View vDialog = getLayoutInflater().inflate(R.layout.alertdialog_new_fact, null);
        AlertDialog.Builder newNoteDialog = new AlertDialog.Builder(this);
        newNoteDialog.setTitle("Neuen Fact verfassen");
        newNoteDialog.setMessage("Beachten Sie das dieser Fact von jedem Benutzer gelesen werden kann!");
        newNoteDialog.setView(vDialog);
        newNoteDialog.setPositiveButton("Senden", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText txtFact = vDialog.findViewById(R.id.editText_newFact);
                if (!txtFact.getText().equals("")) {
                    String fact = txtFact.getText().toString();
                    city.addFact(fact);

                    if (showFacts) {
                        fragment_facts.showInformation(city);
                    } else callFactsActivity();
                }
            }
        });
        newNoteDialog.setNegativeButton("Nicht Senden", null);
        newNoteDialog.show();
    }
}
