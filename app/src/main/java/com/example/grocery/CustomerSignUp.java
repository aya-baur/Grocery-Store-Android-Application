package com.example.grocery;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.grocery.View.MainActivity;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerSignUp extends AppCompatActivity {
    private static boolean idFound;
    EditText editTextEmailSignUpCustomer;
    EditText editTextNameSignUpCustomer;
    EditText editTextPassSignUpCustomer;

    MaterialButton btnSignUpCustomer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_sign_up);
        idFound = false;

        editTextEmailSignUpCustomer = findViewById(R.id.editTextEmailSignUpCustomer);
        editTextNameSignUpCustomer = findViewById(R.id.editTextNameSignUpCustomer);
        editTextPassSignUpCustomer = findViewById(R.id.editTextPassSignUpCustomer);

        btnSignUpCustomer = findViewById(R.id.btnSignUpCustomer);

        btnSignUpCustomer.setOnClickListener(v -> signUpCustomer());


    }


    private void signUpCustomer() {


        String USER_EMAIL = editTextEmailSignUpCustomer.getText().toString();
        String USER_NAME = editTextNameSignUpCustomer.getText().toString();
        String USER_PASS = editTextPassSignUpCustomer.getText().toString();

        if (USER_EMAIL.isEmpty()) {
            editTextEmailSignUpCustomer.setError("Required Field");
            editTextEmailSignUpCustomer.requestFocus();
            return;
        }

        Pattern patternCheck = Pattern.compile("^\\S+@\\S+\\.\\S+$");
        Matcher matcherCheck = patternCheck.matcher(USER_EMAIL);

        if (!matcherCheck.matches()) {
            editTextEmailSignUpCustomer.setError("Invalid Email");
            editTextEmailSignUpCustomer.requestFocus();
            return;
        }

        if (USER_NAME.isEmpty()) {
            editTextNameSignUpCustomer.setError("Required Field");
            editTextNameSignUpCustomer.requestFocus();
            return;
        }

        if (USER_PASS.isEmpty()) {
            editTextPassSignUpCustomer.setError("Required Field");
            editTextPassSignUpCustomer.requestFocus();
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

        mDatabase = FirebaseDatabase.getInstance().getReference("customers");

        Customer newAccount = new Customer(USER_NAME, USER_EMAIL, USER_PASS);

        // Check if id already in database
        mDatabase.child(String.valueOf(newAccount.getId())).child("email").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String email = dataSnapshot.getValue(String.class);
                if (newAccount.getEmail().equals(email)) {
                    CustomerSignUp.idFound = true;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        if (!CustomerSignUp.idFound) {
            mDatabase.child(String.valueOf(newAccount.getId())).setValue(newAccount);

            Intent n = new Intent(CustomerSignUp.this, CustomerHomeActivity.class);
            n.putExtra(MainActivity.CUSTOMER_ID, String.valueOf(newAccount.getId()));
            startActivity(n);
        }

        /*

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
                            Intent n=new Intent(CustomerSignUp.this,CustomerHomeActivity.class);
                            n.putExtra("ID",id);
                            startActivity(n);
                        }

                    }
                }


                Toast.makeText(CustomerSignUp.this,"Email Or Password Is Wrong",Toast.LENGTH_SHORT).show();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(CustomerSignUp.this,"Error",Toast.LENGTH_SHORT).show();
            }
        });

        */


    }

     /*

    public void writeNewCustomer(String userId, String name, String email) {
        User user = new User(name, email);

        mDatabase.child("users").child(userId).setValue(user);
    }
    */
}
