package com.example.grocery.Model;

import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.grocery.Contract.LoginContract;
import com.example.grocery.Customer;
import com.example.grocery.Presenter.LoginPresenter;
import com.example.grocery.Store;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class User implements LoginContract.Model{
    public static final String STORE_DB = "stores";
    public static final String CUSTOMER_DB = "customers";
    public static final int CUSTOMER_TYPE = 0;
    public static final int STORE_TYPE = 1;

    private int id;
    private String email;
    private String name;
    private String password;
    private int userType;

    public User() {}
    public User(String email, String password, int userType) {
        this.email = email;
        this.password = password;
        this.userType = userType;
        generateId();
    }
    // For use in Sign Up
    public User(String email,String name, String password, int userType) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.userType = userType;
        generateId();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void generateId() {
        if (userType == 0) {
            this.id = (new Customer("", email, password)).getId();
        } else if (userType == 1) {
            this.id = (new Store("", email, password)).getId();
        }
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
        generateId();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String email) {
        this.name = name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int getUserType() {
        return userType;
    }

    @Override
    public void setUserType(int userType) {
        this.userType = userType;
    }

    @Override
    public void checkLoginExists(LoginContract.Presenter presenter, boolean signUp) {
        String refPath = userType == 0? User.CUSTOMER_DB : User.STORE_DB;
        User user = this;

        DatabaseReference mDatabase;

        mDatabase = FirebaseDatabase.getInstance().getReference(refPath);

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.child(String.valueOf(user.getId())).exists()) {
                    if (!signUp) {
                        if (user.getPassword().equals(dataSnapshot.child(String.valueOf(user.getId())).child("password").getValue(String.class))) {
                            presenter.loginResponse(false, "Success");
                        }
                    } else {
                        presenter.loginResponse(true, "User Already Exists");
                    }
                } else {
                    if (!signUp) {
                        presenter.loginResponse(true,"Email Or Password Is Wrong");
                    } else {
                        Store newAccount = new Store(user.name, user.email, user.password);

                        mDatabase.child(String.valueOf(user.getId())).setValue(newAccount);

                        presenter.loginResponse(false, "Created");
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                presenter.loginResponse(true,"Error");
            }
        });
    }
}
