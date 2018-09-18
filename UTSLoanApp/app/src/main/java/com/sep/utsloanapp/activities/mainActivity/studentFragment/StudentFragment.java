package com.sep.utsloanapp.activities.mainActivity.studentFragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sep.utsloanapp.R;
import com.sep.utsloanapp.activities.createFormActivity.CreateFormActivity;
import com.sep.utsloanapp.activities.guideActivity.GuideActivity;
import com.sep.utsloanapp.activities.staffLoginActivity.StaffLoginActivity;
import com.sep.utsloanapp.activities.studentMyFormsActivity.StudentMyFormsActivity;
import com.sep.utsloanapp.activities.utils.Constant;
import com.sep.utsloanapp.activities.utils.Utils;

public class StudentFragment extends Fragment implements StudentContract.View, View.OnClickListener{

    private StudentContract.Presenter mPresenter;
    private TextView mCreateForm_tv, mMyForms_tv, mGuidance_tv;
    int mAvailable = 0;

    public StudentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new StudentPresenter(getContext(), this);
        getActivity().setTitle("Welcome Student");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_student, container, false);
        initView(view);

        mCreateForm_tv.setOnClickListener(this);
        mMyForms_tv.setOnClickListener(this);
        mGuidance_tv.setOnClickListener(this);

        //check if the student can create one more form
        mPresenter.checkAvailable();

        return view;
    }

    private void initView(View view) {
        mCreateForm_tv = view.findViewById(R.id.student_fragment_creation_application_tv);
        mMyForms_tv = view.findViewById(R.id.student_fragment_my_application_tv);
        mGuidance_tv = view.findViewById(R.id.student_fragment_guidance_tv);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        if (v == mCreateForm_tv){
            //go to create form activity
            if (mAvailable == Constant.DISABLE_VAL){
                Utils.showMsgDialog(getActivity(), getLayoutInflater().inflate(R.layout.dialog_msg_dialog, null),
                        getString(R.string.reminder),
                        getString(R.string.form_more_than_one_error_msg));
            }else if (mAvailable == Constant.ENABLE_VAL){
                startActivity(new Intent(getActivity(), CreateFormActivity.class));
            }
        }

        if (v == mMyForms_tv){
            startActivity(new Intent(getActivity(), StudentMyFormsActivity.class));
        }

        if (v == mGuidance_tv){
            //go to guidance
            startActivity(new Intent(getActivity(), GuideActivity.class));
        }
    }

    @Override
    public void setPresenter(StudentContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void currentUserNull() {
    }

    @Override
    public void onGetAvailableSuccessful(int available) {
        mAvailable = available;
    }
}
