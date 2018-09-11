package com.sep.utsloanapp.models;

import android.util.Log;

/**
 * Model class that forms the User Object.
 * Descriptions for unclear attributes below.
 * -----------------------------------------
 * UserType - 0 Student, 1 Staff
 * -----------------------------------------
 * */
public class User {
    private String mUTSId, mUid;
    private int mUserType;

    /**
     * Contructor that requires email and uid to build up a user
     * */
    public User(String UTSId, String uid, int userType) {
        mUTSId = UTSId;
        mUid = uid;
        mUserType = userType;
    }

    public User(){}

    /**
     * Getter and Setter methods below for accessing and modifying attributes
     * */

    public String getUid() {
        return mUid;
    }

    public void setUid(String uid) {
        mUid = uid;
    }

    public int getUserType() {
        return mUserType;
    }

    public String getUTSId() {
        return mUTSId;
    }

    public void setUTSId(String UTSId) {
        mUTSId = UTSId;
    }

    public void setUserType(int userType) {
        mUserType = userType;
    }
}

