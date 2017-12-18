package com.codepath.apps.twitterfilter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.codepath.apps.twitterfilter.models.DbHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddFilterActivity extends AppCompatActivity {
    EditText filtre;
    ListView filtre_listesi;
    Button ekle;
    private int uzunluk;
    public String username;
    private String[] filtreler =
            {"burcu","gs"};
    DbHelper vt = new DbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_filter);
        Intent intent= getIntent();
        username = intent.getStringExtra("username");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(username).child("filtreler");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                uzunluk = Integer.parseInt(dataSnapshot.getChildrenCount()+"");
                Log.d("uzunluk", "Value is: " + String.valueOf(uzunluk));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("", "Failed to read value.", error.toException());
            }
        });
        filtre = (EditText) findViewById(R.id.filtre);
        ekle = (Button) findViewById(R.id.btn_ekle);
        listele(uzunluk);
        ekleOnClick();
    }
    private void listele(int count){
        ListView liste=(ListView) findViewById(R.id.filtre_listesi);
        filtreler = vt.getFirebase(count);
        //(B)
        if(filtreler!=null) {
            ArrayAdapter<String> veriAdaptoru = new ArrayAdapter<String>
                    (this, android.R.layout.simple_list_item_1, android.R.id.text1, filtreler);

            //(C)
            liste.setAdapter(veriAdaptoru);
        }
    }


    private void ekleOnClick() {
        ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String gelenfiltre=filtre.getText().toString();
            Boolean sonuc = vt.filtreEkle(gelenfiltre);
            Log.d("ekle",sonuc.toString());
              //  Activity Timeline = new Activity(this, TimelineActivity.class);


                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("users").child(username).child("filtreler");
                Log.d("uzunluk", "Value is: " + String.valueOf(uzunluk));
                myRef.child(String.valueOf(uzunluk)).setValue(filtre.getText().toString());
                listele(4);
            }
        });
    }
}

