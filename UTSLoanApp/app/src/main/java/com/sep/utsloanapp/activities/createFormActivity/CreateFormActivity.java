package com.sep.utsloanapp.activities.createFormActivity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.sep.utsloanapp.R;
import com.sep.utsloanapp.activities.utils.Utils;
import com.sep.utsloanapp.models.Application;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.concurrent.Callable;

public class CreateFormActivity extends AppCompatActivity implements View.OnClickListener, CreateFormContract.View{

    public static final String FORM_STATUS_SAVED = "saved";
    public static final String FORM_STATUS_SUBMITTED = "submitted";

    private TextView mStudentName_tv, mStudentId_tv;
    private ImageView mPersonalArrow_iv, mAmountHelp_iv, mUsageHelp_iv, mOtherUsageHelp_iv,
            mPeriodYearHelp_iv, mRepaymentHelp_iv;
    private EditText mAmount_et, mBsb_et, mAccountNum_et, mOtherUsage_et;
    private Button mCancel_btn, mSave_btn, mSubmit_btn;
    private Spinner mUsageSpinner, mPeriodYearSpinner, mRepaymentMonthSpinner;
    private ArrayAdapter<CharSequence> mUsageListArrayAdapter, mPeriodListArrayAdapter,
            mRepaymentListArrayAdapter;
    private ProgressBar mProgressBar;
    private LinearLayout mLinearLayout, mOtherHiddenLin;
    private CreateFormContract.Presenter mPresenter;
    private String mUsage, mLoanPeriod, mRepaymentPeriod;
    private int mStudentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_form);
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
        setOnClick();
        setSpinnerAdapter();
        listenItemSelections();

        mPresenter = new CreateFormPresenter(this, this);

        if (getIntent().getExtras() != null){
            //It's the saved one
            //TODO: Retrieve application data then Set contents
            mPresenter.loadFormContent();
        }

        mPresenter.loadNameID();
    }

    private void setSpinnerAdapter() {
        mUsageListArrayAdapter = ArrayAdapter.createFromResource(this, R.array.usage_list,
                android.R.layout.simple_spinner_item);
        mPeriodListArrayAdapter = ArrayAdapter.createFromResource(this, R.array.period_list,
                android.R.layout.simple_spinner_item);
        mRepaymentListArrayAdapter = ArrayAdapter.createFromResource(this, R.array.repayment_list,
                android.R.layout.simple_spinner_item);

        mUsageListArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPeriodListArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mRepaymentListArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mUsageSpinner.setAdapter(mUsageListArrayAdapter);
        mPeriodYearSpinner.setAdapter(mPeriodListArrayAdapter);
        mRepaymentMonthSpinner.setAdapter(mRepaymentListArrayAdapter);
    }

    private void setOnClick() {
        mPersonalArrow_iv.setOnClickListener(this);
        mAmountHelp_iv.setOnClickListener(this);
        mUsageHelp_iv.setOnClickListener(this);
        mOtherUsageHelp_iv.setOnClickListener(this);
        mPeriodYearHelp_iv.setOnClickListener(this);
        mRepaymentHelp_iv.setOnClickListener(this);
        mCancel_btn.setOnClickListener(this);
        mSave_btn.setOnClickListener(this);
        mSubmit_btn.setOnClickListener(this);
    }

    private void init() {
        mStudentName_tv = findViewById(R.id.create_form_personal_information_name);
        mStudentId_tv = findViewById(R.id.create_form_personal_information_id);
        mPersonalArrow_iv = findViewById(R.id.create_form_personal_information_arrow_click);
        mAmountHelp_iv = findViewById(R.id.create_form_amount_help_iv);
        mUsageHelp_iv = findViewById(R.id.create_form_usage_dropdown_help_iv);
        mOtherUsageHelp_iv = findViewById(R.id.create_form_another_usage_help_iv);
        mPeriodYearHelp_iv = findViewById(R.id.create_form_period_year_help_iv);
        mRepaymentHelp_iv = findViewById(R.id.create_form_repayment_month_help_iv);
        mAmount_et = findViewById(R.id.create_form_amount_et);
        mBsb_et = findViewById(R.id.create_form_bsb_et);
        mAccountNum_et = findViewById(R.id.create_form_account_num_et);
        mCancel_btn = findViewById(R.id.create_form_cancel_btn);
        mSave_btn = findViewById(R.id.create_form_save_btn);
        mSubmit_btn = findViewById(R.id.create_form_submit_btn);
        mUsageSpinner = findViewById(R.id.create_form_loan_form_usage_dropdown);
        mPeriodYearSpinner = findViewById(R.id.create_form_loan_form_period_year_dropdown);
        mRepaymentMonthSpinner = findViewById(R.id.create_form_loan_form_repayment_month_dropdown);
        mProgressBar = findViewById(R.id.create_form_progressbar);
        mLinearLayout = findViewById(R.id.create_form_name_id_lin);
        mOtherUsage_et = findViewById(R.id.create_form_write_another_usage_et);
        mOtherHiddenLin = findViewById(R.id.create_form_loan_form_other_usage_hidden_linear);
    }

    private void listenItemSelections() {
        mUsageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    mUsage = "";
                }else {
                    mUsage = parent.getItemAtPosition(position).toString();
                }

                if (position == 4){
                    mOtherHiddenLin.setVisibility(View.VISIBLE);
                }else {
                    mOtherHiddenLin.setVisibility(View.GONE);
                    mOtherUsage_et.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mPeriodYearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    mLoanPeriod = "";
                }else {
                    mLoanPeriod = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mRepaymentMonthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    mRepaymentPeriod = "";
                }else {
                    mRepaymentPeriod = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        Utils.showConfirmDialog(this,
                getLayoutInflater().inflate(R.layout.dialog_confirm_dialog, null),
                getString(R.string.are_you_sure),
                getString(R.string.not_sure),
                getString(R.string.yes),
                new Callable<Void>() {
                    @Override
                    public Void call() {
                        onBackPressed();
                        return null;
                    }
                });
        return super.onSupportNavigateUp();
    }

    @Override
    public void onClick(View v) {
        if(v == mPersonalArrow_iv){
            //TODO: go to Personal Detail Activity
        }
        if (v == mAmountHelp_iv) {
            Utils.showMsgDialog(this,
                    getLayoutInflater().inflate(R.layout.dialog_msg_dialog, null),
                    getString(R.string.amount_help_msg));
        }

        if (v == mUsageHelp_iv){
            Utils.showMsgDialog(this,
                    getLayoutInflater().inflate(R.layout.dialog_msg_dialog, null),
                    getString(R.string.usage_help_msg));
        }

        if (v == mPeriodYearHelp_iv){
            Utils.showMsgDialog(this,
                    getLayoutInflater().inflate(R.layout.dialog_msg_dialog, null),
                    getString(R.string.period_yesr_helpZ_msg));
        }

        if (v == mRepaymentHelp_iv){
            Utils.showMsgDialog(this,
                    getLayoutInflater().inflate(R.layout.dialog_msg_dialog, null),
                    getString(R.string.repayment_help_msg));
        }

        if (v == mOtherUsageHelp_iv){
            Utils.showMsgDialog(this,
                    getLayoutInflater().inflate(R.layout.dialog_msg_dialog, null),
                    getString(R.string.other_usage_help_msg));
        }

        if (v == mCancel_btn){
            Utils.showConfirmDialog(this,
                    getLayoutInflater().inflate(R.layout.dialog_confirm_dialog, null),
                    getString(R.string.are_you_sure),
                    getString(R.string.not_sure),
                    getString(R.string.yes),
                    new Callable<Void>() {
                        @Override
                        public Void call() {
                            onBackPressed();
                            return null;
                        }
                    });
        }

        if (v == mSave_btn){
            //TODO: Presenter --> Save
            Utils.showConfirmDialog(this,
                    getLayoutInflater().inflate(R.layout.dialog_confirm_dialog, null),
                    getString(R.string.are_you_sure),
                    getString(R.string.cancel),
                    getString(R.string.save),
                    new Callable<Void>() {
                        @Override
                        public Void call() {
                            Application application = getFormInfo();
                            application.setStatus(FORM_STATUS_SAVED);
                            mPresenter.saveForm(application);
                            return null;
                        }
                    });
        }

        if (v == mSubmit_btn){
            //TODO: Presenter --> Submit
            Utils.showConfirmDialog(this,
                    getLayoutInflater().inflate(R.layout.dialog_confirm_dialog, null),
                    getString(R.string.are_you_sure),
                    getString(R.string.cancel),
                    getString(R.string.submit),
                    new Callable<Void>() {
                        @Override
                        public Void call() {
                            Application application = getFormInfo();
                            application.setStatus(FORM_STATUS_SUBMITTED);
                            mPresenter.submitForm(application);
                            return null;
                        }
                    });
        }
    }

    @Override
    public void setPresenter(CreateFormContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void currentUserNull() {
        mPresenter.logout();
    }

    @Override
    public void onLoadNameStart() {
        mLinearLayout.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadNameSuccessful(String name, String id) {
        mStudentName_tv.setText(name);
        mStudentId_tv.setText(id);
        mStudentId = Integer.valueOf(id);
        mLinearLayout.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoadNameFailed(DatabaseError databaseError) {
        mStudentName_tv.setText(R.string.internet_error);
        mStudentId_tv.setText(R.string.internet_error);
        mLinearLayout.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        databaseError.toException().printStackTrace();
    }

    @Override
    public void onFormInfoInvalid(String errorMsg) {
        if (errorMsg.equals(getString(R.string.err_msg_amount_empty)))
            mAmount_et.setError(errorMsg);
        if (errorMsg.equals(getString(R.string.err_msg_amount_low)))
            mAmount_et.setError(errorMsg);
        if (errorMsg.equals(getString(R.string.err_msg_amount_high)))
            mAmount_et.setError(errorMsg);
        if (errorMsg.equals(getString(R.string.err_msg_account_num_empty)))
            mAccountNum_et.setError(errorMsg);
        if (errorMsg.equals(getString(R.string.err_msg_bsb_empty)))
            mBsb_et.setError(errorMsg);
        if (errorMsg.equals(getString(R.string.err_msg_other_usage_empty)))
            mOtherUsage_et.setError(errorMsg);
        if (errorMsg.equals(getString(R.string.err_msg_bsb_wrong)))
            mBsb_et.setError(errorMsg);
        if (errorMsg.equals(getString(R.string.err_msg_account_num_wrong)))
            mAccountNum_et.setError(errorMsg);

        if (errorMsg.equals(getString(R.string.err_msg_usage_not_chosen)))
            ((TextView)mUsageSpinner.getSelectedView()).setError(errorMsg);
        if (errorMsg.equals(getString(R.string.err_msg_loan_period_notZ_chosen)))
            ((TextView)mPeriodYearSpinner.getSelectedView()).setError(errorMsg);
        if (errorMsg.equals(getString(R.string.err_msg_repayment_not_chosen)))
            ((TextView)mRepaymentMonthSpinner.getSelectedView()).setError(errorMsg);
    }

    @Override
    public void onSaveFinished() {
        onBackPressed();
    }

    private Application getFormInfo(){
        String applicationId = "";
        String timeSubmitted = "";
        String timeDeclared = "";
        String status = "";

        String amount = mAmount_et.getText().toString();
        String usage = mUsage;
        String loanPeriod = mLoanPeriod;
        String repaymentPeriod = mRepaymentPeriod;
        String otherUsage = mOtherUsage_et.getText().toString();
        String bankBsb = mBsb_et.getText().toString();
        String bankAccount = mAccountNum_et.getText().toString();

        String rejectReason = "";
        String studentUid = "";
        String staffUid = "";
        int result = -1;

        return new Application(applicationId, timeSubmitted, timeDeclared, status, amount, usage,
                loanPeriod, repaymentPeriod, otherUsage, rejectReason, bankBsb, bankAccount,
                studentUid, staffUid, result);
    }
}
