package com.sep.utsloanapp.activities.createFormActivity;

import android.os.Bundle;

import com.google.firebase.database.DatabaseError;
import com.sep.utsloanapp.activities.utils.BasePresenter;
import com.sep.utsloanapp.activities.utils.BaseView;
import com.sep.utsloanapp.models.Application;
import com.sep.utsloanapp.models.Student;

public interface CreateFormContract {

    interface View extends BaseView<Presenter>{
        /**
         * Show the progressBar
         * */
        void onLoadNameStart();
        /**
         * hide progress bar and show the text box with the value passed in
         * set mStudent = student
         * */
        void onLoadNameSuccessful(String name, String id, Student student);
        /**
         * print error msg
         * */
        void onLoadNameFailed(DatabaseError databaseError);
        /**
         * set error msg to corresponding text box
         * */
        void onFormInfoInvalid(String errorMsg);
        /**
         * show the msg passed in and go back to the previous activity
         * */
        void onSaveFinished(String msg);

        /**
         * Use the below data to set the content
         indexes - [0] for usage drop down index - [1] for loanPeriod - [2] for repayment
         * */
        void setFormContent(Application application, int[] indexes);
    }

    interface Presenter extends BasePresenter{
        /**
         * Call the authHelper.logoutUser()
         * */
        void logout();

        /**
         * Call dbHelper.saveObject(application) to save the application
         then update the user's availability, set to 'disable'
         * */
        void saveForm(Application application);

        /**
         * Set timeSubmitted - current time, set application status to 'Submitted'
         check form detail validation, if not valid, call view.formInfoInvalid(errorMsg)
         Call dbHelper.saveObject(application) to save the application
         then update the user's availability, set to 'disable'
         * */
        void submitForm(Application application);

        /**
         * Call dbHelper.retrieveStudentData(listener)
         * Listener on start call the view.loadNameStart()
         * Listener on failed call the view.loadNameFailed()
         * on successful call the view.ladNameSuccessful(Student) and pass in the student object
         * */
        void loadNameID();

        /**
         * Call the authHelper.logoutUser()
         * */
        void loadFormContent(Bundle extra);
    }

}
