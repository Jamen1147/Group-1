package com.sep.utsloanapp.activities.createFormActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sep.utsloanapp.R;
import com.sep.utsloanapp.firebaseHelper.AuthHelper;
import com.sep.utsloanapp.firebaseHelper.DatabaseHelper;
import com.sep.utsloanapp.models.Application;
import com.sep.utsloanapp.models.Student;
import com.sep.utsloanapp.models.User;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateFormPresenter implements CreateFormContract.Presenter{

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private Context mContext;
    private CreateFormContract.View mView;
    private AuthHelper mAuthHelper;
    private DatabaseHelper mDatabaseHelper;

    public CreateFormPresenter(Context context, CreateFormContract.View view){
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
    public void loadNameID() {
        mDatabaseHelper.retrieveStudentData(new DatabaseHelper.OnGetDataListener() {
            @Override
            public void onStart() {
                mView.onLoadNameStart();
            }

            @Override
            public void onSuccessful(DataSnapshot dataSnapshot) {
                String uid = mAuthHelper.getUid();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Student student = ds.getValue(Student.class);
                    if (student != null) {
                        if (student.getUid().equals(uid)) {
                            String firstName = student.getFirstName();
                            String lastName = student.getLastName();
                            String name = firstName + " " + lastName;
                            String id = student.getStudentId() + "";
                            mView.onLoadNameSuccessful(name, id);
                        }
                    }
                }
            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                mView.onLoadNameFailed(databaseError);
            }
        });
    }

    @Override
    public void loadFormContent() {
        mDatabaseHelper.retrieveApplicationData(new DatabaseHelper.OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccessful(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onFailed(DatabaseError databaseError) {

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
    public void saveForm(Application application) {
        String uid = mAuthHelper.getUid();
        application.setApplicationId(mDatabaseHelper.pushKey());
        application.setStudentUid(uid);
        application.setStaffUid("");
        mDatabaseHelper.saveObject(application);
        //disable
        mDatabaseHelper.updateUserAvailability(uid, 0);
        mView.onSaveFinished();
    }

    @Override
    public void submitForm(Application application) {
        application.setApplicationId(mDatabaseHelper.pushKey());
        application.setStudentUid(mAuthHelper.getUid());
        @SuppressLint("SimpleDateFormat")
        String currentDateAndTime = new SimpleDateFormat(DATE_TIME_PATTERN).format(new Date());
        application.setTimeSubmitted(currentDateAndTime);

        if (formIsValid(application)){
            mDatabaseHelper.saveObject(application);
        }

    }

    private boolean formIsValid(Application application) {
        if (TextUtils.isEmpty(application.getAmount())){
            mView.onFormInfoInvalid(mContext.getString(R.string.err_msg_amount_empty));
            return false;
        }

        if (application.getAmount().length() < 3){
            mView.onFormInfoInvalid(mContext.getString(R.string.err_msg_amount_low));
            return false;
        }

        if (application.getAmount().length() > 6){
            mView.onFormInfoInvalid(mContext.getString(R.string.err_msg_amount_high));
            return false;
        }

        if (TextUtils.isEmpty(application.getBankBsb())){
            mView.onFormInfoInvalid(mContext.getString(R.string.err_msg_bsb_empty));
            return false;
        }

        if (TextUtils.isEmpty(application.getBankAccount())){
            mView.onFormInfoInvalid(mContext.getString(R.string.err_msg_account_num_empty));
            return false;
        }

        if (TextUtils.isEmpty(application.getUsage())){
            mView.onFormInfoInvalid(mContext.getString(R.string.err_msg_usage_not_chosen));
            return false;
        }

        if (application.getUsage().equals("Other") && TextUtils.isEmpty(application.getOtherUsage())){
            mView.onFormInfoInvalid(mContext.getString(R.string.err_msg_other_usage_empty));
            return false;
        }

        if (TextUtils.isEmpty(application.getLoanPeriodYear())){
            mView.onFormInfoInvalid(mContext.getString(R.string.err_msg_loan_period_notZ_chosen));
            return false;
        }

        if (TextUtils.isEmpty(application.getRepaymentPeriodMonth())){
            mView.onFormInfoInvalid(mContext.getString(R.string.err_msg_repayment_not_chosen));
            return false;
        }

        if (application.getBankBsb().length() != 6){
            mView.onFormInfoInvalid(mContext.getString(R.string.err_msg_bsb_wrong));
            return false;
        }

        if (application.getBankAccount().length() < 6 || application.getBankAccount().length() > 8){
            mView.onFormInfoInvalid(mContext.getString(R.string.err_msg_account_num_wrong));
            return false;
        }

        return true;
    }

}