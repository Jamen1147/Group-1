package com.sep.utsloanapp.activities.studentLoginActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.sep.utsloanapp.R;
import com.sep.utsloanapp.activities.mainActivity.MainActivity;
import com.sep.utsloanapp.activities.staffLoginActivity.StaffLoginActivity;
import com.sep.utsloanapp.activities.utils.Utils;

public class StudentLoginActivity extends AppCompatActivity implements StudentLoginContract.View,
        View.OnClickListener{

    private EditText mStudentIdEt, mPasswordEt;
    private TextView mLoginHelpTv;
    private Button mLoginBtn;
    private ProgressBar mProgressBar;
    private String mStudentId, mPassword;

    private StudentLoginContract.Presenter mPresenter;

    public static final int STUDENT_VAL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        Toolbar toolbar = findViewById(R.id.student_login_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorGrey));
        getSupportActionBar().setHomeAsUpIndicator(getResources()
                .getDrawable(R.drawable.ic_action_back_grey));

        init();

        mLoginHelpTv.setOnClickListener(this);
        mLoginBtn.setOnClickListener(this);

        mPresenter = new StudentLoginPresenter(this, this);
    }

    private void init() {
        mStudentIdEt = findViewById(R.id.student_login_student_id_et);
        mPasswordEt = findViewById(R.id.student_login_password_et);
        mLoginHelpTv = findViewById(R.id.student_login_help_tv);
        mLoginBtn = findViewById(R.id.student_login_login_btn);
        mProgressBar = findViewById(R.id.student_login_progressbar);
    }

    @Override
    public void setPresenter(StudentLoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void currentUserNull() {
    }

    @Override
    public void onClick(View view) {
        if (view == mLoginBtn){
            doLogin();
        }
        if (view == mLoginHelpTv){
            Utils.showMsgDialog(this,
                    getLayoutInflater().inflate(R.layout.dialog_msg_dialog, null),
                    getResources().getString(R.string.login_help_text));
        }
    }

    private void doLogin() {
        mStudentId = mStudentIdEt.getText().toString();
        mPassword = mPasswordEt.getText().toString();
        mPresenter.doLogin(mStudentId, mPassword);
    }

    @Override
    public void loginInputInvalid(String errorMessage) {
        if (errorMessage.equals(getString(R.string.id_empty_err_msg))){
            mStudentIdEt.setError(errorMessage);
        }else if (errorMessage.equals(getString(R.string.invalid_id_err_msg))){
            mStudentIdEt.setError(errorMessage);
        }else if (errorMessage.equals(getString(R.string.password_empty_err_msg))){
            mPasswordEt.setError(errorMessage);
        }else {
            mPasswordEt.setError(errorMessage);
        }
    }

    @Override
    public void onLoginStart() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoginSuccessful() {
        Log.d("Tag----------", "LoginSuccessful");
        mPresenter.checkType();
    }

    @Override
    public void onLoginFailed() {
        mProgressBar.setVisibility(View.GONE);
        Utils.showMsg(this, getString(R.string.login_failed_err_msg));
    }

    @Override
    public void onGetDataSuccessfulUserStudent() {
        mProgressBar.setVisibility(View.GONE);
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra(StaffLoginActivity.TYPE_KEY , STUDENT_VAL);
        startActivity(i);
        finish();
    }

    @Override
    public void onGetDataSuccessfulUserStaff() {
        mProgressBar.setVisibility(View.GONE);
        Utils.showMsg(this, getString(R.string.no_access_to_Student_err_msg));
    }

    @Override
    public void onGetDataFailed(DatabaseError databaseError) {
        mProgressBar.setVisibility(View.GONE);
        databaseError.toException().printStackTrace();
    }

    @Override
    public void onGetFirstTimeUser() {
        mPresenter.doSaveUser(mStudentId, mPassword);
        mProgressBar.setVisibility(View.GONE);
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra(StaffLoginActivity.TYPE_KEY, STUDENT_VAL);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
