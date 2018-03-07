package com.codepath.apps.twitterfilter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.twitterfilter.models.DbHelper;
import com.codepath.apps.twitterfilter.models.FilterAdapter;
import com.codepath.apps.twitterfilter.models.FilterModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddFilterActivity extends AppCompatActivity {
    EditText filtre;
    Button ekle;
    ListView liste;
    public String username;
    private List<FilterModel> filterList = new ArrayList<>();
    DbHelper vt = new DbHelper(this);
    private DatabaseReference mDatabase;
    FilterAdapter filterAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_filter);

        Intent intent= getIntent();
        username = intent.getStringExtra("username");

        mDatabase = FirebaseDatabase.getInstance().getReference("users/"+username+"/filtreler");

        liste =(ListView) findViewById(R.id.filtre_listesi);
        filtre = (EditText) findViewById(R.id.filtre);
        ekle = (Button) findViewById(R.id.btn_ekle);
        filterAdapter = new FilterAdapter(this,filterList,username);
        liste.setAdapter(filterAdapter);
        listele(username);

    }

    private void listele(String username){

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                filterList.clear();
                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    filterList.add(snap.getValue(FilterModel.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("", "Failed to read value.", error.toException());
            }
        });
        filterAdapter.notifyDataSetChanged();

    }


    private void writeNewFilter(String gelenFiltre) {
        FilterModel filterModel = new FilterModel(gelenFiltre);
        mDatabase.push().setValue(filterModel);
    }

    public void ekleOnClick(View view) {
        String gelenfiltre=filtre.getText().toString();
        Boolean sonuc = vt.filtreEkle(gelenfiltre);
        writeNewFilter(gelenfiltre);
    }
}

