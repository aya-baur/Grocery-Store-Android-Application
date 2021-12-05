package com.example.grocery.Presenter;

import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.grocery.Contract.LoginContract;
import com.example.grocery.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginPresenter implements LoginContract.Presenter
{
    private LoginContract.Model user;
    private LoginContract.View view;
    public LoginPresenter(LoginContract.Model user, LoginContract.View view) {
        this.user=user;
        this.view=view;
    }

    @Override
    public void validate(int userType, boolean signUp) {
        String email = view.getEmail();
        String name = signUp? view.getName(): "";
        String password = view.getPass();

        EditText textBoxEmail = view.getEditTextEmail();
        EditText textBoxName = signUp? view.getEditTextName(): null;
        EditText textBoxPassword = view.getEditTextPassword();

        view.showProgressBar();

        Pattern patternCheck = signUp? Pattern.compile("^\\S+@\\S+\\.\\S+$"): null;
        Matcher matcherCheck = signUp? patternCheck.matcher(email): null;

        if(email.isEmpty()) {
            loginResponse( true,"");
            textBoxEmail.requestFocus();
            textBoxEmail.setError("Email is required");
        }
        else if(signUp && !matcherCheck.matches()) {
            loginResponse( true,"");
            textBoxEmail.requestFocus();
            textBoxEmail.setError("Invalid Email");
        }
        else if(signUp && name.isEmpty()) {
            loginResponse( true,"");
            textBoxName.requestFocus();
            textBoxName.setError("Name is required");
        }
        else if(password.isEmpty()) {
            loginResponse( true,"");
            textBoxPassword.requestFocus();
            textBoxPassword.setError("Password is required");
        }
        else {

            user.setEmail(email);
            if (signUp) user.setName(name);
            user.setPassword(password);
            user.setUserType(userType);
            user.checkLoginExists(this, signUp);
        }
    }

    @Override
    public void loginResponse(boolean isError, String message) {
        view.hideProgressBar();
        if (isError) {
            if (!message.isEmpty()) {
                view.writeToast(message);
            }
        } else {
            if (user.getUserType() == User.CUSTOMER_TYPE) {
                view.continueCustomerHome(String.valueOf(user.getId()));
            } else {
                view.continueStoreHome(String.valueOf(user.getId()));
            }
        }
    }

}
