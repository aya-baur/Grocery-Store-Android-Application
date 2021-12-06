package com.example.grocery.Contract;

public interface SignUpContract {
    interface Presenter {
        public void validate(int userType);
        public void loginResponse(boolean isError, String message);
    }

    interface Model {
        public void checkLoginExists(SignUpContract.Presenter presenter);

        public int getId();
        public void generateId();

        public String getEmail();
        public void setEmail(String email);

        //Use for SignUp
        public String getName();
        public void setName(String name);

        public String getPassword();
        public void setPassword(String password);
        //userType=-1  MEANS ERROR | userType=0 Means Logged in as customer |   userType=1 Means logged in as seller
        public int getUserType();
        public void setUserType(int userType);
    }

    interface View {
        public String getEmail();

        //Use for SignUp
        public String getName();

        public String getPass();

        public void writeToast(String message);
        public void continueCustomerHome(String id);
        public void continueStoreHome(String id);

        public void showProgressBar();
        public void hideProgressBar();

        //For focus
        public void emailEmpty();
        public void emailInvalid();
        public void nameEmpty();
        public void passwordEmpty();
    }
}
