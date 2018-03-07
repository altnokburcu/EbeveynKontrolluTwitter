package com.codepath.apps.twitterfilter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.twitterfilter.models.DbHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.zip.Checksum;

public class ParentActivity extends AppCompatActivity {
    private Checksum dataSnapshot;
    EditText password;
    EditText username;
    Button ebv_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);

        password=(EditText)findViewById(R.id.password);
        username=(EditText)findViewById(R.id.username);
        ebv_login=(Button) findViewById(R.id.ebv_login);


        loginOnClick();

    }

    private void loginOnClick() {
        ebv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");

                // Read from the database
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        //String value = dataSnapshot.getValue(String.class);
                    try{
                        dataSnapshot = dataSnapshot.child(String.valueOf(username.getText()));
                        String parola =  dataSnapshot.child("password").getValue().toString();
                        if(password.getText().toString().equals(parola) ){

                            Intent a = new Intent( ParentActivity.this,AddFilterActivity.class);
                            a.putExtra("username", username.getText().toString());
                            DbHelper dbHelper = new DbHelper(ParentActivity.this);
                            dbHelper.kullaniciEkle(username.getText().toString());
                            startActivity(a);
                            finish();

                        }
                        else{
                            Context context = getApplicationContext();
                            CharSequence text = "Parolanız yanlış, lütfen tekrar deneyiniz!";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                    }
                    catch (Exception e){
                        Context context = getApplicationContext();
                        CharSequence text = "Kullanıcı adınız yanlış, lütfen tekrar deneyiniz!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                    }


                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                    //    Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });


            }
        });

    }

    public void kayitOl(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
