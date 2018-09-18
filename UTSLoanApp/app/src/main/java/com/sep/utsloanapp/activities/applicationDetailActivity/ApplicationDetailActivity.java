package com.sep.utsloanapp.activities.applicationDetailActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.google.gson.Gson;
import com.sep.utsloanapp.R;
import com.sep.utsloanapp.activities.studentPersonalDetailActivity.StudentPersonalDetailActivity;
import com.sep.utsloanapp.activities.utils.Constant;
import com.sep.utsloanapp.activities.utils.Utils;
import com.sep.utsloanapp.models.Application;
import com.sep.utsloanapp.models.Student;

import java.util.concurrent.Callable;

public class ApplicationDetailActivity extends AppCompatActivity implements ApplicationDetailContract.View,
                                                                            View.OnClickListener{

    private ApplicationDetailContract.Presenter mPresenter;

    private ImageView mArrowImage;
    private Button mBack_btn, mReview_btn, mCancel_btn;

    private LinearLayout mNameIdLinear, mOtherLinear, mBtnsLinear;
    private ScrollView mScrollView;
    private ProgressBar mProgressBar;

    private TextView mAmount_tv, mUsage_tv, mOtherUsage_tv, mLoanPeriod_tv, mRepayment_tv,
            mBsb_tv, mAccountNum_tv, mStudentName_tv, mStudentId_tv;

    private boolean hasStudent, hasApplication, isStudent, isStaff;
    private Student mStudent = new Student();
    private Application mApplication = new Application();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_detail);
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
        mArrowImage.setOnClickListener(this);
        mBack_btn.setOnClickListener(this);
        mReview_btn.setOnClickListener(this);
        mCancel_btn.setOnClickListener(this);

        mPresenter = new ApplicationDetailPresenter(this, this);

        Bundle extra = getIntent().getExtras();
        if (extra != null)
            //check type and unwrap data
            mPresenter.unwrapApplicationData(extra);
    }

    private void init() {
        mNameIdLinear = findViewById(R.id.form_detail_name_id_lin);
        mOtherLinear = findViewById(R.id.form_detail_other_usage_hidden_linear);
        mProgressBar = findViewById(R.id.form_detail_progressbar);
        mArrowImage = findViewById(R.id.form_detail_personal_information_arrow_click);
        mAmount_tv = findViewById(R.id.form_detail_amount_tv);
        mUsage_tv = findViewById(R.id.form_detail_usage_tv);
        mOtherUsage_tv = findViewById(R.id.form_detail_another_usage_tv);
        mLoanPeriod_tv = findViewById(R.id.form_detail_period_year_tv);
        mRepayment_tv = findViewById(R.id.form_detailrepayment_month_tv);
        mBsb_tv = findViewById(R.id.form_detail_bsb_tv);
        mAccountNum_tv = findViewById(R.id.form_detail_account_num_tv);
        mBack_btn = findViewById(R.id.form_detail_back_btn);
        mReview_btn = findViewById(R.id.form_detail_start_review_btn);
        mStudentName_tv = findViewById(R.id.form_detail_personal_information_name);
        mStudentId_tv = findViewById(R.id.form_detail_personal_information_id);
        mCancel_btn = findViewById(R.id.form_detail_cancel_btn);
        mBtnsLinear = findViewById(R.id.form_detail_btn_lin);
        mScrollView = findViewById(R.id.form_detail_scrollView);
    }

    @Override
    public void setPresenter(ApplicationDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void currentUserNull() {
        mPresenter.logout();
    }

    @Override
    public void onClick(View v) {
        if (v == mBack_btn)
            onBackPressed();
        if (v == mReview_btn)
            startReviewApplication();
        if (v == mArrowImage)
            goToPersonalDetailView();
        if (v == mCancel_btn)
            cancelSubmission();
    }

    private void cancelSubmission() {
        if (isStudent){
            if (hasApplication){
                if (mApplication.getStatus().equals(Constant.FORM_STATUS_SUBMITTED)){
                    //TODO: Cancel Submission Function
                    Utils.showConfirmDialog(this,
                            getLayoutInflater().inflate(R.layout.dialog_confirm_dialog, null),
                            getString(R.string.confirmation),
                            getString(R.string.not_cancel_submission),
                            getString(R.string.not_sure),
                            getString(R.string.i_am_sure),
                            new Callable<Void>() {
                                @Override
                                public Void call() {
                                    mPresenter.cancelApplicationSubmission(mApplication);
                                    return null;
                                }
                            });

                }else
                    Utils.showMsgDialog(this, getLayoutInflater().inflate(R.layout.dialog_msg_dialog, null),
                            getString(R.string.reminder),
                            getString(R.string.cannot_cancel_application_submission));
            }
        }
    }

    private void goToPersonalDetailView() {
        if (hasStudent){
            String jsonStudent = new Gson().toJson(mStudent);
            Intent intent = new Intent(this, StudentPersonalDetailActivity.class);
            intent.putExtra(Constant.JSON_STUDENT_KEY, jsonStudent);
            startActivity(intent);
        }
    }

    private void startReviewApplication() {
        if (isStaff){
            //TODO: Review Function
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void setContent(Application application, int userType) {
        String amount = "$" + application.getAmount();
        mAmount_tv.setText(amount);
        mUsage_tv.setText(application.getUsage());

        if (!TextUtils.isEmpty(application.getOtherUsage())){
            mOtherUsage_tv.setText(application.getOtherUsage());
            mOtherLinear.setVisibility(View.VISIBLE);
        }

        mLoanPeriod_tv.setText(application.getLoanPeriodYear());
        mRepayment_tv.setText(application.getRepaymentPeriodMonth());
        mBsb_tv.setText(application.getBankBsb());
        mAccountNum_tv.setText(application.getBankAccount());

        if (userType == Constant.STUDENT_VAL){
            mCancel_btn.setVisibility(View.VISIBLE);
            isStudent = true;
        }
        else if (userType == Constant.STAFF_VAL){
            mReview_btn.setVisibility(View.VISIBLE);
            isStaff = true;
        }

        mApplication = application;
        hasApplication = true;

        mPresenter.getStudentInfo(application.getStudentUid());
    }

    @Override
    public void onGetStudentDataStart() {
        mNameIdLinear.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onGetStudentDataSuccessful(Student student) {
        mNameIdLinear.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        String name = student.getFirstName() + " " + student.getLastName();
        mStudentName_tv.setText(name);
        mStudentId_tv.setText(String.valueOf(student.getStudentId()));
        mStudent = student;
        hasStudent = true;
    }

    @Override
    public void onGetStudentDataFailed(DatabaseError databaseError) {
        mStudentName_tv.setText(R.string.internet_error);
        mStudentId_tv.setText(R.string.internet_error);
        mNameIdLinear.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        databaseError.toException().printStackTrace();
    }

    @Override
    public void onCancelFinished() {
        onBackPressed();
    }
}
