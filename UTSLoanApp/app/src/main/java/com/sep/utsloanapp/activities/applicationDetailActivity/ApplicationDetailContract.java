package com.sep.utsloanapp.activities.applicationDetailActivity;

import android.os.Bundle;

import com.google.firebase.database.DatabaseError;
import com.sep.utsloanapp.activities.utils.BasePresenter;
import com.sep.utsloanapp.activities.utils.BaseView;
import com.sep.utsloanapp.models.Application;
import com.sep.utsloanapp.models.Student;

public interface ApplicationDetailContract {

    interface View extends BaseView<Presenter>{
        void setContent(Application application, int userType);

        void onGetStudentDataStart();
        void onGetStudentDataSuccessful(Student student);
        void onGetStudentDataFailed(DatabaseError databaseError);

        void finishViewWithMsg(String msg);
    }

    interface Presenter extends BasePresenter{
        void logout();
        void unwrapApplicationData(Bundle extra);
        void getStudentInfo(String uid);
        void cancelApplicationSubmission(Application application);

        //staff clicked start review, set staffUid as the current uid, set status, save to database
        //then tell the view to finish
        void startReviewForm(Application application);

        void startDeclareForm(Application application);
    }
}