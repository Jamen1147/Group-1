package com.sep.utsloanapp.activities.studentMyFormsActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DatabaseError;
import com.sep.utsloanapp.R;
import com.sep.utsloanapp.activities.applicationDetailActivity.ApplicationDetailActivity;
import com.sep.utsloanapp.activities.createFormActivity.CreateFormActivity;
import com.sep.utsloanapp.activities.utils.Constant;
import com.sep.utsloanapp.activities.utils.Utils;
import com.sep.utsloanapp.models.Application;

import java.util.ArrayList;

public class StudentMyFormsActivity extends AppCompatActivity implements StudentMyFormsContract.View{

    private StudentMyFormsContract.Presenter mPresenter;
    private StudentMyFormsListAdapter mAdapter;

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;

    //Half way change, staff might need to share this view
    private boolean isStaff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_my_forms);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorGrey));
        getSupportActionBar().setHomeAsUpIndicator(getResources()
                .getDrawable(R.drawable.ic_action_back_grey));

        init();

        Bundle extra = getIntent().getExtras();
        int type = 0;
        if (extra != null){
            type = extra.getInt(Constant.TYPE_KEY);
            if (type == Constant.STAFF_VAL){
                isStaff = true;
            }
        }

        mPresenter = new StudentMyFormsPresenter(this, this);
        mPresenter.getFormsData(type);
    }

    private void init() {
        mRecyclerView = findViewById(R.id.student_my_form_recyclerView);
        mProgressBar = findViewById(R.id.student_my_form_progressbar);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void setPresenter(StudentMyFormsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void currentUserNull() {
        mPresenter.logout();
    }

    @Override
    public void onGetFormStart() {
        mRecyclerView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onGetFormSuccessful(ArrayList<Application> applications) {
        mRecyclerView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        StudentMyFormsListAdapter mAdapter = new StudentMyFormsListAdapter(applications, mPresenter, this);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onGetFormFailed(DatabaseError databaseError) {
        mRecyclerView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        Utils.showMsg(this, "Loading error, please re-load the page");
        databaseError.toException().printStackTrace();
    }

    @Override
    public void goToEditing(String jsonApplication) {
        Intent intent = new Intent(this, CreateFormActivity.class);
        intent.putExtra(Constant.JSON_APPLICATION_KEY, jsonApplication);
        startActivity(intent);
    }

    @Override
    public void goToDetailView(String jsonApplication) {
        //TODO: Form Detail View need to be completed
        Intent intent = new Intent(this, ApplicationDetailActivity.class);
        if (isStaff){
            intent.putExtra(Constant.TYPE_KEY, Constant.STAFF_VAL);
        }else {
            intent.putExtra(Constant.TYPE_KEY, Constant.STUDENT_VAL);
        }
        intent.putExtra(Constant.JSON_APPLICATION_KEY, jsonApplication);
        startActivity(intent);
    }
}
