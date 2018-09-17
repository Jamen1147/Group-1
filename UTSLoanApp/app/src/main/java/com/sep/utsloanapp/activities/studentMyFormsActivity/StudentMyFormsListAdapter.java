package com.sep.utsloanapp.activities.studentMyFormsActivity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sep.utsloanapp.R;
import com.sep.utsloanapp.activities.createFormActivity.CreateFormActivity;
import com.sep.utsloanapp.activities.utils.Constant;
import com.sep.utsloanapp.activities.utils.Utils;
import com.sep.utsloanapp.models.Application;

import java.util.ArrayList;

public class StudentMyFormsListAdapter extends RecyclerView.Adapter<StudentMyFormsListAdapter.ViewHolder> {

    private ArrayList<Application> mApplications;
    private StudentMyFormsContract.Presenter mPresenter;
    private Context mContext;

    public StudentMyFormsListAdapter(ArrayList<Application> applications,
                                     StudentMyFormsContract.Presenter presenter, Context context) {

        mApplications = applications;
        mPresenter = presenter;
        mContext = context;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mName_tv, mStudentId_tv, mSubmiteDate_tv, mRefCode_tv, mSubmitedStatus_tv
                ,mInProcessStatus_tv, mCompleteStatus_tv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mName_tv = itemView.findViewById(R.id.list_item_student_name_tv);
            mStudentId_tv = itemView.findViewById(R.id.list_item_student_id_tv);
            mSubmiteDate_tv = itemView.findViewById(R.id.list_item_submit_date_tv);
            mRefCode_tv = itemView.findViewById(R.id.list_item_ref_code_tv);
            mSubmitedStatus_tv = itemView.findViewById(R.id.list_item_submitted_tv);
            mInProcessStatus_tv = itemView.findViewById(R.id.list_item_in_process_tv);
            mCompleteStatus_tv = itemView.findViewById(R.id.list_item_complete_tv);
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_forms, parent , false);
        final ViewHolder holder = new ViewHolder(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Tell the Presenter to tell the View to go to the corresponding item detail
                //TODO: Pass the whole list and the position using 'holder.getAdapterPosition()'
                mPresenter.goToItemDetail(mApplications.get(holder.getAdapterPosition()));
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

        String name = mApplications.get(i).getStudentFirstName() + " " + mApplications.get(i).getStudentLastName();
        holder.mName_tv.setText(name);

        holder.mStudentId_tv.setText(String.valueOf(mApplications.get(i).getStudentID()));

        String submitDate = mApplications.get(i).getTimeSubmitted();

        if (!TextUtils.isEmpty(submitDate)){
            holder.mSubmiteDate_tv.setText(submitDate);
            holder.mRefCode_tv.setText(mApplications.get(i).getApplicationId());
        }
        else {
            holder.mSubmiteDate_tv.setText(mContext.getResources().getString(R.string.to_be_submitted));
            holder.mSubmiteDate_tv.setTextColor(mContext.getResources().getColor(R.color.colorGreyRed));
            holder.mRefCode_tv.setText(mContext.getResources().getString(R.string.to_be_issued));
            holder.mRefCode_tv.setTextColor(mContext.getResources().getColor(R.color.colorGreyRed));
        }

        String status = mApplications.get(i).getStatus();

        switch (status) {
            case Constant.FORM_STATUS_SUBMITTED:
                //submitted
                holder.mSubmitedStatus_tv.setTextColor(mContext.getResources().getColor(R.color.milkGreen));
                break;
            case Constant.FORM_STATUS_IN_PROCESS:
                holder.mSubmitedStatus_tv.setTextColor(mContext.getResources().getColor(R.color.milkGreen));
                holder.mInProcessStatus_tv.setTextColor(mContext.getResources().getColor(R.color.milkGreen));
                break;
            case Constant.FORM_STATUS_COMPLETE:
                holder.mSubmitedStatus_tv.setTextColor(mContext.getResources().getColor(R.color.milkGreen));
                holder.mInProcessStatus_tv.setTextColor(mContext.getResources().getColor(R.color.milkGreen));
                holder.mCompleteStatus_tv.setTextColor(mContext.getResources().getColor(R.color.milkGreen));
                break;
            default:
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mApplications.size();
    }


}
