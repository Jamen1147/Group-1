package com.sep.utsloanapp.activities.mainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.sep.utsloanapp.R;
import com.sep.utsloanapp.activities.logoActivity.LogoPageActivity;
import com.sep.utsloanapp.activities.mainActivity.staffFragment.StaffFragment;
import com.sep.utsloanapp.activities.mainActivity.studentFragment.StudentFragment;
import com.sep.utsloanapp.activities.staffLoginActivity.StaffLoginActivity;
import com.sep.utsloanapp.activities.utils.Utils;

import java.util.concurrent.Callable;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

    public static final String STUDENT_FRAGMENT = "student_frag";
    public static final String STAFF_FRAGMENT = "staff_frag";
    public static final String AVAILABILITY_KEY = "availabilityKey";

    private MainActivityContract.Presenter mPresenter;

    private StudentFragment mStudentFragment;
    private StaffFragment mStaffFragment;

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorGrey));

        mPresenter = new MainActivityPresenter(this, this);
        mProgressBar = findViewById(R.id.main_progressBar);

        //check user type, if there is extra come from login activities
        if (getIntent().getExtras() != null){
            mPresenter.checkType(getIntent().getExtras());
        }else {
            //check from the database otherwise
            mPresenter.checkType();
        }

    }

    private void showStudentFragment() {
        mStudentFragment = new StudentFragment();
        StudentFragment savedStudentFragment = (StudentFragment) getSupportFragmentManager()
                .findFragmentByTag(STUDENT_FRAGMENT);

        if (savedStudentFragment == null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.main_frame_layout, mStudentFragment, STUDENT_FRAGMENT);
            fragmentTransaction.commit();
        }
    }

    private void showStaffFragment() {
        mStaffFragment = new StaffFragment();
        StaffFragment savedStaffFragment = (StaffFragment) getSupportFragmentManager()
                .findFragmentByTag(STAFF_FRAGMENT);

        if (savedStaffFragment == null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.main_frame_layout, mStaffFragment, STAFF_FRAGMENT);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void setPresenter(MainActivityContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void currentUserNull() {
        startActivity(new Intent(this, LogoPageActivity.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.stop();
    }

    @Override
    public void showStudent() {
        mProgressBar.setVisibility(View.GONE);
        showStudentFragment();
    }

    @Override
    public void showStaff() {
        mProgressBar.setVisibility(View.GONE);
        showStaffFragment();
    }

    @Override
    public void onGetDataFailed(DatabaseError databaseError) {
        databaseError.toException().printStackTrace();
        Utils.showMsg(this, "Re-Directing Failed, Please Login Again");
        mPresenter.logout();
    }

    @Override
    public void onGetDataStart() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_bar_logout){
            Utils.showConfirmDialog(this,
                    getLayoutInflater().inflate(R.layout.dialog_confirm_dialog, null),
                    getResources().getString(R.string.want_to_sign_out),
                    getResources().getString(R.string.get_me_back),
                    getResources().getString(R.string.sign_me_out),
                    new Callable<Void>() {
                        @Override
                        public Void call() {
                            mPresenter.logout();
                            return null;
                        }
                    });
        }

        return super.onOptionsItemSelected(item);
    }
}
