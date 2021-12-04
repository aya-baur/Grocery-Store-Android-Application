package com.example.grocery;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.grocery.Model.User;
import com.example.grocery.Presenter.LoginPresenter;
import com.example.grocery.View.MainActivity;

@RunWith(MockitoJUnitRunner.class)
public class PresenterUnitTest {

    @Mock
    MainActivity view;

    @Mock
    User user;

    @Test
    public void testPresenter(){

        /*** stubbing ***/
//        when(view.getEmail()).thenReturn("");
//
//        LoginPresenter presenter = new LoginPresenter(user, view);
//        presenter.checkUsername();

        /*** verifying a method call with a specific value ***/
//        verify(view).writeToast("user found");

        /*** verifying a method call with any value ***/
        //verify(view).displayMessage(anyString());

        /*** verifying a certain number of method calls ***/
        //verify(view, times(2)).displayMessage("user found");

        /*** Argument captors ***/
        //ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        //verify(view).displayMessage(captor.capture());
        //assertEquals(captor.getValue(), "user found");

        /*** Verifying order ***/
        //InOrder order = inOrder(model, view);
        //order.verify(model).userExists("abc");
        //order.verify(view).displayMessage("user found");
    }

    @Test
    public void validateEmailEmpty() {
        when(view.getEmail()).thenReturn("");
        when(view.getPass()).thenReturn("abc");

        LoginPresenter presenter = new LoginPresenter(user, view);
        presenter.validate(0);

        InOrder order = inOrder(view);
        order.verify(view).showProgressBar();
        order.verify(view).hideProgressBar();
        order.verify(view).writeToast("Email is required");
    }

    @Test
    public void validatePasswordEmpty() {
        when(view.getEmail()).thenReturn("1@gmail.com");
        when(view.getPass()).thenReturn("");

        LoginPresenter presenter = new LoginPresenter(user, view);
        presenter.validate(0);

        InOrder order = inOrder(view);
        order.verify(view).showProgressBar();
        order.verify(view).hideProgressBar();
        order.verify(view).writeToast("Password is required");
    }

    @Test
    public void validateNonEmptyFields() {
        when(view.getEmail()).thenReturn("1@gmail.com");
        when(view.getPass()).thenReturn("abc");
        doNothing().when(user).checkLoginExists(anyObject());

        LoginPresenter presenter = new LoginPresenter(user, view);

        presenter.validate(0);

        InOrder order = inOrder(view, user);
        order.verify(view).showProgressBar();
        order.verify(user).checkLoginExists(presenter);
    }

    @Test
    public void loginResponseError() {
        when(view.getEmail()).thenReturn("1@gmail.com");
        when(view.getPass()).thenReturn("abc");

        LoginPresenter presenter = new LoginPresenter(user, view);
        String message = "Email or Password Incorrect";
        presenter.loginResponse(true, message);

        InOrder order = inOrder(view);
        order.verify(view).hideProgressBar();
        order.verify(view).writeToast(message);
    }

    @Test
    public void loginResponseCustomerLogin() {
        when(view.getEmail()).thenReturn("1@gmail.com");
        when(view.getPass()).thenReturn("abc");
        when(user.getUserType()).thenReturn(User.CUSTOMER_TYPE);
        when(user.getId()).thenReturn(123);

        LoginPresenter presenter = new LoginPresenter(user, view);
        String message = "Success";
        presenter.loginResponse(false, message);

        InOrder order = inOrder(view);
        order.verify(view).hideProgressBar();
        order.verify(view).continueCustomerHome("123");
    }

    @Test
    public void loginResponseStoreLogin() {
        when(view.getEmail()).thenReturn("1@gmail.com");
        when(view.getPass()).thenReturn("abc");
        when(user.getUserType()).thenReturn(User.STORE_TYPE);
        when(user.getId()).thenReturn(123);

        LoginPresenter presenter = new LoginPresenter(user, view);
        String message = "Success";
        presenter.loginResponse(false, message);

        InOrder order = inOrder(view);
        order.verify(view).hideProgressBar();
        order.verify(view).continueStoreHome(String.valueOf(user.getId()));
    }
}