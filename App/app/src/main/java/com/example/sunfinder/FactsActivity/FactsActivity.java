package com.example.sunfinder.FactsActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sunfinder.DataAdministration.City;
import com.example.sunfinder.R;
import com.example.sunfinder.ServerCommunication.OnTaskFinishedListener;
import com.example.sunfinder.ServerCommunication.ServerTask;

import org.json.JSONException;
import org.json.JSONObject;

public class FactsActivity extends AppCompatActivity implements OnAddFactListener {

    private City city;
    private final String URL_AddFact = "http://varchar42.me:3000/sunFinder/put?id=<id>";
    private static final String TAG = FactsActivity.class.getSimpleName();

    Fragment_Facts fragment_facts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_facts);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation != Configuration.ORIENTATION_PORTRAIT) {


            finish();
            return;
        }
        city = (City) getIntent().getSerializableExtra("city");
        fragment_facts = (Fragment_Facts) getSupportFragmentManager().findFragmentById(R.id.fragment_facts);
        fragment_facts.setCity(city);

    }

    @Override
    public void addFactListener() {
        final View vDialog = getLayoutInflater().inflate(R.layout.alertdialog_new_fact, null);
        AlertDialog.Builder newNoteDialog = new AlertDialog.Builder(this);
        newNoteDialog.setTitle("Neuen Fakt verfassen");
        newNoteDialog.setMessage("Beachten Sie das dieser Fakt von jedem Benutzer gelesen werden kann!");
        newNoteDialog.setView(vDialog);
        newNoteDialog.setPositiveButton("Senden", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText txtFact = vDialog.findViewById(R.id.editText_newFact);
                if (!txtFact.getText().equals("")) {
                    String fact = txtFact.getText().toString();
                    city.addFact(fact);
                    ServerTask serverTask = new ServerTask(new OnTaskFinishedListener() {
                        @Override
                        public void onTaskFinished(String response) {
                            Log.d(TAG, response);
                        }
                    });
                    //PUT the new Fact on the Server
                    JSONObject factData = new JSONObject();
                    try {
                        factData.put("fact", fact);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    serverTask.execute("PUT", URL_AddFact.replace("<id>", city.get_id()), factData.toString());

                    fragment_facts.showInformation(city);
                }
            }
        });
        newNoteDialog.setNegativeButton("Abbrechen", null);
        newNoteDialog.show();
    }
}
