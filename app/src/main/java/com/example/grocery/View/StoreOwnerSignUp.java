package com.example.grocery.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grocery.Contract.SignUpContract;
import com.example.grocery.CustomerHomeActivity;
import com.example.grocery.Model.UserLogin;
import com.example.grocery.Model.UserSignUp;
import com.example.grocery.Presenter.SignUpPresenter;
import com.example.grocery.R;
import com.example.grocery.StoreOwnerHomeActivity;
import com.google.android.material.button.MaterialButton;


public class StoreOwnerSignUp extends AppCompatActivity implements SignUpContract.View{
    public static final String CUSTOMER_ID = "CUSTOMER_ID";
    public static final String STORE_ID = "STORE_ID";

    EditText editTextEmailSignUpStoreOwner;
    EditText editTextNameSignUpStoreOwner;
    EditText editTextPassSignUpStoreOwner;

    MaterialButton btnSignUpStoreOwner;

    private SignUpPresenter signUpPresenter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_owner_sign_up);

        editTextEmailSignUpStoreOwner = findViewById(R.id.editTextEmailSignUpStoreOwner);
        editTextNameSignUpStoreOwner = findViewById(R.id.editTextNameSignUpStoreOwner);
        editTextPassSignUpStoreOwner = findViewById(R.id.editTextPassSignUpStoreOwner);

        btnSignUpStoreOwner = findViewById(R.id.btnSignUpStoreOwner);

        signUpPresenter=new SignUpPresenter(new UserSignUp(), this);

        btnSignUpStoreOwner.setOnClickListener(v -> signUpPresenter.validate(UserLogin.STORE_TYPE));

    }

    @Override
    public void emailEmpty() {
        editTextEmailSignUpStoreOwner.requestFocus();
        editTextEmailSignUpStoreOwner.setError("Email is required");
    }

    @Override
    public void emailInvalid() {
        editTextEmailSignUpStoreOwner.requestFocus();
        editTextEmailSignUpStoreOwner.setError("Invalid Email");
    }

    @Override
    public void nameEmpty() {
        editTextNameSignUpStoreOwner.requestFocus();
        editTextNameSignUpStoreOwner.setError("Name is required");
    }



    @Override
    public void passwordEmpty() {
        editTextPassSignUpStoreOwner.requestFocus();
        editTextPassSignUpStoreOwner.setError("Password is required");
    }

    @Override
    public String getEmail() {
        return editTextEmailSignUpStoreOwner.getText().toString();
    }

    @Override
    public String getName() {
        return editTextNameSignUpStoreOwner.getText().toString();
    }

    @Override
    public String getPass() {
        return editTextPassSignUpStoreOwner.getText().toString();
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
