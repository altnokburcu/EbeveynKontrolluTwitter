package com.codepath.apps.twitterfilter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.twitterfilter.models.FirebaseUserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.view.View.*;

public class RegisterActivity extends AppCompatActivity {

    private EditText password;
    private EditText email;
    Button ebv_register;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        auth = FirebaseAuth.getInstance();

        password = (EditText) findViewById(R.id.pass);
        email = (EditText) findViewById(R.id.email);

        ebv_register = (Button) findViewById(R.id.ebv_register);


    }


    public void kayitTamamla(View view) {
        final String mail = email.getText().toString();
        final String pass = password.getText().toString();

        Log.d("edittext", password.getText().toString());
        Log.d("edittext2", email.getText().toString());
        if (TextUtils.isEmpty(mail)) {
            Toast.makeText(getApplicationContext(), "Email adresinizi giriniz!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(getApplicationContext(), "Parolanızı giriniz!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (pass.length() < 6) {
            Toast.makeText(getApplicationContext(), "Parolanız en az 6 karakter olmalıdır!", Toast.LENGTH_SHORT).show();
            return;
        }

        //create user
        auth.createUserWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(RegisterActivity.this, "Hesabınız oluşturuldu" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                        writeDatabase(mail, pass);
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Hesabınız oluşturulamadı" + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(RegisterActivity.this, ParentActivity.class));
                            finish();
                        }
                    }
                });

    }

    private DatabaseReference mDatabase;
    private FirebaseUserModel firebaseUserModel;

    private void writeDatabase(String mail, String pass) {
        // Write a message to the database
        mDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseUserModel = new FirebaseUserModel(mail, pass);
        mDatabase.child("users").child(auth.getCurrentUser().getUid()).setValue(firebaseUserModel);
    }
}