package com.sep.utsloanapp.models;

/**
 * Model class that forms the Staff Object.
 * Descriptions for unclear attributes below.
 * -----------------------------------------
 * Gender - 0 male, 1 female, 2 other
 * -----------------------------------------
 * */
public class Staff {

    private String mUid, mFirstName, mLastName, mEmail, mPhoneNumber, mDOB;
    private String mDateEmployed;
    private int mStaffId, mGender, mTimesApplicationDeclared;

    public Staff() {
    }

    public Staff(String uid, String firstName, String lastName, String email, String phoneNumber,
                 String DOB, String dateEmployed, int staffId, int gender,
                 int timesApplicationDeclared) {
        mUid = uid;
        mFirstName = firstName;
        mLastName = lastName;
        mEmail = email;
        mPhoneNumber = phoneNumber;
        mDOB = DOB;
        mDateEmployed = dateEmployed;
        mStaffId = staffId;
        mGender = gender;
        mTimesApplicationDeclared = timesApplicationDeclared;
    }

    public String getUid() {
        return mUid;
    }

    public void setUid(String uid) {
        mUid = uid;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public String getDOB() {
        return mDOB;
    }

    public void setDOB(String DOB) {
        mDOB = DOB;
    }

    public String getDateEmployed() {
        return mDateEmployed;
    }

    public void setDateEmployed(String dateEmployed) {
        mDateEmployed = dateEmployed;
    }

    public int getStaffId() {
        return mStaffId;
    }

    public void setStaffId(int staffId) {
        mStaffId = staffId;
    }

    public int getGender() {
        return mGender;
    }

    public void setGender(int gender) {
        mGender = gender;
    }

    public int getTimesApplicationDeclared() {
        return mTimesApplicationDeclared;
    }

    public void setTimesApplicationDeclared(int timesApplicationDeclared) {
        mTimesApplicationDeclared = timesApplicationDeclared;
    }
}
