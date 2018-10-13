package com.sep.utsloanapp.activities.staffAllFormsActivity;

import com.google.firebase.database.DatabaseError;
import com.sep.utsloanapp.activities.utils.BasePresenter;
import com.sep.utsloanapp.activities.utils.BaseView;
import com.sep.utsloanapp.models.Application;

import java.util.ArrayList;

public interface StaffAllFormsContract {

    interface View extends BaseView<Presenter>{
        /**
         * go to the applicationDetailActivity with the json file.
         * */
        void goApplicationDetailView(String jsonApplication);

        void onGetFormStart();
        /**
         * Simple show / hide of progressBar
         * Declare new adapter with this list of applications
         * set adapter for the recyclerView
         * */
        void onGetFormSuccessful(ArrayList<Application> applications);

        void onGetFormFailed(DatabaseError databaseError);
    }

    interface Presenter extends BasePresenter{
        /**
         * turn the application object into a json file
         * then call mView.goApplicationDetailView(String jsonFile)
         * */
        void goToItemDetail(Application application);

        /**
         * Find key words of every application item passed in
         * return application item name / ID / appID that is equal to the newText passed in
         * */
        ArrayList<Application> setList(ArrayList<Application> applications, String newText);

        void getFormsData();

        void logout();
    }

}
