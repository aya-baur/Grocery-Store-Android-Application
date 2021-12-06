package com.example.grocery;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.grocery.Model.UserLogin;
import com.example.grocery.Presenter.LoginPresenter;
import com.example.grocery.View.MainActivity;

@RunWith(MockitoJUnitRunner.class)
public class PresenterUnitTest {

    @Mock
    MainActivity view;

    @Mock
    UserLogin userLogin;

    @Test
    public void validateEmailEmpty() {
        when(view.getEmail()).thenReturn("");
        when(view.getPass()).thenReturn("abc");

        LoginPresenter presenter = new LoginPresenter(userLogin, view);
        presenter.validate(0);

        verify(view).emailEmpty();
    }

    @Test
    public void validateInvalidEmail() {
        when(view.getEmail()).thenReturn("3234");
        when(view.getPass()).thenReturn("abc");

        LoginPresenter presenter = new LoginPresenter(userLogin, view);
        presenter.validate(0);

        verify(view).emailInvalid();
    }

    @Test
    public void validatePasswordEmpty() {
        when(view.getEmail()).thenReturn("1@gmail.com");
        when(view.getPass()).thenReturn("");

        LoginPresenter presenter = new LoginPresenter(userLogin, view);
        presenter.validate(0);

        verify(view).passwordEmpty();
    }

    @Test
    public void validateValidFields() {
        when(view.getEmail()).thenReturn("1@gmail.com");
        when(view.getPass()).thenReturn("abc");
        doNothing().when(userLogin).checkLoginExists(anyObject());

        LoginPresenter presenter = new LoginPresenter(userLogin, view);

        presenter.validate(0);

        InOrder order = inOrder(view, userLogin);
        order.verify(view).showProgressBar();
        order.verify(userLogin).checkLoginExists(presenter);
    }

    @Test
    public void loginResponseError() {
        when(view.getEmail()).thenReturn("1@gmail.com");
        when(view.getPass()).thenReturn("abc");

        LoginPresenter presenter = new LoginPresenter(userLogin, view);
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
        when(userLogin.getUserType()).thenReturn(UserLogin.CUSTOMER_TYPE);
        when(userLogin.getId()).thenReturn(123);

        LoginPresenter presenter = new LoginPresenter(userLogin, view);
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
        when(userLogin.getUserType()).thenReturn(UserLogin.STORE_TYPE);
        when(userLogin.getId()).thenReturn(123);

        LoginPresenter presenter = new LoginPresenter(userLogin, view);
        String message = "Success";
        presenter.loginResponse(false, message);

        InOrder order = inOrder(view);
        order.verify(view).hideProgressBar();
        order.verify(view).continueStoreHome(String.valueOf(userLogin.getId()));
    }
}