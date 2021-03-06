package com.example.grocery.View;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocery.Contract.LoginContract;
import com.example.grocery.CustomerHomeActivity;
import com.example.grocery.Model.UserLogin;
import com.example.grocery.Presenter.LoginPresenter;
import com.example.grocery.R;
import com.example.grocery.StoreOwnerHomeActivity;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements LoginContract.View {
    public static final String CUSTOMER_ID = "CUSTOMER_ID";
    public static final String STORE_ID = "STORE_ID";

    private LoginPresenter loginPresenter;
    private ProgressDialog progressDialog;

    private EditText editTextEmail;
    private EditText editTextPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialButton btnLoginAsCustomer = findViewById(R.id.btnLoginAsCustomer);
        MaterialButton btnLoginAsSeller = findViewById(R.id.btnLoginAsSeller);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPass = findViewById(R.id.editTextPass);

        TextView txtCreateAccount = findViewById(R.id.txtCreateAccount);

        loginPresenter=new LoginPresenter(new UserLogin(), this);


        btnLoginAsCustomer.setOnClickListener(view -> loginPresenter.validate(UserLogin.CUSTOMER_TYPE));

        btnLoginAsSeller.setOnClickListener(view -> loginPresenter.validate(UserLogin.STORE_TYPE));

        txtCreateAccount.setOnClickListener(view -> {
            new AlertDialog.Builder(this).setPositiveButton("Customer", (DialogInterface dialog, int which) -> {
                Intent intent = new Intent(MainActivity.this, CustomerSignUp.class);
                startActivity(intent);
            }).setNeutralButton("Store Owner ", (DialogInterface dialog, int which) -> {
                Intent intent = new Intent(MainActivity.this, StoreOwnerSignUp.class);
                startActivity(intent);
            }).show();
        });
    }

    @Override
    public void emailEmpty() {
        editTextEmail.requestFocus();
        editTextEmail.setError("Email is required");
    }

    @Override
    public void emailInvalid() {
        editTextEmail.requestFocus();
        editTextEmail.setError("Invalid Email");
    }

    @Override
    public void passwordEmpty() {
        editTextPass.requestFocus();
        editTextPass.setError("Password is required");
    }

    @Override
    public String getEmail() {
        return editTextEmail.getText().toString();
    }

    @Override
    public String getPass() {
        return editTextPass.getText().toString();
    }

    @Override
    public void writeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void continueCustomerHome(String id) {
        Intent intent =new Intent(this, CustomerHomeActivity.class);
        intent.putExtra(CUSTOMER_ID, id);
        startActivity(intent);
    }

    @Override
    public void continueStoreHome(String id) {
        Intent intent=new Intent(this, StoreOwnerHomeActivity.class);
        intent.putExtra(STORE_ID, id);
        startActivity(intent);
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
}