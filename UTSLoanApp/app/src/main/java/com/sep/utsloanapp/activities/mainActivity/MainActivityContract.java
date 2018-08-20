package com.sep.utsloanapp.activities.mainActivity;

import android.os.Bundle;

import com.google.firebase.database.DatabaseError;
import com.sep.utsloanapp.activities.utils.BasePresenter;
import com.sep.utsloanapp.activities.utils.BaseView;

public interface MainActivityContract {

    interface View extends BaseView<Presenter>{
        //take the user to student page
        void showStudent();

        //take the user to staff page
        void showStaff();

        //show err msg and then logout the user
        void onGetDataFailed(DatabaseError databaseError);
    }

    interface Presenter extends BasePresenter{
        //check type from extra, means user just login
        void checkType(Bundle extra);

        //check type from the database, means user has login before
        void checkType();

        //logout the user
        void logout();
    }
}
