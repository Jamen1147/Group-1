package com.sep.utsloanapp.activities.studentMyFormsActivity;

import com.google.firebase.database.DatabaseError;
import com.sep.utsloanapp.activities.utils.BasePresenter;
import com.sep.utsloanapp.activities.utils.BaseView;
import com.sep.utsloanapp.models.Application;

import java.util.ArrayList;

public interface StudentMyFormsContract {

    interface View extends BaseView<Presenter>{
        void onGetFormStart();

        /**
         * check type, if student, call get form successful and pass the student's form
         if staff, pass staff form
         * */
        void onGetFormSuccessful(ArrayList<Application> applications);

        void onGetFormFailed(DatabaseError databaseError);

        /**
         * Go to createFormActivity and put the JSON data passed in as extra
         * */
        void goToEditing(String jsonApplication);

        /**
         * Go to formDetailView and put the JSON data passed in as extra
         * */
        void goToDetailView(String jsonApplication);
    }

    interface Presenter extends BasePresenter{
        void logout();

        /**
         * check type, if student, call get form successful and pass the student's form
         if staff, pass staff form
         * */
        void getFormsData(int type);

        /**
         * turn application into json and call goToEdit() or goToDetail() based on the status..
         if submitted go edit, otherwise go detail
         * */
        void goToItemDetail(Application application);
    }

}
