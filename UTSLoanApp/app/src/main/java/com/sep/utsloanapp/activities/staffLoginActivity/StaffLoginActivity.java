package com.sep.utsloanapp.activities.staffLoginActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sep.utsloanapp.R;
import com.sep.utsloanapp.activities.mainActivity.MainActivity;
import com.sep.utsloanapp.activities.utils.Utils;
import com.sep.utsloanapp.firebaseHelper.DatabaseHelper;

public class StaffLoginActivity extends AppCompatActivity implements StaffLoginContract.View,
        View.OnClickListener{

    private StaffLoginContract.Presenter mPresenter;

    private EditText mStaffIdEt, mPasswordEt;
    private TextView mLoginHelpTv;
    private Button mLoginBtn;
    private ProgressBar mProgressBar;
    private String mStaffId, mPassword;

    public static final int STAFF_VAL = 0;
    public static final String TYPE_KEY = "typeKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_login);

        Toolbar toolbar = findViewById(R.id.staff_login_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            Log.d("Action bar", " != null");
        }
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorGrey));
        getSupportActionBar().setHomeAsUpIndicator(getResources()
                .getDrawable(R.drawable.ic_action_back_grey));

        init();

        mLoginHelpTv.setOnClickListener(this);
        mLoginBtn.setOnClickListener(this);

        mPresenter = new StaffLoginPresenter(this, this);
    }

    private void init() {
        mStaffIdEt = findViewById(R.id.staff_login_staff_id_et);
        mPasswordEt = findViewById(R.id.staff_login_password_et);
        mLoginHelpTv = findViewById(R.id.staff_login_help_tv);
        mLoginBtn = findViewById(R.id.staff_login_login_btn);
        mProgressBar = findViewById(R.id.staff_login_progressbar);

    }

    @Override
    public void setPresenter(StaffLoginContract.Presenter presenter) {
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
            //dialog to tell the user to go to UTS online
        }
    }

    private void doLogin() {
        mStaffId = mStaffIdEt.getText().toString();
        mPassword = mPasswordEt.getText().toString();
        mPresenter.doLogin(mStaffId, mPassword);
    }

    @Override
    public void loginInputInvalid(String errorMessage) {
        if (errorMessage.equals(getString(R.string.id_empty_err_msg))){
            mStaffIdEt.setError(errorMessage);
        }else if (errorMessage.equals(getString(R.string.invalid_id_err_msg))){
            mStaffIdEt.setError(errorMessage);
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
        Utils.showMsg(this, getString(R.string.no_access_to_staff_err_msg));
    }

    @Override
    public void onGetDataSuccessfulUserStaff() {
        mProgressBar.setVisibility(View.GONE);
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra(TYPE_KEY , STAFF_VAL);
        startActivity(i);
        finish();
    }

    @Override
    public void onGetDataFailed(DatabaseError databaseError) {
        mProgressBar.setVisibility(View.GONE);
        databaseError.toException().printStackTrace();
    }

    @Override
    public void onGetFirstTimeUser() {
        mPresenter.doSaveUser(mStaffId, mPassword);
        mProgressBar.setVisibility(View.GONE);
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra(TYPE_KEY , STAFF_VAL);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
