package com.example.sunfinder.FactsActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sunfinder.DataAdministration.City;
import com.example.sunfinder.R;

import java.text.DecimalFormat;
import java.util.List;

public class FactListAdapter extends BaseAdapter {
    private String[] facts;
    private int layoutId;
    private LayoutInflater inflater;

    public FactListAdapter(String[] facts, int layoutId, Context context) {
        this.facts = facts;
        this.layoutId = layoutId;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return facts.length;
    }

    @Override
    public Object getItem(int position) {
        return facts[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String fact = facts[position];
        View listItem = (convertView == null) ? inflater.inflate(this.layoutId, null) : convertView;
        ((TextView) listItem.findViewById(R.id.textView_facts)).setText(fact);
        return listItem;
    }
}
