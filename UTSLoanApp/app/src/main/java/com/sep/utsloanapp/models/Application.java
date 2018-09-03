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
            mOtherUsage, mRejectReason;

    private int mStudentId, mStaffId, mLoanPeriodYear, mRepaymentPeriodMonth, mResult;

    public Application() {
    }

    public Application(String applicationId, String timeSubmitted, String timeDeclared,
                       String status, String amount, String usage, String otherUsage,
                       String rejectReason, int studentId, int staffId, int loanPeriodYear,
                       int repaymentPeriodMonth, int result) {

        mApplicationId = applicationId;
        mTimeSubmitted = timeSubmitted;
        mTimeDeclared = timeDeclared;
        mStatus = status;
        mAmount = amount;
        mUsage = usage;
        mOtherUsage = otherUsage;
        mRejectReason = rejectReason;
        mStudentId = studentId;
        mStaffId = staffId;
        mLoanPeriodYear = loanPeriodYear;
        mRepaymentPeriodMonth = repaymentPeriodMonth;
        mResult = result;

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

    public int getStudentId() {
        return mStudentId;
    }

    public void setStudentId(int studentId) {
        mStudentId = studentId;
    }

    public int getStaffId() {
        return mStaffId;
    }

    public void setStaffId(int staffId) {
        mStaffId = staffId;
    }

    public int getLoanPeriodYear() {
        return mLoanPeriodYear;
    }

    public void setLoanPeriodYear(int loanPeriodYear) {
        mLoanPeriodYear = loanPeriodYear;
    }

    public int getRepaymentPeriodMonth() {
        return mRepaymentPeriodMonth;
    }

    public void setRepaymentPeriodMonth(int repaymentPeriodMonth) {
        mRepaymentPeriodMonth = repaymentPeriodMonth;
    }

    public int getResult() {
        return mResult;
    }

    public void setResult(int result) {
        mResult = result;
    }
}
