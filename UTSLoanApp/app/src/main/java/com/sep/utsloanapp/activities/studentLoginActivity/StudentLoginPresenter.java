package com.sep.utsloanapp.activities.studentLoginActivity;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sep.utsloanapp.R;
import com.sep.utsloanapp.activities.utils.Utils;
import com.sep.utsloanapp.firebaseHelper.AuthHelper;
import com.sep.utsloanapp.firebaseHelper.DatabaseHelper;
import com.sep.utsloanapp.models.Student;
import com.sep.utsloanapp.models.User;

public class StudentLoginPresenter implements StudentLoginContract.Presenter{

    private StudentLoginContract.View mView;
    private Context mContext;

    private AuthHelper mAuthHelper;
    private DatabaseHelper mDatabaseHelper;

    public static final String SUFFIX = "@uts.com";

    public StudentLoginPresenter(Context context, StudentLoginContract.View view){
        mView = view;
        mContext = context;
        mView.setPresenter(this);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        mAuthHelper = new AuthHelper(firebaseAuth);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        mDatabaseHelper = new DatabaseHelper(reference);
    }

    @Override
    public void start() {}

    @Override
    public void stop() {}

    @Override
    public void doLogin(String studentId, String password) {
        if (isInputValid(studentId, password)){
            String email = studentId + SUFFIX;
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
    public void doSaveUser(String studentId, String password) {
        String uid = "";
        if (mAuthHelper.getCurrentUser() != null){
            uid = mAuthHelper.getUid();
        }

        User user = new User(studentId, uid, 0, 1);
        Student student = new Student(uid, "Kong", "Kim", "Kong.Kim-1@student.uts.edu.au",
                "0432890131", "1998-05-12", "Science in Information Technology",
                "Inter-Networking", "Engineering and Information Technology",
                "Bachelor", "Korean", Integer.valueOf(studentId), 0, 4);

        mDatabaseHelper.saveObject(user);
        mDatabaseHelper.saveObject(student);
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
                                    mView.onGetDataSuccessfulUserStudent(userType);
                                    return;
                                }else {
                                    mAuthHelper.logOutUser();
                                    mView.onGetDataSuccessfulUserStaff();
                                    return;
                                }
                            }else {
                                //not the one looking for
                            }
                        }
                    }
                    //not found inside the loop, meaning that it is the first time user.
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
