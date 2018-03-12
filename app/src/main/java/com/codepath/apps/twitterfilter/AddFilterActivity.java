package com.codepath.apps.twitterfilter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.twitterfilter.models.DbHelper;
import com.codepath.apps.twitterfilter.models.FilterAdapter;
import com.codepath.apps.twitterfilter.models.FilterModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
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
    TextView usermail;
    Button btn_cikis;

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    public String userId = auth.getCurrentUser().getUid();
    private List<FilterModel> filterList = new ArrayList<>();
    DbHelper vt = new DbHelper(this);
    private DatabaseReference mDatabase;
    FilterAdapter filterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_filter);

        mDatabase = FirebaseDatabase.getInstance().getReference("users/"+auth.getCurrentUser().getUid()+"/filtreler");

        liste =(ListView) findViewById(R.id.filtre_listesi);
        filtre = (EditText) findViewById(R.id.filtre);
        ekle = (Button) findViewById(R.id.btn_ekle);
        usermail = (TextView) findViewById(R.id.usermail);
        btn_cikis = (Button) findViewById(R.id.btn_cikis);
        listele();
        filterAdapter = new FilterAdapter(this,filterList,userId);
        liste.setAdapter(filterAdapter);

        usermail.setText(auth.getCurrentUser().getEmail());


        btn_cikis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertMessage = new AlertDialog.Builder(v.getContext()).create();
                alertMessage.setTitle("Çıkış Yap");
                alertMessage.setMessage("Çıkış yapmak istediğinizden emin misiniz?");
                alertMessage.setButton("Evet", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        auth.signOut();
                        finish();
                    }
                });
                alertMessage.setButton2("Hayır", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertMessage.show();

            }
        });
    }

    private void listele(){

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                filterList.clear();
                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    filterList.add(snap.getValue(FilterModel.class));
                }
                filterAdapter.notifyDataSetChanged();
                //silme butonuna tıklandığında silme yapıp silinen kelimeyi göstermememizi sağladı
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void writeNewFilter(String gelenFiltre) {
        FilterModel filterModel = new FilterModel(gelenFiltre);
        mDatabase.push().setValue(filterModel);
    }

    public void ekleOnClick(View view) {
        String gelenfiltre=filtre.getText().toString();
        Boolean sonuc = vt.filtreEkle(gelenfiltre);
        writeNewFilter(gelenfiltre);
        Toast.makeText(this, "Filtre eklendi!",Toast.LENGTH_LONG).show();
        filtre.setText("");
    }

    public void childControl(View view) {
        Intent i = new Intent(this, ChildActivity.class );
        startActivity(i);
    }
}

