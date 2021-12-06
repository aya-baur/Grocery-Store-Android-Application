package com.example.grocery.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grocery.Contract.LoginContract;
import com.example.grocery.CustomerHomeActivity;
import com.example.grocery.Model.User;
import com.example.grocery.Presenter.LoginPresenter;
import com.example.grocery.Presenter.SignUpPresenter;
import com.example.grocery.R;
import com.example.grocery.StoreOwnerHomeActivity;
import com.google.android.material.button.MaterialButton;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CustomerSignUp extends AppCompatActivity implements LoginContract.View{
    public static final String CUSTOMER_ID = "CUSTOMER_ID";
    public static final String STORE_ID = "STORE_ID";

    EditText editTextEmailSignUpCustomer;
    EditText editTextNameSignUpCustomer;
    EditText editTextPassSignUpCustomer;

    MaterialButton btnSignUpCustomer;

    private SignUpPresenter signUpPresenter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_sign_up);

        editTextEmailSignUpCustomer = findViewById(R.id.editTextEmailSignUpCustomer);
        editTextNameSignUpCustomer = findViewById(R.id.editTextNameSignUpCustomer);
        editTextPassSignUpCustomer = findViewById(R.id.editTextPassSignUpCustomer);

        btnSignUpCustomer = findViewById(R.id.btnSignUpCustomer);
        signUpPresenter=new SignUpPresenter(new User(), this);

        btnSignUpCustomer.setOnClickListener(v -> signUpPresenter.validate(User.CUSTOMER_TYPE));

    }

    @Override
    public void emailEmpty() {
        editTextEmailSignUpCustomer.requestFocus();
        editTextEmailSignUpCustomer.setError("Email is required");
    }

    @Override
    public void emailInvalid() {
        editTextEmailSignUpCustomer.requestFocus();
        editTextEmailSignUpCustomer.setError("Invalid Email");
    }

    @Override
    public void nameEmpty() {
        editTextNameSignUpCustomer.requestFocus();
        editTextNameSignUpCustomer.setError("Name is required");
    }

    @Override
    public void nameInvalid() {

    }

    @Override
    public void passwordEmpty() {
        editTextPassSignUpCustomer.requestFocus();
        editTextPassSignUpCustomer.setError("Password is required");
    }

    @Override
    public String getEmail() {
        return editTextEmailSignUpCustomer.getText().toString();
    }

    @Override
    public String getName() {
        return editTextNameSignUpCustomer.getText().toString();
    }

    @Override
    public String getPass() {
        return editTextPassSignUpCustomer.getText().toString();
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
        progressDialog.setMessage("Signing Up");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void hideProgressBar() {
        if(progressDialog.isShowing())
            progressDialog.dismiss();
    }

}
