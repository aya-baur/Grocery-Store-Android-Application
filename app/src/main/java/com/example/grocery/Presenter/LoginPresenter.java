package com.example.grocery.Presenter;

import androidx.annotation.NonNull;

import com.example.grocery.Contract.LoginContract;
import com.example.grocery.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        if(email.isEmpty()) {
            loginResponse( true,"Email is required");
        }
        else if(password.isEmpty()) {
            loginResponse( true,"Password is required");
        }
        else {

            user.setEmail(email);
            user.setPassword(password);
            user.setUserType(userType);
            user.checkLoginExists(this);
        }
    }

    @Override
    public void loginResponse(boolean isError, String message) {
        view.hideProgressBar();
        if (isError) {
            view.writeToast(message);
        } else {
            if (user.getUserType() == User.CUSTOMER_TYPE) {
                view.continueCustomerHome(String.valueOf(user.getId()));
            } else {
                view.continueStoreHome(String.valueOf(user.getId()));
            }
        }
    }

}
