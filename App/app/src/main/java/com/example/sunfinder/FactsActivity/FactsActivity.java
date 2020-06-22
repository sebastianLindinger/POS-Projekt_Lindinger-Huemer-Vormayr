package com.example.sunfinder.FactsActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    public static final String URL_AddFact = "http://varchar42.me:3000/sunFinder/put?id=<id>";
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

                    sendDataToServer(city.get_id(), fact);

                    fragment_facts.showInformation(city);
                }
                vDialog.dismiss();
            }
        });
        vDialog.show();
    }

    public static void sendDataToServer(String id, String fact) {
        ServerTask serverTask = new ServerTask(new OnTaskFinishedListener() {
            @Override
            public void onTaskFinished(String response) {
                Log.d(TAG, response);
            }
        });

        try {
            //create JSON object
            JSONObject factData = FactsActivity.createJSONObject(fact);

            //send to Server
            serverTask.execute("PUT", URL_AddFact.replace("<id>", id), factData.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private static JSONObject createJSONObject(String fact) throws JSONException {
        JSONObject factData = new JSONObject();
        factData.put("fact", fact);
        return factData;
    }

}
