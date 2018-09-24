package com.sep.utsloanapp.activities.staffAllFormsActivity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sep.utsloanapp.R;
import com.sep.utsloanapp.models.Application;

import java.util.ArrayList;

public class StaffApplicationFormListAdapter extends RecyclerView.Adapter<StaffApplicationFormListAdapter.ViewHolder>  {

    private ArrayList<Application> mApplications;
    private StaffAllFormsContract.Presenter mPresenter;
    private Context mContext;

    public StaffApplicationFormListAdapter(ArrayList<Application> applications,
                                           StaffAllFormsContract.Presenter presenter, Context context){

        mApplications = applications;
        mPresenter = presenter;
        mContext = context;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mName_tv, mStudentId_tv, mSubmiteDate_tv, mRefCode_tv, mSubmittedStatus_tv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mName_tv = itemView.findViewById(R.id.list_item_student_name_tv);
            mStudentId_tv = itemView.findViewById(R.id.list_item_student_id_tv);
            mSubmiteDate_tv = itemView.findViewById(R.id.list_item_submit_date_tv);
            mRefCode_tv = itemView.findViewById(R.id.list_item_ref_code_tv);
            mSubmittedStatus_tv = itemView.findViewById(R.id.list_item_submitted_tv);
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
        holder.mSubmiteDate_tv.setText(mApplications.get(i).getTimeSubmitted());
        holder.mRefCode_tv.setText(mApplications.get(i).getApplicationId());

        holder.mSubmittedStatus_tv.setTextColor(mContext.getResources().getColor(R.color.milkGreen));
    }

    @Override
    public int getItemCount() {
        return mApplications.size();
    }
}
