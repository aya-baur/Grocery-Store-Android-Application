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

import com.example.grocery.Model.User;
import com.example.grocery.Presenter.LoginPresenter;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements LoginPresenter.View {


    EditText editTextEmail;
    EditText editTextPass;


    MaterialButton btnLoginAsCustomer;
    MaterialButton btnLoginAsSeller;
    String DB_REF;
    TextView txtCreateAccount;
    LoginPresenter loginPresenter;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtCreateAccount=findViewById(R.id.txtCreateAccount);
        editTextEmail=findViewById(R.id.editTextEmail);
        editTextPass=findViewById(R.id.editTextPass);

        btnLoginAsCustomer=findViewById(R.id.btnLoginAsCustomer);
        btnLoginAsSeller=findViewById(R.id.btnLoginAsSeller);

        loginPresenter=new LoginPresenter(this);


        btnLoginAsCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DB_REF="customers";
                loginPresenter.validate(getUser());

            }
        });

        btnLoginAsSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DB_REF="stores";

                loginPresenter.validate(getUser());

            }
        });

        txtCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(MainActivity.this).setPositiveButton("Create Account As Customer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(MainActivity.this, CustomerSignUp.class);
                        startActivity(intent);

                    }
                }).setNeutralButton("Create Account As Store Owner ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(MainActivity.this, StoreOwnerSignUp.class);
                        startActivity(intent);
                    }
                }).show();

            }
        });

    }



    @Override
    public void onValidateResponse(boolean isValid, String message)
    {

        if(isValid)
        {
            loginPresenter.login(getUser(),DB_REF);
        }
        else
        {

            Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onLoginResponse(boolean isError, String message, int logedInAs, User user)
    {

        if(isError)
        {
            Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();

        }
        else
        {

            if(logedInAs==1)
            {

                Intent n=new Intent(MainActivity.this, CustomerHomeActivity.class);
                n.putExtra("ID",user.getId());
                startActivity(n);
            }
            if(logedInAs==2)
            {
                Intent n=new Intent(MainActivity.this, StoreOwnerHomeActivity.class);
                n.putExtra("ID",user.getId());
                startActivity(n);
            }
            }
        }


    @Override
    public void showProgressBar() {
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Logging In");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void hideProgressBar() {

        if(progressDialog.isShowing())
                 progressDialog.dismiss();
    }

    public  User getUser()
    {
        User user=new User();
        user.setEmail(editTextEmail.getText().toString());
        user.setPassword(editTextPass.getText().toString());
        return  user;
    }
}