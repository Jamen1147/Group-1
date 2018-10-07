package com.sep.utsloanapp.activities.utils;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sep.utsloanapp.R;
import com.sep.utsloanapp.activities.applicationDetailActivity.ApplicationDetailActivity;
import com.sep.utsloanapp.models.Application;
import com.sep.utsloanapp.models.Student;

import java.lang.invoke.MethodHandle;
import java.util.concurrent.Callable;

public class Utils {

    /**
     * Shows the message
     */
    public static void showMsg(Context c, String msg) {
        Toast.makeText(c, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * Checks if the target is a valid email address
     */
    public static boolean isValidEmail(CharSequence target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    /**
     * Return true only when id.length == 8
     */
    public static boolean isUserIdValid(String id){
        return id.length() == 8;
    }

    /**
     * Return true only when password.length >= 6 or password.length <= 20
     */
    public static boolean isPasswordValid(String password){
        return password.length() >= 6 && password.length() <= 20;
    }

    /**
     * Show a simple alert dialog.
     */
    public static void showMsgDialog(Context context, View view, String title, String msg){
        AlertDialog.Builder ab = new AlertDialog.Builder(context);
        ab.setView(view);
        final Dialog dialog = ab.create();
        dialog.show();

        TextView msg_tv = view.findViewById(R.id.msg_dialog_tv);
        msg_tv.setText(msg);
        TextView title_tv = view.findViewById(R.id.msg_dialog_title_tv);
        title_tv.setText(title);

        Button okBtn = view.findViewById(R.id.msg_dialog_ok_btn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    /**
     * Show a confirm dialog.
     * Pass in: context, view, confirm msg, cancel button msg, confirm button msg,
     * and a callable to handle the confirm button click event.
     */
    public static void showConfirmDialog(Context context, View view, String title, String confirmMsg,
                                         String cancelBtnMsg, String confirmBtnMsg,
                                         final Callable<Void> method){

        AlertDialog.Builder ab = new AlertDialog.Builder(context);
        ab.setView(view);
        final Dialog dialog = ab.create();
        dialog.show();

        TextView msg_tv = view.findViewById(R.id.dialog_confirm_dialog_confirm_msg_tv);
        msg_tv.setText(confirmMsg);
        TextView title_tv = view.findViewById(R.id.confirm_dialog_title_tv);
        title_tv.setText(title);

        Button cancelBtn = view.findViewById(R.id.dialog_confirm_dialog_cancel_btn);
        cancelBtn.setText(cancelBtnMsg);

        Button confirmBtn = view.findViewById(R.id.dialog_confirm_dialog_confirm_btn);
        confirmBtn.setText(confirmBtnMsg);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    dialog.dismiss();
                    method.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void showRejectReasonDialog(Context context, View view, String title, String confirmMsg,
                                         String cancelBtnMsg, String confirmBtnMsg,
                                         final Callable<Void> method){

        AlertDialog.Builder ab = new AlertDialog.Builder(context);
        ab.setView(view);
        final Dialog dialog = ab.create();
        dialog.show();

        TextView msg_tv = view.findViewById(R.id.dialog_confirm_dialog_confirm_msg_tv);
        msg_tv.setText(confirmMsg);
        TextView title_tv = view.findViewById(R.id.confirm_dialog_title_tv);
        title_tv.setText(title);

        Button cancelBtn = view.findViewById(R.id.dialog_confirm_dialog_cancel_btn);
        cancelBtn.setText(cancelBtnMsg);

        final Button confirmBtn = view.findViewById(R.id.dialog_confirm_dialog_confirm_btn);
        confirmBtn.setText(confirmBtnMsg);

        EditText editText = view.findViewById(R.id.reason_et);
        final String reason = editText.getText().toString();

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (reason.length() < 50){
                        confirmBtn.setError("Reject reason cannot be less than 50 characters");
                    }else {
                        dialog.dismiss();
                        ApplicationDetailActivity activity = new ApplicationDetailActivity();
                        activity.setReason(reason);
                        method.call();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void showTwoPickOneDialog(Context context, View view, String title, String confirmMsg,
                                         String optionOneBtnMsg, String optionTwoBtnMsg,
                                         final Callable<Void> method, final Callable<Void> method2){

        AlertDialog.Builder ab = new AlertDialog.Builder(context);
        ab.setView(view);
        final Dialog dialog = ab.create();
        dialog.show();

        TextView msg_tv = view.findViewById(R.id.dialog_confirm_dialog_confirm_msg_tv);
        msg_tv.setText(confirmMsg);
        TextView title_tv = view.findViewById(R.id.confirm_dialog_title_tv);
        title_tv.setText(title);

        Button optionOneBtn = view.findViewById(R.id.dialog_confirm_dialog_cancel_btn);
        optionOneBtn.setText(optionOneBtnMsg);

        Button optionTwoBtn = view.findViewById(R.id.dialog_confirm_dialog_confirm_btn);
        optionTwoBtn.setText(optionTwoBtnMsg);

        optionOneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    dialog.dismiss();
                    method.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        optionTwoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    dialog.dismiss();
                    method2.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public static void sendReviewEmail(Application application, Student student){
        String to = student.getEmail();
        String subject = "UTS Loan Application Status Changed";

        String name = student.getFirstName();

        String msgEnding = "UTS CRICOS Provider Code: 00099F DISCLAIMER: This email message and any accompanying attachments may contain confidential information. If you are not the intended recipient, do not read, use, disseminate, distribute or copy this message or attachments. If you have received this message in error, please notify the sender immediately and delete this message. Any views expressed in this message are those of the individual sender, except where the sender expressly, and with authority, states them to be the views of the University of Technology Sydney. Before opening any attachments, please check them for viruses and defects. Think. Green. Do. Please consider the environment before printing this email.";

        String msg = "Dear " + name + ",\n\n" + "Your student loan application is now in process, " +
                "if you have any questions regarding this, please refer to UTS Center with the following Ref Code provided.\n\n"
                + "Ref Code: " + application.getApplicationId() + "\n\nKindest regards, \n\nUTS Student Loan Application Team\n\n" +
                msgEnding;

        Email email = new Email(to, subject, msg);
        email.execute();
    }

    public static void sendResultEmail(Application application, Student student){
        String to = student.getEmail();
        String subject = "UTS Loan Application Result";
        String name = student.getFirstName();
        String msgEnding = "UTS CRICOS Provider Code: 00099F DISCLAIMER: This email message and any accompanying attachments may contain confidential information. If you are not the intended recipient, do not read, use, disseminate, distribute or copy this message or attachments. If you have received this message in error, please notify the sender immediately and delete this message. Any views expressed in this message are those of the individual sender, except where the sender expressly, and with authority, states them to be the views of the University of Technology Sydney. Before opening any attachments, please check them for viruses and defects. Think. Green. Do. Please consider the environment before printing this email.";
        String msg;

        if (application.getResult() == Constant.RESULT_APPROVED){
           msg = "Dear " + name + ",\n\n" + "Congratulations! Your student loan application (Ref Code: " + application.getApplicationId() + ") has been approved. " +
                   "The loan will be available within 5 working days. If you have any question, " +
                   "please refer the UTS center with the Ref Code provided.\n\n" +
                   "Kindest regards,\n\n" + "" +
                   "UTS Student Loan Application Team\n\n" + msgEnding;
        }else {
            msg = "Dear " + name + ",\n\n" + "Sorry! Your student loan application (Ref Code: " + application.getApplicationId() + ") has been rejected. " +
                    "If you have any question, " +
                    "please refer the UTS center with the Ref Code provided.\n\n" +
                    "Kindest regards,\n\n" +
                    "UTS Student Loan Application Team\n\n" + msgEnding;
        }

        Email email = new Email(to, subject, msg);
        email.execute();
    }
}