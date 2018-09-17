package com.sep.utsloanapp.activities.createFormActivity;

import android.os.Bundle;

import com.google.firebase.database.DatabaseError;
import com.sep.utsloanapp.activities.utils.BasePresenter;
import com.sep.utsloanapp.activities.utils.BaseView;
import com.sep.utsloanapp.models.Application;
import com.sep.utsloanapp.models.Student;

public interface CreateFormContract {

    interface View extends BaseView<Presenter>{
        void onLoadNameStart();
        void onLoadNameSuccessful(String name, String id, Student student);
        void onLoadNameFailed(DatabaseError databaseError);
        void onFormInfoInvalid(String errorMsg);
        void onSaveFinished(String msg);

        //Use the below data to set the content
        //indexes - [0] for usage drop down index - [1] for loanPeriod - [2] for repayment
        void setFormContent(Application application, int[] indexes);
    }

    interface Presenter extends BasePresenter{
        void logout();
        void saveForm(Application application);
        void submitForm(Application application);
        void loadNameID();
        void loadFormContent(Bundle extra);
    }

}
