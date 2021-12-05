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
import com.example.grocery.R;
import com.example.grocery.StoreOwnerHomeActivity;
import com.google.android.material.button.MaterialButton;


public class CustomerSignUp extends AppCompatActivity implements LoginContract.View{
    public static final String CUSTOMER_ID = "CUSTOMER_ID";
    public static final String STORE_ID = "STORE_ID";

    MaterialButton btnSignUpCustomer;

    private LoginPresenter loginPresenter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_sign_up);

        btnSignUpCustomer = findViewById(R.id.btnSignUpCustomer);
        loginPresenter=new LoginPresenter(new User(), this);

        btnSignUpCustomer.setOnClickListener(v -> loginPresenter.validate(User.CUSTOMER_TYPE, true));

    }

    @Override
    public EditText getEditTextEmail() {
        return findViewById(R.id.editTextEmailSignUpCustomer);
    }

    @Override
    public EditText getEditTextName() {
        return findViewById(R.id.editTextNameSignUpCustomer);
    }

    @Override
    public EditText getEditTextPassword() {
        return findViewById(R.id.editTextPassSignUpCustomer);
    }

    @Override
    public String getEmail() {
        return getEditTextEmail().getText().toString();
    }

    @Override
    public String getName() {
        return getEditTextName().getText().toString();
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
