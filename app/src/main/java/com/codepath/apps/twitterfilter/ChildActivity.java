package com.codepath.apps.twitterfilter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.apps.twitterfilter.models.ChildModel;
import com.codepath.apps.twitterfilter.models.DbHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChildActivity extends AppCompatActivity {
    EditText edttxt_child_name;
    TextView childs;
    TextView current_child_name;
    Button btn_child_add;
    Button btn_child_delete;
    ChildModel childModel;
    DbHelper dbHelper;
    String kullanici;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("users").
            child(auth.getCurrentUser().getUid()).child("childs");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

        edttxt_child_name = (EditText) findViewById(R.id.edttxt_child_name);
        childs = (TextView) findViewById(R.id.child_names);
        btn_child_add = (Button) findViewById(R.id.btn_child_add);
        btn_child_delete = (Button) findViewById(R.id.btn_child_delete);
        current_child_name = (TextView) findViewById(R.id.current_child_name);
        dbHelper =  new DbHelper(this);
        kullanici = dbHelper.kullaniciCek();
        if(kullanici!=null){
            current_child_name.setText(dbHelper.kullaniciCek());
        }
        else {
            current_child_name.setText("Telefon bir çocuk üzerine kayıtlı değil");
        }
        getChilds();

    }

    public void childAdd(View view) {
        childModel = new ChildModel(myRef.push().getKey(),edttxt_child_name.getText().toString());
        myRef.child(childModel.getKey()).setValue(childModel);
        dbHelper.kullaniciEkle(edttxt_child_name.getText().toString());
        current_child_name.setText(childModel.getName());
    }

    public void childDelete(View view) {
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()){
                    childModel = snap.getValue(ChildModel.class);
                    if (childModel.getName().equals(edttxt_child_name.getText().toString())){
                        myRef.child(childModel.getKey()).removeValue();
                        childs.setText("");
                        dbHelper.kullaniciSil();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getChilds(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    childs.setText("");
                    for (DataSnapshot snap : dataSnapshot.getChildren()) {
                        childModel = snap.getValue(ChildModel.class);
                        childs.setText(childs.getText().toString() + childModel.getName() + "\n");

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
