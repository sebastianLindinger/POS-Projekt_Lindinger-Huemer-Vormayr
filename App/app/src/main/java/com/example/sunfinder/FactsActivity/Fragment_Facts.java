package com.example.sunfinder.FactsActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sunfinder.DataAdministration.City;
import com.example.sunfinder.DetailActivity.DetailActivity;
import com.example.sunfinder.MainActivity.Fragment_Start;
import com.example.sunfinder.MainActivity.MainActivity;
import com.example.sunfinder.MasterActivity.OnTownSelectedListener;
import com.example.sunfinder.R;


public class Fragment_Facts extends Fragment implements View.OnClickListener{

    private static final String TAG = Fragment_Start.class.getSimpleName();

    private Context ctx;
    private OnAddFactListener mListener;
    private City city;

    private Button button_addFact;
    private ListView factList;
    private TextView factFragTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: entered");
        View view = inflater.inflate(R.layout.fragment_fragment__facts, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {
        button_addFact = view.findViewById(R.id.button_Facts_WriteFact);
        button_addFact.setOnClickListener(new AdapterView.OnClickListener(){
            @Override
            public void onClick(View v) {
               mListener.addFactListener();
            }
        });

        factFragTitle = view.findViewById(R.id.textView_Facts_Title);
        factList = view.findViewById(R.id.listview_Facts);
    }
    public void showinformation(City pCity) {
       ArrayAdapter<String> factAdapter =
                new ArrayAdapter<String>(ctx, android.R.layout.simple_list_item_1, pCity.getFacts());

       factList.setAdapter(factAdapter);
       factFragTitle.setText("Nutzerkommentare zu " +pCity.getName());
    }

    public void setCity(City city) {
        showinformation(city);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAddFactListener) {
            mListener = (OnAddFactListener) context;
            ctx = context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAddFactListener");
        }
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == button_addFact.getId())
        {
            mListener.addFactListener();
        }
    }

}
