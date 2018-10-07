package com.sep.utsloanapp.activities.applicationDetailActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.sep.utsloanapp.R;
import com.sep.utsloanapp.activities.utils.Constant;
import com.sep.utsloanapp.activities.utils.Utils;
import com.sep.utsloanapp.firebaseHelper.AuthHelper;
import com.sep.utsloanapp.firebaseHelper.DatabaseHelper;
import com.sep.utsloanapp.models.Application;
import com.sep.utsloanapp.models.Student;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ApplicationDetailPresenter implements ApplicationDetailContract.Presenter{

    private Context mContext;
    private ApplicationDetailContract.View mView;
    private AuthHelper mAuthHelper;
    private DatabaseHelper mDatabaseHelper;

    public ApplicationDetailPresenter(Context context, ApplicationDetailContract.View view){
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
    public void unwrapApplicationData(Bundle extra) {
        String jsonApplication = extra.getString(Constant.JSON_APPLICATION_KEY);
        int userType = extra.getInt(Constant.TYPE_KEY);
        Application application = new Gson().fromJson(jsonApplication, Application.class);
        mView.setContent(application, userType);
    }

    @Override
    public void getStudentInfo(final String studentUID) {
        mDatabaseHelper.retrieveStudentData(new DatabaseHelper.OnGetDataListener() {
            @Override
            public void onStart() {
                mView.onGetStudentDataStart();
            }

            @Override
            public void onSuccessful(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Student student = ds.getValue(Student.class);
                    if (student!=null){
                        if (student.getUid().equals(studentUID)){
                            mView.onGetStudentDataSuccessful(student);
                        }
                    }
                }
            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                mView.onGetStudentDataFailed(databaseError);
            }
        });
    }

    @Override
    public void cancelApplicationSubmission(Application application) {
        mDatabaseHelper.removeObject(application);
        //Update student availability to submit
        String uid = application.getStudentUid();
        mDatabaseHelper.updateUserAvailability(uid, Constant.ENABLE_VAL);

        mView.finishViewWithMsg(mContext.getResources().getString(R.string.cancel_application_msg));
    }

    @Override
    public void startReviewForm(Application application, Student student) {
        String uid = mAuthHelper.getUid();

        application.setStaffUid(uid);
        application.setStatus(Constant.FORM_STATUS_IN_PROCESS);

        mDatabaseHelper.saveObject(application);

        Utils.sendReviewEmail(application,student);

        mView.finishViewWithMsg(mContext.getResources().getString(R.string.start_review_msg));
    }

    @Override
    public void startDeclareForm(Application application, Student student, int resultCode, String rejectReason) {
        //TODO: set application status and timeDeclared, set application result, set application reject reason if rejected
        //TODO: update student availability, send email

        application.setStatus(Constant.FORM_STATUS_COMPLETE);
        application.setResult(resultCode);

        if (resultCode == Constant.RESULT_REJECTED)
            application.setRejectReason(rejectReason);

        @SuppressLint("SimpleDateFormat")
        String currentDateAndTime = new SimpleDateFormat(Constant.DATE_TIME_PATTERN).format(new Date());
        application.setTimeDeclared(currentDateAndTime);

        mDatabaseHelper.saveObject(application);
        mDatabaseHelper.updateUserAvailability(application.getStudentUid(), Constant.ENABLE_VAL);

        Utils.sendResultEmail(application, student);

        mView.finishViewWithMsg("You have declared a result for an application successfully.");
    }

}
