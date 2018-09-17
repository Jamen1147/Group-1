package com.sep.utsloanapp.activities.studentPersonalDetailActivity;

import android.os.Bundle;

import com.sep.utsloanapp.activities.utils.BasePresenter;
import com.sep.utsloanapp.activities.utils.BaseView;
import com.sep.utsloanapp.models.Student;

public interface StudentPersonalDetailContract {

    interface View extends BaseView<Presenter>{
        //TODO: Use the 'student' object for changing the text views
        void setTextViews(Student student);
    }

    interface Presenter extends BasePresenter{
        void unwrapData(Bundle extra);
        void logout();
    }
}
