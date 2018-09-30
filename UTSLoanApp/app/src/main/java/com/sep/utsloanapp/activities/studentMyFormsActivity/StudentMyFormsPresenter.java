package com.sep.utsloanapp.activities.studentMyFormsActivity;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.sep.utsloanapp.activities.createFormActivity.CreateFormActivity;
import com.sep.utsloanapp.activities.utils.Constant;
import com.sep.utsloanapp.firebaseHelper.AuthHelper;
import com.sep.utsloanapp.firebaseHelper.DatabaseHelper;
import com.sep.utsloanapp.models.Application;

import java.util.ArrayList;

public class StudentMyFormsPresenter implements StudentMyFormsContract.Presenter {

    private Context mContext;
    private StudentMyFormsContract.View mView;
    private AuthHelper mAuthHelper;
    private DatabaseHelper mDatabaseHelper;

    private ArrayList<Application> mApplications = new ArrayList<>();

    public StudentMyFormsPresenter(Context context, StudentMyFormsContract.View view){
        mContext = context;
        mView = view;
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
    public void getFormsData(final int type) {
        mDatabaseHelper.retrieveApplicationData(new DatabaseHelper.OnGetDataListener() {
            @Override
            public void onStart() {
                mView.onGetFormStart();
            }

            @Override
            public void onSuccessful(DataSnapshot dataSnapshot) {
                String uid = mAuthHelper.getUid();
                mApplications.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Application application = ds.getValue(Application.class);
                    if (application != null) {
                        if (type == Constant.STAFF_VAL) {
                            //staff
                            if (application.getStaffUid().equals(uid))
                                mApplications.add(application);
                        }else {
                            //student
                            if (application.getStudentUid().equals(uid))
                                mApplications.add(application);
                        }
                    }
                    mView.onGetFormSuccessful(mApplications);
                }
            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                mView.onGetFormFailed(databaseError);
            }
        });
    }


    @Override
    public void goToItemDetail(Application application) {
        String jsonApplication = new Gson().toJson(application);
        if (application.getStatus().equals(Constant.FORM_STATUS_SAVED))
            mView.goToEditing(jsonApplication);
        else
            mView.goToDetailView(jsonApplication);
    }
}
