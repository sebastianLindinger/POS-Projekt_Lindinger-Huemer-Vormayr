package com.example.sunfinder.DetailActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
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
import com.example.sunfinder.ServerCommunication.OnTaskFinishedListener;
import com.example.sunfinder.ServerCommunication.ServerTask;

import org.json.JSONException;
import org.json.JSONObject;

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
        final Dialog vDialog = new Dialog(this);
        vDialog.setContentView(R.layout.alertdialog_new_fact);

        vDialog.findViewById(R.id.button_dialog_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vDialog.dismiss();
            }
        });
        vDialog.findViewById(R.id.button_dialog_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "entered: sendClicked");
                EditText txtFact = vDialog.findViewById(R.id.editText_newFact);
                if (!txtFact.getText().equals("")) {
                    String fact = txtFact.getText().toString();
                    city.addFact(fact);

                    FactsActivity.sendDataToServer(city.get_id(), fact);

                    if (showFacts) {
                        fragment_facts.showInformation(city);
                    } else callFactsActivity();
                }
                vDialog.dismiss();
            }
        });
        vDialog.show();
    }
}
