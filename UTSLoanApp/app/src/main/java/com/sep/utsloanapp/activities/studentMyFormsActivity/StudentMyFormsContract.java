package com.sep.utsloanapp.activities.studentMyFormsActivity;

import com.google.firebase.database.DatabaseError;
import com.sep.utsloanapp.activities.utils.BasePresenter;
import com.sep.utsloanapp.activities.utils.BaseView;
import com.sep.utsloanapp.models.Application;

import java.util.ArrayList;

public interface StudentMyFormsContract {

    interface View extends BaseView<Presenter>{
        void onGetFormStart();
        void onGetFormSuccessful(ArrayList<Application> applications);
        void onGetFormFailed(DatabaseError databaseError);
        void goToEditing(String jsonApplication);
        void goToDetailView(String jsonApplication);
    }

    interface Presenter extends BasePresenter{
        void logout();
        void getFormsData();
        void goToItemDetail(Application application);
    }

}
