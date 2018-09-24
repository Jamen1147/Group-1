package com.sep.utsloanapp.activities.staffAllFormsActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DatabaseError;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.sep.utsloanapp.R;
import com.sep.utsloanapp.activities.applicationDetailActivity.ApplicationDetailActivity;
import com.sep.utsloanapp.activities.utils.Constant;
import com.sep.utsloanapp.activities.utils.Utils;
import com.sep.utsloanapp.models.Application;

import java.util.ArrayList;

public class StaffAllFormsActivity extends AppCompatActivity implements StaffAllFormsContract.View{

    private StaffAllFormsContract.Presenter mPresenter;
    private MaterialSearchView mMaterialSearchView;
    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private ArrayList<Application> mApplications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_all_forms);
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

        mPresenter = new StaffAllFormsPresenter(this, this);
        mPresenter.getFormsData();
    }

    private void init() {
        mMaterialSearchView = findViewById(R.id.staff_all_forms_search_bar);
        mProgressBar = findViewById(R.id.staff_all_forms_progressbar);
        mRecyclerView = findViewById(R.id.staff_all_forms_recyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void goApplicationDetailView(String jsonApplication) {
        Intent intent = new Intent(this, ApplicationDetailActivity.class);
        intent.putExtra(Constant.TYPE_KEY, Constant.STAFF_VAL);
        intent.putExtra(Constant.JSON_APPLICATION_KEY, jsonApplication);
        startActivity(intent);
    }

    @Override
    public void onGetFormStart() {
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mMaterialSearchView.setVisibility(View.GONE);
    }

    @Override
    public void onGetFormSuccessful(ArrayList<Application> applications) {
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mMaterialSearchView.setVisibility(View.VISIBLE);
        StaffApplicationFormListAdapter adapter = new StaffApplicationFormListAdapter(applications, mPresenter, this);
        adapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(adapter);

        mApplications = applications;
    }

    @Override
    public void onGetFormFailed(DatabaseError databaseError) {
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        Utils.showMsg(this, "Loading error, please re-load the page");
        databaseError.toException().printStackTrace();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_bar, menu);
        MenuItem menuItem = menu.findItem(R.id.action_bar_search);
        mMaterialSearchView.setMenuItem(menuItem);

        mMaterialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText) {
                refreshList(newText);
                return false;
            }
        });

        mMaterialSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() { }

            @Override
            public void onSearchViewClosed() {
                resetList();
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void refreshList(String newText){
        ArrayList<Application> applications = mPresenter.setList(mApplications, newText);
        StaffApplicationFormListAdapter adapter = new StaffApplicationFormListAdapter(applications, mPresenter, this);
        mRecyclerView.setAdapter(adapter);
    }

    private void resetList(){
        StaffApplicationFormListAdapter adapter = new StaffApplicationFormListAdapter(mApplications, mPresenter, this);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void setPresenter(StaffAllFormsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void currentUserNull() {
        mPresenter.logout();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
