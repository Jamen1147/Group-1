package com.sep.utsloanapp.activities.utils;

import android.content.Context;
import android.widget.Toast;

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

    public static boolean isUserIdValid(String id){
        return id.length() == 8;
    }

    public static boolean isPasswordValid(String password){
        return password.length() > 6;
    }
}