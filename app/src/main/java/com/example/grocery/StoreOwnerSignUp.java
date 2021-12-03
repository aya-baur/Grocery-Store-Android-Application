package com.example.grocery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StoreOwnerSignUp extends AppCompatActivity {
    EditText editTextEmailSignUpStoreOwner;
    EditText editTextNameSignUpStoreOwner;
    EditText editTextPassSignUpStoreOwner;

    MaterialButton btnSignUpStoreOwner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_owner_sign_up);

        editTextEmailSignUpStoreOwner = findViewById(R.id.editTextEmailSignUpStoreOwner);
        editTextNameSignUpStoreOwner = findViewById(R.id.editTextNameSignUpStoreOwner);
        editTextPassSignUpStoreOwner = findViewById(R.id.editTextPassSignUpStoreOwner);

        btnSignUpStoreOwner = findViewById(R.id.btnSignUpStoreOwner);

        btnSignUpStoreOwner.setOnClickListener(v -> signUpStoreOwner());
    }


    private void signUpStoreOwner() {


        String USER_EMAIL = editTextEmailSignUpStoreOwner.getText().toString();
        String USER_NAME = editTextNameSignUpStoreOwner.getText().toString();
        String USER_PASS = editTextPassSignUpStoreOwner.getText().toString();

        if (USER_EMAIL.isEmpty()) {
            editTextEmailSignUpStoreOwner.setError("Required Field");
            editTextEmailSignUpStoreOwner.requestFocus();
            return;
        }

        Pattern patternCheck = Pattern.compile("^\\S+@\\S+\\.\\S+$");
        Matcher matcherCheck = patternCheck.matcher(USER_EMAIL);

        if (!matcherCheck.matches()) {
            editTextEmailSignUpStoreOwner.setError("Invalid Email");
            editTextEmailSignUpStoreOwner.requestFocus();
            return;
        }

        if (USER_NAME.isEmpty()) {
            editTextNameSignUpStoreOwner.setError("Required Field");
            editTextNameSignUpStoreOwner.requestFocus();
            return;
        }

        if (USER_PASS.isEmpty()) {
            editTextPassSignUpStoreOwner.setError("Required Field");
            editTextPassSignUpStoreOwner.requestFocus();
            return;
        }

        /*

        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Signing up");
        progressDialog.setCancelable(false);
        progressDialog.show();

        */
        DatabaseReference mDatabase;
        Integer ID = USER_EMAIL.hashCode();

        mDatabase = FirebaseDatabase.getInstance().getReference("stores");

        Store newAccount = new Store(USER_NAME, USER_EMAIL, USER_PASS);
        // Check if already in database

        // Create User
        mDatabase.child(String.valueOf(newAccount.getId())).setValue(newAccount);

        Intent n=new Intent(StoreOwnerSignUp.this,StoreOwnerHomeActivity.class);
        n.putExtra("ID",ID.toString());
        startActivity(n);

        /*



            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressDialog.dismiss();

                if(dataSnapshot.exists())
                {

                    for(DataSnapshot child:dataSnapshot.getChildren())
                    {

                        String email=child.setValue(USER_EMAIL);
                        String password=child.child("password").getValue().toString();
                        String id=child.child("id").getValue().toString();
                        if(USER_EMAIL.trim().equalsIgnoreCase(email.trim()) && USER_PASS.equals(password))
                        {
                            Intent n=new Intent(MainActivity.this,CustomerHomeActivity.class);
                            n.putExtra("ID",id);
                            startActivity(n);
                        }

                    }
                }


                Toast.makeText(MainActivity.this,"Email Or Password Is Wrong",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_SHORT).show();
            }
        });

         */

    }

    /*
    public void writeNewStoreOwner(String userId, String name, String email) {
        User user = new User(name, email);

        mDatabase.child("users").child(userId).setValue(user);
    }

     */
}
