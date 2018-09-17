package com.sep.utsloanapp.activities.studentPersonalDetailActivity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sep.utsloanapp.R;
import com.sep.utsloanapp.models.Student;

public class StudentPersonalDetailActivity extends AppCompatActivity implements
                                    StudentPersonalDetailContract.View, View.OnClickListener{

    private TextView mFirstName_tv, mLastName_tv, mStudentId_tv, mGender_tv, mDob_tv, mCitizenship_tv,
                        mEmail_tv, mPhoneNum_tv, mCourse_tv, mMajor_tv, mFaculty_tv, mDegree_tv, mYear_tv;

    private Button mButton;

    private StudentPersonalDetailContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_personal_detail);
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
        mButton.setOnClickListener(this);

        mPresenter = new StudentPersonalDetailPresenter(this, this);

        if (getIntent().getExtras() != null){
            mPresenter.unwrapData(getIntent().getExtras());
        }
    }

    private void init() {
        mFirstName_tv = findViewById(R.id.student_personal_information_first_name);
        mLastName_tv = findViewById(R.id.student_personal_information_last_name);
        mStudentId_tv = findViewById(R.id.student_personal_information_student_id);
        mGender_tv = findViewById(R.id.student_personal_information_gender);
        mDob_tv = findViewById(R.id.student_personal_information_date_of_birth);
        mCitizenship_tv = findViewById(R.id.student_personal_information_citizenship);
        mEmail_tv = findViewById(R.id.student_personal_information_email_address);
        mPhoneNum_tv = findViewById(R.id.student_personal_information_phone_number);
        mCourse_tv = findViewById(R.id.student_personal_information_course);
        mMajor_tv = findViewById(R.id.student_personal_information_major);
        mFaculty_tv = findViewById(R.id.student_personal_information_faculty);
        mDegree_tv = findViewById(R.id.student_personal_information_degree);
        mYear_tv = findViewById(R.id.student_personal_information_year_of_student);
        mButton = findViewById(R.id.student_personal_information_back_btn);
    }

    @Override
    public void onClick(View v) {
        if (v == mButton){
            onBackPressed();
        }
    }

    @Override
    public void setPresenter(StudentPersonalDetailContract.Presenter presenter) {
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
    public void setTextViews(Student student) {
        Log.d("test", "doing");
        mFirstName_tv.setText(student.getFirstName());
        mLastName_tv.setText(student.getLastName());
        mStudentId_tv.setText(String.valueOf(student.getStudentId()));

        if (student.getGender() == 0){
            mGender_tv.setText("Male");
        }else {
            mGender_tv.setText("Female");
        }

        mDob_tv.setText(student.getDOB());
        mCitizenship_tv.setText(student.getCitizenship());
        mEmail_tv.setText(student.getEmail());
        mPhoneNum_tv.setText(student.getPhoneNumber());
        mCourse_tv.setText(student.getCourse());
        mMajor_tv.setText(student.getMajor());
        mFaculty_tv.setText(student.getFaculty());
        mDegree_tv.setText(student.getDegree());
        mYear_tv.setText(String.valueOf(student.getYear()));
    }
}
