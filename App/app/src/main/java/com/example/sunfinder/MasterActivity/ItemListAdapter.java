package com.example.sunfinder.MasterActivity;

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

public class ItemListAdapter extends BaseAdapter {
    private List<City>cities;
    private int layoutId;
    private LayoutInflater inflater;

    public ItemListAdapter(List<City> cities, int layoutId, Context context) {
        this.cities = cities;
        this.layoutId = layoutId;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return cities.size();
    }

    @Override
    public Object getItem(int position) {
        return cities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DecimalFormat df = new DecimalFormat("#");

        City city = cities.get(position);
        View listItem = (convertView == null)? inflater.inflate(this.layoutId, null):convertView;
        ((TextView)listItem.findViewById(R.id.textView_listViewItem_master_city)).setText(city.getName());
        ((TextView)listItem.findViewById(R.id.textView_listViewItem_master_distance)).setText(df.format(city.getDistance())+" KM");
        return listItem;
    }
}
