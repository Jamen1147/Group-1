package com.sep.utsloanapp.models;

public class User {
    private String mUTSId, mUid, mUserType;

    /**
     * Contructor that requires email and uid to build up a user
     * */
    public User(String UTSId, String uid, String userType) {
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

    public String getUserType() {
        return mUserType;
    }

    public String getUTSId() {
        return mUTSId;
    }

    public void setUTSId(String UTSId) {
        mUTSId = UTSId;
    }

    public void setUserType(String userType) {
        mUserType = userType;
    }
}

