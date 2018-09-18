package com.sep.utsloanapp.activities.guideActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.sep.utsloanapp.R;
import com.sep.utsloanapp.activities.createFormActivity.CreateFormActivity;
import com.sep.utsloanapp.activities.utils.Constant;
import com.sep.utsloanapp.activities.utils.Utils;

public class GuideActivity extends AppCompatActivity implements View.OnClickListener, GuideContract.View{

    private Button mOK_btn, mCreate_btn;

    private GuideContract.Presenter mPresenter;

    private boolean mIsAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
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
        mOK_btn.setOnClickListener(this);
        mCreate_btn.setOnClickListener(this);

        popUpDialog();

        mPresenter = new GuidePresenter(this, this);

        mPresenter.checkAvailable();
    }

    private void popUpDialog() {
        Utils.showMsgDialog(this,
                getLayoutInflater().inflate(R.layout.dialog_msg_dialog, null),
                getResources().getString(R.string.help),
                getString(R.string.guidance_pop_up_msg));
    }

    private void init() {
        mOK_btn = findViewById(R.id.guide_ok_btn);
        mCreate_btn = findViewById(R.id.guide_create_btn);
    }

    @Override
    public void onClick(View v) {
        if (v == mCreate_btn)
            createForm();
        if (v == mOK_btn)
            onBackPressed();;
    }

    private void createForm() {
        if (mIsAvailable)
            startActivity(new Intent(this, CreateFormActivity.class));
        else
            Utils.showMsgDialog(this, getLayoutInflater().inflate(R.layout.dialog_msg_dialog, null),
                    getString(R.string.reminder),
                    getString(R.string.form_more_than_one_error_msg));
    }

    @Override
    public void setPresenter(GuideContract.Presenter presenter) {
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

    @Override
    public void onGetAvailableSuccessful(int availabilityCode) {
        if (availabilityCode == Constant.DISABLE_VAL)
            mIsAvailable = false;
        else if (availabilityCode == Constant.ENABLE_VAL)
            mIsAvailable = true;
    }
}
