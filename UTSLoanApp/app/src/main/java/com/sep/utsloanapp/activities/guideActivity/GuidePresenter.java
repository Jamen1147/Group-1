package com.sep.utsloanapp.activities.guideActivity;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sep.utsloanapp.firebaseHelper.AuthHelper;
import com.sep.utsloanapp.firebaseHelper.DatabaseHelper;
import com.sep.utsloanapp.models.User;

public class GuidePresenter implements GuideContract.Presenter {

    private GuideContract.View mView;
    private Context mContext;
    private AuthHelper mAuthHelper;
    private DatabaseHelper mDatabaseHelper;

    public GuidePresenter(Context context, GuideContract.View view){
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

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
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
    public void logout() {
        mAuthHelper.logOutUser();
    }

    @Override
    public void checkAvailable() {
        if (mAuthHelper.getCurrentUser() != null) {
            mDatabaseHelper.retrieveUserData(new DatabaseHelper.OnGetDataListener() {
                @Override
                public void onStart() {

                }

                @Override
                public void onSuccessful(DataSnapshot dataSnapshot) {
                    String uid = mAuthHelper.getUid();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        User user = ds.getValue(User.class);
                        if (user != null) {
                            if (user.getUid().equals(uid)) {
                                int available = user.getAvailable();
                                mView.onGetAvailableSuccessful(available);
                            }
                        }
                    }
                }
                @Override
                public void onFailed(DatabaseError databaseError) {

                }
            });
        }else {
            mView.currentUserNull();
        }
    }
}
