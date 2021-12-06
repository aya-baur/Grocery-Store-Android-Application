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
    public void validate(int userType) {
        String email = view.getEmail();
        String password = view.getPass();

        view.showProgressBar();

        Pattern patternCheck = Pattern.compile("^\\S+@\\S+\\.\\S+$");
        Matcher matcherCheck = patternCheck.matcher(email);

        if(email.isEmpty()) {
            loginResponse( true,"");
            view.emailEmpty();
        }
        else if(!matcherCheck.matches()) {
            loginResponse( true,"");
            view.emailInvalid();
        }
        else if(password.isEmpty()) {
            loginResponse( true,"");
            view.passwordEmpty();
        }
        else {

            user.setEmail(email);
            user.setPassword(password);
            user.setUserType(userType);
            user.checkLoginExists(this, false);
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
