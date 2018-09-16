package com.sep.utsloanapp.activities.createFormActivity;

import com.google.firebase.database.DatabaseError;
import com.sep.utsloanapp.activities.utils.BasePresenter;
import com.sep.utsloanapp.activities.utils.BaseView;
import com.sep.utsloanapp.models.Application;

public interface CreateFormContract {

    interface View extends BaseView<Presenter>{
        void onLoadNameStart();
        void onLoadNameSuccessful(String name, String id);
        void onLoadNameFailed(DatabaseError databaseError);
        void onFormInfoInvalid(String errorMsg);
        void onSaveFinished();
    }

    interface Presenter extends BasePresenter{
        void logout();
        void saveForm(Application application);
        void submitForm(Application application);
        void loadNameID();
        void loadFormContent();
    }

}
