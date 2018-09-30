package com.sep.utsloanapp.activities.mainActivity.staffFragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sep.utsloanapp.R;
import com.sep.utsloanapp.activities.staffAllFormsActivity.StaffAllFormsActivity;
import com.sep.utsloanapp.activities.studentMyFormsActivity.StudentMyFormsActivity;
import com.sep.utsloanapp.activities.utils.Constant;

import org.w3c.dom.Text;

public class StaffFragment extends Fragment implements StaffContract.View, View.OnClickListener{

    private TextView mAllForms_tv, mMyForm_tv;
    private StaffContract.Presenter mPresenter;

    public StaffFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new StaffPresenter(getContext(), this);
        getActivity().setTitle("Staff Review System");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_staff, container, false);
        initView(view);
        mAllForms_tv.setOnClickListener(this);
        mMyForm_tv.setOnClickListener(this);
        return view;
    }

    private void initView(View view) {
        mAllForms_tv = view.findViewById(R.id.staff_fragment_all_application_forms_tv);
        mMyForm_tv = view.findViewById(R.id.staff_fragment_my_forms_tv);
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
    public void onClick(View view) {
        if (view == mAllForms_tv){
            //go to submitted forms activity with a string extra called 'staff_submitted'
            startActivity(new Intent(getActivity(), StaffAllFormsActivity.class));
        }
        if (view == mMyForm_tv){
            //go to my reviewing forms activity with a string extra called 'staff_reviewing'
            Intent intent = new Intent(getActivity(), StudentMyFormsActivity.class);
            intent.putExtra(Constant.TYPE_KEY, Constant.STAFF_VAL);
            startActivity(intent);
        }
    }

    @Override
    public void setPresenter(StaffContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void currentUserNull() {

    }
}
