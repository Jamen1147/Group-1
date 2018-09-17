package com.sep.utsloanapp.models;

/**
 * Model class that forms the Application Object.
 * Descriptions for unclear attributes below.
 * -----------------------------------------
 * LoanPeriodYear - 1 Year to 4 Year
 * RepaymentPeriodMonth - 6 month to 60 month
 * Result - 0 rejected, 1 approved
 * Status - "Submitted", "Processing", "Approved or Rejected"
 * -----------------------------------------
 * */

public class Application {

    private String mApplicationId, mTimeSubmitted, mTimeDeclared, mStatus, mAmount, mUsage,
            mLoanPeriodYear, mRepaymentPeriodMonth, mOtherUsage, mRejectReason, mBankBsb,
            mBankAccount, mStudentUid, mStaffUid, mStudentFirstName, mStudentLastName;

    private int mStudentID, mResult;

    public Application() {
    }

    public Application(String applicationId, String timeSubmitted, String timeDeclared,
                       String status, String amount, String usage, String loanPeriodYear,
                       String repaymentPeriodMonth, String otherUsage,
                       String rejectReason, String bankBsb, String bankAccount,
                       String studentUid, String staffUid, String studentFirstName, String studentLastName,
                       int studentID, int result) {

        mApplicationId = applicationId;
        mTimeSubmitted = timeSubmitted;
        mTimeDeclared = timeDeclared;
        mStatus = status;
        mAmount = amount;
        mUsage = usage;
        mOtherUsage = otherUsage;
        mRejectReason = rejectReason;
        mLoanPeriodYear = loanPeriodYear;
        mRepaymentPeriodMonth = repaymentPeriodMonth;
        mResult = result;
        mBankAccount = bankAccount;
        mBankBsb = bankBsb;
        mStudentUid = studentUid;
        mStaffUid = staffUid;
        mStudentFirstName = studentFirstName;
        mStudentLastName = studentLastName;
        mStudentID = studentID;

    }

    public String getStudentFirstName() {
        return mStudentFirstName;
    }

    public void setStudentFirstName(String studentFirstName) {
        mStudentFirstName = studentFirstName;
    }

    public String getStudentLastName() {
        return mStudentLastName;
    }

    public void setStudentLastName(String studentLastName) {
        mStudentLastName = studentLastName;
    }

    public int getStudentID() {
        return mStudentID;
    }

    public void setStudentID(int studentID) {
        mStudentID = studentID;
    }

    public String getStudentUid() {
        return mStudentUid;
    }

    public void setStudentUid(String studentUid) {
        mStudentUid = studentUid;
    }

    public String getStaffUid() {
        return mStaffUid;
    }

    public void setStaffUid(String staffUid) {
        mStaffUid = staffUid;
    }

    public String getBankBsb() {
        return mBankBsb;
    }

    public void setBankBsb(String bankBsb) {
        mBankBsb = bankBsb;
    }

    public String getBankAccount() {
        return mBankAccount;
    }

    public void setBankAccount(String bankAccount) {
        mBankAccount = bankAccount;
    }

    public String getApplicationId() {
        return mApplicationId;
    }

    public void setApplicationId(String applicationId) {
        mApplicationId = applicationId;
    }

    public String getTimeSubmitted() {
        return mTimeSubmitted;
    }

    public void setTimeSubmitted(String timeSubmitted) {
        mTimeSubmitted = timeSubmitted;
    }

    public String getTimeDeclared() {
        return mTimeDeclared;
    }

    public void setTimeDeclared(String timeDeclared) {
        mTimeDeclared = timeDeclared;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getAmount() {
        return mAmount;
    }

    public void setAmount(String amount) {
        mAmount = amount;
    }

    public String getUsage() {
        return mUsage;
    }

    public void setUsage(String usage) {
        mUsage = usage;
    }

    public String getOtherUsage() {
        return mOtherUsage;
    }

    public void setOtherUsage(String otherUsage) {
        mOtherUsage = otherUsage;
    }

    public String getRejectReason() {
        return mRejectReason;
    }

    public void setRejectReason(String rejectReason) {
        mRejectReason = rejectReason;
    }

    public String getLoanPeriodYear() {
        return mLoanPeriodYear;
    }

    public void setLoanPeriodYear(String loanPeriodYear) {
        mLoanPeriodYear = loanPeriodYear;
    }

    public String getRepaymentPeriodMonth() {
        return mRepaymentPeriodMonth;
    }

    public void setRepaymentPeriodMonth(String repaymentPeriodMonth) {
        mRepaymentPeriodMonth = repaymentPeriodMonth;
    }

    public int getResult() {
        return mResult;
    }

    public void setResult(int result) {
        mResult = result;
    }
}
