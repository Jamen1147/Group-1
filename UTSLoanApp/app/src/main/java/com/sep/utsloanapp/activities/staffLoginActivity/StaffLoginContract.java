package com.sep.utsloanapp.activities.staffLoginActivity;

import com.google.firebase.database.DatabaseError;
import com.sep.utsloanapp.activities.utils.BasePresenter;
import com.sep.utsloanapp.activities.utils.BaseView;

public interface StaffLoginContract {
    interface View extends BaseView<Presenter> {
        //This is called if login input invalid, set error msg
        //to the corresponding EditText.
        void loginInputInvalid(String errorMessage);

        //This is called when login starts, show the progressbar.
        void onLoginStart();

        //This is called once login successful, hide the progressbar and send the user
        //to the next activity.
        void onLoginSuccessful();

        //This is called once login failed, hide the progressbar and show the err msg.
        void onLoginFailed();

        //wrong type of user, stop it.
        void onGetDataSuccessfulUserStudent();

        //Right type of user, go to the next activity.
        void onGetDataSuccessfulUserStaff();

        //print trace
        void onGetDataFailed(DatabaseError databaseError);

        //first time login, no type to check, write type = staff
        void onGetFirstTimeUser();
    }

    interface Presenter extends BasePresenter {
        //This is called when the user clicks the login button.
        //Tell the auth to login the user and handel states after checking the input validation.
        void doLogin(String staffId, String password);

        //save user info using firebase database
        void doSaveUser(String staffId, String password);

        //check the user type : student or staff
        void checkType();
    }
}
