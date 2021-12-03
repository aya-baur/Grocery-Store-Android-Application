package com.example.grocery.Presenter;

import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.grocery.CustomerHomeActivity;
import com.example.grocery.MainActivity;
import com.example.grocery.Model.User;
import com.example.grocery.StoreOwnerHomeActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginPresenter
{

    User user;
    View view;
    public  LoginPresenter(View view)
    {
        this.user=new User();
        this.view=view;
    }

    public  void  validate(User user)
    {
        if(user.getEmail().isEmpty())
        {
            view.onValidateResponse(false,"Email is Required");
        }
        else
        if(user.getPassword().isEmpty())
        {
            view.onValidateResponse(false,"Password is Required");
        }
        else
        {
            view.onValidateResponse(true,"Success");
        }


    }
    public  void  login(User user,String DB_REF)
    {

        view.showProgressBar();
        FirebaseDatabase.getInstance().getReference(DB_REF).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                view.hideProgressBar();

                if(dataSnapshot.exists())
                {

                    for(DataSnapshot child:dataSnapshot.getChildren())
                    {

                        User user1=new User();
                        String email=child.child("email").getValue().toString();
                        String password=child.child("password").getValue().toString();
                        String id=child.child("id").getValue().toString();
                        user1.setEmail(email);
                        user1.setPassword(password);
                        user1.setId(id);

                        if(user.getEmail().trim().equalsIgnoreCase(email.trim()) && user.getPassword().equals(password))
                        {


                            if(DB_REF.equals("customers"))
                            {
                                view.onLoginResponse(false,"Success",1,user1);
                                return;
                            }
                            else
                            {

                                view.onLoginResponse(false,"Success",2,user1);
                            }
                            return;
                        }

                    }

                }


                view.onLoginResponse(true,"Email Or Password Is Wrong",-1,null);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                view.hideProgressBar();
                view.onLoginResponse(true,"Error",-1,null);
            }
        });


    }


    public interface View{

        void onValidateResponse(boolean isValid,String message);

        //logedInAs=-1  MEANS ERROR | logedInAs=1 Means Loged in as customer |   logedInAs=2 Means loged in as seller
        void onLoginResponse(boolean isError,String message,int logedInAs,User user); //isError to show if there is any error

        void showProgressBar();
        void hideProgressBar();

    }
}
