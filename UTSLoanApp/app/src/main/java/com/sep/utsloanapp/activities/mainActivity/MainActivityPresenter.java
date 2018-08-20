package com.sep.utsloanapp.activities.mainActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sep.utsloanapp.R;
import com.sep.utsloanapp.activities.staffLoginActivity.StaffLoginActivity;
import com.sep.utsloanapp.firebaseHelper.AuthHelper;
import com.sep.utsloanapp.firebaseHelper.DatabaseHelper;
import com.sep.utsloanapp.models.User;

public class MainActivityPresenter implements MainActivityContract.Presenter{

    private MainActivityContract.View mView;
    private Context mContext;
    private AuthHelper mAuthHelper;
    private DatabaseHelper mDatabaseHelper;

    public MainActivityPresenter(Context context, MainActivityContract.View view){
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

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child(DatabaseHelper.KEY_DB_USER);
        mDatabaseHelper = new DatabaseHelper(reference);
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
    public void checkType(Bundle extra) {
        if (extra != null){
            int type = extra.getInt(StaffLoginActivity.TYPE_KEY);
            if (type == StaffLoginActivity.STAFF_VAL){
                //staff
                mView.showStaff();
            }else {
                //student
                mView.showStudent();
            }
        }
    }

    @Override
    public void checkType() {
        final String[] userType = {""};
        if (mAuthHelper.getCurrentUser() != null) {
            mDatabaseHelper.retrieveData(new DatabaseHelper.OnGetDataListener() {
                @Override
                public void onStart() { }

                @Override
                public void onSuccessful(DataSnapshot dataSnapshot) {
                    String uid = mAuthHelper.getUid();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        User user = ds.getValue(User.class);
                        if (user != null) {
                            String uidd = user.getUid();
                            if (user.getUid().equals(uid)) {
                                userType[0] = user.getUserType();
                                if (userType[0].equals(mContext.getString(R.string.student))){
                                    mView.showStudent();
                                    return;
                                }else {
                                    mView.showStaff();
                                    return;
                                }
                            }else {
                                //not the one looking for
                            }
                        }else {
                        }
                    }
                }
                @Override
                public void onFailed(DatabaseError databaseError) {
                    mView.onGetDataFailed(databaseError);
                }
            });
        }else {
            mView.currentUserNull();
        }
    }

    @Override
    public void logout() {
        mAuthHelper.logOutUser();
    }
}
