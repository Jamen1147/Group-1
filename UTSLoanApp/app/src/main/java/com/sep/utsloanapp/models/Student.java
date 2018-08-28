package com.sep.utsloanapp.models;

/**
 * Model class that forms the Student Object.
 * Descriptions for unclear attributes below.
 * -----------------------------------------
 * Gender - 0 male, 1 female, 2 other
 * -----------------------------------------
 * */
public class Student {

    private String mUid, mFirstName, mLastName, mEmail, mPhoneNumber, mDOB;
    private String mCourse, mMajor, mFaculty, mDegree, mCitizenship;
    private int mStudentId, mGender, mYear;

    public Student() {
    }

    public Student(String uid, String firstName, String lastName, String email, String phoneNumber,
                   String DOB, String course, String major, String faculty, String degree,
                   String citizenship, int studentId, int gender, int year) {
        mUid = uid;
        mFirstName = firstName;
        mLastName = lastName;
        mEmail = email;
        mPhoneNumber = phoneNumber;
        mDOB = DOB;
        mCourse = course;
        mMajor = major;
        mFaculty = faculty;
        mDegree = degree;
        mCitizenship = citizenship;
        mStudentId = studentId;
        mGender = gender;
        mYear = year;
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

    public String getCourse() {
        return mCourse;
    }

    public void setCourse(String course) {
        mCourse = course;
    }

    public String getMajor() {
        return mMajor;
    }

    public void setMajor(String major) {
        mMajor = major;
    }

    public String getFaculty() {
        return mFaculty;
    }

    public void setFaculty(String faculty) {
        mFaculty = faculty;
    }

    public String getDegree() {
        return mDegree;
    }

    public void setDegree(String degree) {
        mDegree = degree;
    }

    public String getCitizenship() {
        return mCitizenship;
    }

    public void setCitizenship(String citizenship) {
        mCitizenship = citizenship;
    }

    public int getStudentId() {
        return mStudentId;
    }

    public void setStudentId(int studentId) {
        mStudentId = studentId;
    }

    public int getGender() {
        return mGender;
    }

    public void setGender(int gender) {
        mGender = gender;
    }

    public int getYear() {
        return mYear;
    }

    public void setYear(int year) {
        mYear = year;
    }
}
