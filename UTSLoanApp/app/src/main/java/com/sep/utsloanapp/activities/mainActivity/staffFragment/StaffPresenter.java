package com.sep.utsloanapp.activities.mainActivity.staffFragment;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.sep.utsloanapp.firebaseHelper.AuthHelper;

public class StaffPresenter implements StaffContract.Presenter{

    private StaffContract.View mView;
    private Context mContext;
    private AuthHelper mAuthHelper;

    public StaffPresenter(Context context, StaffContract.View view){

        mContext = context;
        mView = view;

    }

    @Override
    public void logout() {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
