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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomerSignUp extends AppCompatActivity {
    EditText editTextEmailSignUpCustomer;
    EditText editTextPassSignUpCustomer;

    MaterialButton btnSignUpCustomer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_sign_up);

        /*
        editTextEmailSignUpCustomer = findViewById(R.id.editTextEmailSignUpCustomer);
        editTextPassSignUpCustomer = findViewById(R.id.editTextPassSignUpCustomer);

        btnSignUpCustomer = findViewById(R.id.btnLoginAsCustomer);
        btnSignUpCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //signUp();

            }
        });
         */

    }

    /*
    private void signUp() {

        String USER_EMAIL = editTextEmailSignUpCustomer.getText().toString();
        String USER_PASS = editTextPassSignUpCustomer.getText().toString();

        if (USER_EMAIL.isEmpty()) {
            editTextEmailSignUpCustomer.setError("Required Field");
            editTextEmailSignUpCustomer.requestFocus();
            return;
        }
        if (USER_PASS.isEmpty()) {
            editTextPassSignUpCustomer.setError("Required Field");
            editTextPassSignUpCustomer.requestFocus();
            return;
        }

        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Signing up");
        progressDialog.setCancelable(false);
        progressDialog.show();

        FirebaseDatabase.getInstance().getReference("customers").addListenerForSingleValueEvent(new ValueEventListener() {
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

    }

     */

}
