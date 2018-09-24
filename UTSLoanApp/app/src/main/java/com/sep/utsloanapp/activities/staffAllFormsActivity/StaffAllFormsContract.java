package com.sep.utsloanapp.activities.staffAllFormsActivity;

import com.google.firebase.database.DatabaseError;
import com.sep.utsloanapp.activities.utils.BasePresenter;
import com.sep.utsloanapp.activities.utils.BaseView;
import com.sep.utsloanapp.models.Application;

import java.util.ArrayList;

public interface StaffAllFormsContract {

    interface View extends BaseView<Presenter>{
        //go to the applicationDetailActivity with the json file.
        void goApplicationDetailView(String jsonApplication);

        void onGetFormStart();
        void onGetFormSuccessful(ArrayList<Application> applications);
        void onGetFormFailed(DatabaseError databaseError);
    }

    interface Presenter extends BasePresenter{
        //turn the application object into a json file
        // then call mView.goApplicationDetailView(String jsonFile)
        void goToItemDetail(Application application);

        void getFormsData();

        void logout();

        ArrayList<Application> setList(ArrayList<Application> applications, String newText);
    }

}
