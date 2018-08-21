package com.sep.utsloanapp.activities.logoActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.sep.utsloanapp.R;
import com.sep.utsloanapp.activities.staffLoginActivity.StaffLoginActivity;
import com.sep.utsloanapp.activities.studentLoginActivity.StudentLoginActivity;

public class LogoPageActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mStudentBtn, mStaffBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitleTextColor(getResources().getColor(R.color.colorGrey));

        init();

        mStudentBtn.setOnClickListener(this);
        mStaffBtn.setOnClickListener(this);
    }

    private void init(){
        mStudentBtn = findViewById(R.id.logo_student_btn);
        mStaffBtn = findViewById(R.id.logo_staff_btn);
    }

    @Override
    public void onClick(View view) {
        if (view == mStudentBtn){
            startActivity(new Intent(this, StudentLoginActivity.class));
        }
        
        if (view == mStaffBtn){
            startActivity(new Intent(this, StaffLoginActivity.class));
        }
    }

}
