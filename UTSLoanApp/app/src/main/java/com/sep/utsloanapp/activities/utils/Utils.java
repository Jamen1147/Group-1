package com.sep.utsloanapp.activities.utils;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sep.utsloanapp.R;

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
    public static void showDialog(Context context, View view, String msg){
        AlertDialog.Builder ab = new AlertDialog.Builder(context);
        ab.setView(view);
        final Dialog dialog = ab.create();
        dialog.show();

        TextView textView = view.findViewById(R.id.msg_dialog_tv);
        textView.setText(msg);

        Button okBtn = view.findViewById(R.id.msg_dialog_ok_btn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}