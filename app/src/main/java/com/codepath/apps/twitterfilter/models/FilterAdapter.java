package com.codepath.apps.twitterfilter.models;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.twitterfilter.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by Burcu on 22.12.2017.
 */

public class FilterAdapter extends BaseAdapter {
    private DatabaseReference mDatabase;
    private LayoutInflater mInflater;
    private List<FilterModel> FilterList;
    private String username;

    public FilterAdapter(Activity activity, List<FilterModel> filter,String username) {
        //XML'i alıp View'a çevirecek inflater'ı örnekleyelim
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        //gösterilecek listeyi de alalım
        FilterList = filter;
        this.username = username;
    }

    @Override
    public int getCount() {
        return FilterList.size();
    }

    @Override
    public FilterModel getItem(int position) {
        return FilterList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView;

        rowView = mInflater.inflate(R.layout.filter_row_layout, null);
        TextView textView =
                (TextView) rowView.findViewById(R.id.filterItem);
        ImageButton imageButton =
                (ImageButton) rowView.findViewById(R.id.filterDelete);
        final FilterModel filter = FilterList.get(position);
        textView.setText(filter.getFilter());
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteFilterFirebase(filter);
            }
        });


        return rowView;
    }
    FilterModel filterM;
    private void deleteFilterFirebase(final FilterModel filtre){
        mDatabase = FirebaseDatabase.getInstance().getReference("users").child(username).child("filtreler");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    if(snap.getValue(FilterModel.class).getFilter().toString().equals(filtre.getFilter().toString())){
                        String s = snap.getKey().toString();
                        mDatabase.child(s).removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("", "Failed to read value.", error.toException());
            }
        });

    }
}

