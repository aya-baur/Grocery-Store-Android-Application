package com.example.grocery.Contract;

import android.content.Intent;
import android.widget.Toast;

import com.example.grocery.CustomerHomeActivity;
import com.example.grocery.Model.User;

public interface LoginContract {

    public interface Model{
        public void checkLoginExists(LoginContract.Presenter presenter);

        public int getId();
        public void generateId();
        public String getEmail();
        public void setEmail(String email);
        public String getPassword();
        public void setPassword(String password);
        //userType=-1  MEANS ERROR | userType=0 Means Logged in as customer |   userType=1 Means logged in as seller
        public int getUserType();
        public void setUserType(int userType);
    }

    public interface View{
        public String getEmail();
        public String getPass();

        public void writeToast(String message);
        public void continueCustomerHome(String id);
        public void continueStoreHome(String id);

        public void showProgressBar();
        public void hideProgressBar();
    }

    public interface Presenter{
        public void validate(int userType);
        public void loginResponse(boolean isError, String message);
    }
}
