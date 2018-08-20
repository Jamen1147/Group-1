package com.sep.utsloanapp.activities.staffLoginActivity;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sep.utsloanapp.R;
import com.sep.utsloanapp.activities.studentLoginActivity.StudentLoginPresenter;
import com.sep.utsloanapp.activities.utils.Utils;
import com.sep.utsloanapp.firebaseHelper.AuthHelper;
import com.sep.utsloanapp.firebaseHelper.DatabaseHelper;
import com.sep.utsloanapp.models.User;

public class StaffLoginPresenter implements StaffLoginContract.Presenter {

    private Context mContext;
    private StaffLoginContract.View mView;

    private AuthHelper mAuthHelper;
    private DatabaseHelper mDatabaseHelper;

    public StaffLoginPresenter(Context context, StaffLoginContract.View view){
        mContext = context;
        mView = view;
        mView.setPresenter(this);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child(DatabaseHelper.KEY_DB_USER);

        mAuthHelper = new AuthHelper(firebaseAuth);
        mDatabaseHelper = new DatabaseHelper(reference);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void doLogin(String staffId, String password) {
        if (isInputValid(staffId, password)){
            String email = staffId + StudentLoginPresenter.SUFFIX;
            mAuthHelper.loginUser(email, password, new AuthHelper.StateListener() {
                @Override
                public void onStart() {
                    mView.onLoginStart();
                }
                @Override
                public void onSuccessful() {
                    mView.onLoginSuccessful();
                }
                @Override
                public void onFailed() {
                    mView.onLoginFailed();
                }
            });
        }
    }

    @Override
    public void doSaveUser(String staffId, String password) {
        String uid = "";
        if (mAuthHelper.getCurrentUser() != null){
            uid = mAuthHelper.getUid();
        }
        User user = new User(staffId, uid, mContext.getString(R.string.staff));
        mDatabaseHelper.saveUser(user);
    }

    @Override
    public void checkType() {
        final String[] userType = {""};
        if (mAuthHelper.getCurrentUser() != null) {
            mDatabaseHelper.retrieveData(new DatabaseHelper.OnGetDataListener() {
                @Override
                public void onStart() {

                }
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
                                    mAuthHelper.logOutUser();
                                    mView.onGetDataSuccessfulUserStudent();
                                    return;
                                }else {
                                    mView.onGetDataSuccessfulUserStaff();
                                    return;
                                }
                            }else {
                                //not the one looking for
                            }
                        }else {
                            //means first time user. ? do save user.
                            mView.onGetFirstTimeUser();
                            return;
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

    private boolean isInputValid(String studentId, String password){

        if (TextUtils.isEmpty(studentId)){
            mView.loginInputInvalid(mContext.getString(R.string.id_empty_err_msg));
            return false;
        }else if (!Utils.isUserIdValid(studentId)){
            mView.loginInputInvalid(mContext.getString(R.string.invalid_id_err_msg));
            return false;
        }

        if (TextUtils.isEmpty(password)){
            mView.loginInputInvalid(mContext.getString(R.string.password_empty_err_msg));
            return false;
        }else if (!Utils.isPasswordValid(password)){
            mView.loginInputInvalid(mContext.getString(R.string.password_length_err_msg));
            return false;
        }
        return true;
    }
}
