package com.caffeinlocator;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class VenueListAdapter extends BaseAdapter {

    private ArrayList<Venue> venues;

    private LayoutInflater layoutInflater;

    public VenueListAdapter(Context context, ArrayList<Venue> venues) {
        this.venues = venues;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return venues.size();
    }

    @Override
    public Object getItem(int position) {
        return venues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Log.d(getClass().getSimpleName(), "getView - creating view");
        ViewHolder holder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.venue_list, null);

            holder = new ViewHolder();
            holder.nameView = (TextView) convertView.findViewById(R.id.name);
            holder.distanceView = (TextView) convertView.findViewById(R.id.distance);
            holder.addressView = (TextView) convertView.findViewById(R.id.address);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Venue venue = venues.get(position);

        holder.nameView.setText(venue.getName());
        holder.distanceView.setText(String.valueOf(venue.getDistance()) + "m");
        holder.addressView.setText(venue.getAddress() + " " + venue.getCity() + " " + venue.getState());

        return convertView;
    }

    public void setVenues(ArrayList<Venue> venues) {
        this.venues = venues;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        TextView nameView;
        TextView distanceView;
        TextView addressView;
    }

}