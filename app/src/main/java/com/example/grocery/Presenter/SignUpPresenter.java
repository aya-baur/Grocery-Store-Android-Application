package com.example.grocery.Presenter;

import com.example.grocery.Contract.SignUpContract;
import com.example.grocery.Model.UserLogin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpPresenter implements SignUpContract.Presenter
{
    private SignUpContract.Model user;
    private SignUpContract.View view;
    public SignUpPresenter(SignUpContract.Model user, SignUpContract.View view) {
        this.user=user;
        this.view=view;
    }

    @Override
    public void validate(int userType) {
        String email = view.getEmail();
        String name = view.getName();
        String password = view.getPass();

        Pattern patternCheck = Pattern.compile("^\\S+@\\S+\\.\\S+$");
        Matcher matcherCheck = patternCheck.matcher(email);

        if(email.isEmpty()) {
            view.emailEmpty();
        }
        else if(!matcherCheck.matches()) {
            view.emailInvalid();
        }
        else if(name.isEmpty()) {
            view.nameEmpty();
        }
        else if(password.isEmpty()) {
            view.passwordEmpty();
        }
        else {
            view.showProgressBar();
            user.setEmail(email);
            user.setName(name);
            user.setPassword(password);
            user.setUserType(userType);
            user.checkLoginExists(this);
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
            if (user.getUserType() == UserLogin.CUSTOMER_TYPE) {
                view.continueCustomerHome(String.valueOf(user.getId()));
            } else {
                view.continueStoreHome(String.valueOf(user.getId()));
            }
        }
    }

}
