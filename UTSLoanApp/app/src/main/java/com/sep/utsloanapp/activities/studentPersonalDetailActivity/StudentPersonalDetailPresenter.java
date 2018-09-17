package com.sep.utsloanapp.activities.studentPersonalDetailActivity;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.sep.utsloanapp.activities.createFormActivity.CreateFormActivity;
import com.sep.utsloanapp.activities.utils.Constant;
import com.sep.utsloanapp.firebaseHelper.AuthHelper;
import com.sep.utsloanapp.models.Student;

public class StudentPersonalDetailPresenter implements StudentPersonalDetailContract.Presenter {

    private Context mContext;
    private StudentPersonalDetailContract.View mView;
    private AuthHelper mAuthHelper;

    public StudentPersonalDetailPresenter(Context context, StudentPersonalDetailContract.View view){
        mView = view;
        mContext = context;
        mView.setPresenter(this);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        mAuthHelper = new AuthHelper(firebaseAuth);

        mAuthHelper.listenAuthState(new AuthHelper.AuthStateListener() {
            @Override
            public void onAuthStateChanged() {
                mView.currentUserNull();
            }
        });
    }

    @Override
    public void unwrapData(Bundle extra) {
        String jsonStudent = extra.getString(Constant.JSON_STUDENT_KEY);
        Student student = new Gson().fromJson(jsonStudent, Student.class);
        mView.setTextViews(student);
    }

    @Override
    public void start() {
        mAuthHelper.addAuthListener();
    }

    @Override
    public void stop() {
        mAuthHelper.removeAuthListener();
    }

    @Override
    public void logout() {
        mAuthHelper.logOutUser();
    }
}
