package com.sep.utsloanapp.activities.staffAllFormsActivity;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.sep.utsloanapp.activities.utils.Constant;
import com.sep.utsloanapp.firebaseHelper.AuthHelper;
import com.sep.utsloanapp.firebaseHelper.DatabaseHelper;
import com.sep.utsloanapp.models.Application;

import java.util.ArrayList;

public class StaffAllFormsPresenter implements StaffAllFormsContract.Presenter {

    private Context mContext;
    private StaffAllFormsContract.View mView;
    private AuthHelper mAuthHelper;
    private DatabaseHelper mDatabaseHelper;

    private ArrayList<Application> mApplications = new ArrayList<>();

    public StaffAllFormsPresenter(Context context, StaffAllFormsContract.View view){
        mContext = context;
        mView = view;

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
    public void goToItemDetail(Application application) {
        String jsonApplication = new Gson().toJson(application);
        mView.goApplicationDetailView(jsonApplication);
    }

    @Override
    public void getFormsData() {
        mDatabaseHelper.retrieveApplicationData(new DatabaseHelper.OnGetDataListener() {
            @Override
            public void onStart() {
                mView.onGetFormStart();
            }

            @Override
            public void onSuccessful(DataSnapshot dataSnapshot) {
                mApplications.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Application application = ds.getValue(Application.class);
                    if (application != null){
                        if (application.getStatus().equals(Constant.FORM_STATUS_SUBMITTED)) {
                            mApplications.add(application);
                        }
                    }
                }
                mView.onGetFormSuccessful(mApplications);
            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                mView.onGetFormFailed(databaseError);
            }
        });
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
    public ArrayList<Application> setList(ArrayList<Application> applications, String newText) {
        newText = newText.replace(" ", "").toLowerCase();
        ArrayList<Application> resultList = new ArrayList<>();

        for (Application application : applications){
            String name = (application.getStudentFirstName() + application.getStudentLastName()).toLowerCase();
            String studentId = String.valueOf(application.getStudentID());
            String refCode = application.getApplicationId().toLowerCase();

            if (name.contains(newText) || studentId.contains(newText) || refCode.contains(newText))
                resultList.add(application);
        }

        return resultList;
    }
}
