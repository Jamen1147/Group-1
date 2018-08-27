package com.sep.utsloanapp.activities.mainActivity.studentFragment;

import android.content.Context;

public class StudentPresenter implements StudentContract.Presenter{

    private StudentContract.View mView;
    private Context mContext;

    public StudentPresenter(Context context, StudentContract.View view){
        mContext = context;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
