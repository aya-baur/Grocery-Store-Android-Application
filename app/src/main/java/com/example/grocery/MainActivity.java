package com.example.grocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {


    EditText editTextEmail;
    EditText editTextPass;


    MaterialButton btnLoginAsCustomer;
    MaterialButton btnLoginAsSeller;
    String DB_REF;

    TextView txtCreateAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtCreateAccount=findViewById(R.id.txtCreateAccount);
        editTextEmail=findViewById(R.id.editTextEmail);
        editTextPass=findViewById(R.id.editTextPass);

        btnLoginAsCustomer=findViewById(R.id.btnLoginAsCustomer);
        btnLoginAsSeller=findViewById(R.id.btnLoginAsSeller);


        btnLoginAsCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DB_REF="customers";
                login();

            }
        });

        btnLoginAsSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DB_REF="stores";
                login();

            }
        });

        txtCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(MainActivity.this).setPositiveButton("Create Account As Customer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        Toast.makeText(MainActivity.this,"Create Account As Customer  Clicked",Toast.LENGTH_SHORT).show();

                        //TODO
                    }
                }).setNeutralButton("Create Account As Store Owner ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        Toast.makeText(MainActivity.this,"Create Account As Store Owner Clicked",Toast.LENGTH_SHORT).show();

                        //TODO
                    }
                }).show();

            }
        });

    }

    private void login()
    {

        String USER_EMAIL=editTextEmail.getText().toString();
        String USER_PASS=editTextPass.getText().toString();

        if(USER_EMAIL.isEmpty())
        {
            editTextEmail.setError("Required Field");
            editTextEmail.requestFocus();
            return;
        }
        if(USER_PASS.isEmpty())
        {
            editTextPass.setError("Required Field");
            editTextPass.requestFocus();
            return;
        }

        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Logging In");
        progressDialog.setCancelable(false);
        progressDialog.show();


        FirebaseDatabase.getInstance().getReference(DB_REF).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressDialog.dismiss();

                if(dataSnapshot.exists())
                {

                    for(DataSnapshot child:dataSnapshot.getChildren())
                    {

                        String email=child.child("email").getValue().toString();
                        String password=child.child("password").getValue().toString();
                        String id=child.child("id").getValue().toString();
                        if(USER_EMAIL.trim().equalsIgnoreCase(email.trim()) && USER_PASS.equals(password))
                        {


                            if(DB_REF.equals("customers"))
                            {
                                Intent n=new Intent(MainActivity.this,CustomerHomeActivity.class);
                                n.putExtra("ID",id);
                                startActivity(n);
                            }
                            else
                            {

                                Intent n=new Intent(MainActivity.this,StoreOwnerHomeActivity.class);
                                n.putExtra("ID",id);
                                startActivity(n);

                            }
                            return;
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


    }
}