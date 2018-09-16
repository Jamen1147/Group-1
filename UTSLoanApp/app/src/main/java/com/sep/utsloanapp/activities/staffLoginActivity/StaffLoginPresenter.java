package com.sep.utsloanapp.activities.staffLoginActivity;

import android.content.Context;
import android.text.TextUtils;

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
import com.sep.utsloanapp.models.Staff;
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
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

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

        User user = new User(staffId, uid, 1, 1);
        Staff staff = new Staff(uid, "Six", "Lee", "666666@uts.edu.au", "0412666666", "1966-06-06",
                "2006-06-06", Integer.valueOf(staffId), 0, 66);

        mDatabaseHelper.saveObject(user);
        mDatabaseHelper.saveObject(staff);
    }

    @Override
    public void checkType() {
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
                                int userType = user.getUserType();
                                if (userType == 0){
                                    mAuthHelper.logOutUser();
                                    mView.onGetDataSuccessfulUserStudent();
                                    return;
                                }else {
                                    mView.onGetDataSuccessfulUserStaff(userType);
                                    return;
                                }
                            }else {
                                //not the one looking for
                            }
                        }
                    }
                    mView.onGetFirstTimeUser();
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
