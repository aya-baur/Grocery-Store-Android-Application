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
import com.example.grocery.Model.User;
import com.example.grocery.Presenter.LoginPresenter;
import com.example.grocery.R;
import com.example.grocery.StoreOwnerHomeActivity;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements LoginContract.View {
    public static final String CUSTOMER_ID = "CUSTOMER_ID";
    public static final String STORE_ID = "STORE_ID";

    private LoginPresenter loginPresenter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialButton btnLoginAsCustomer = findViewById(R.id.btnLoginAsCustomer);
        MaterialButton btnLoginAsSeller = findViewById(R.id.btnLoginAsSeller);
        TextView txtCreateAccount = findViewById(R.id.txtCreateAccount);

        loginPresenter=new LoginPresenter(new User(), this);


        btnLoginAsCustomer.setOnClickListener(view -> loginPresenter.validate(User.CUSTOMER_TYPE, false));

        btnLoginAsSeller.setOnClickListener(view -> loginPresenter.validate(User.STORE_TYPE, false));

        txtCreateAccount.setOnClickListener(view -> {
            new AlertDialog.Builder(this).setPositiveButton("Create Account As Customer", (DialogInterface dialog, int which) -> {
                Intent intent = new Intent(MainActivity.this, CustomerSignUp.class);
                startActivity(intent);
            }).setNeutralButton("Create Account As Store Owner ", (DialogInterface dialog, int which) -> {
                Intent intent = new Intent(MainActivity.this, StoreOwnerSignUp.class);
                startActivity(intent);
            }).show();
        });
    }

    @Override
    public EditText getEditTextEmail() {
        return findViewById(R.id.editTextEmail);
    }

    @Override
    public EditText getEditTextName() {
        return null;
    }

    @Override
    public EditText getEditTextPassword() {
        return findViewById(R.id.editTextPass);
    }

    @Override
    public String getEmail() {
        return getEditTextEmail().getText().toString();
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getPass() {
        return getEditTextPassword().getText().toString();
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