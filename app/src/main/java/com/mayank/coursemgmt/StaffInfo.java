package com.mayank.coursemgmt;

public class StaffInfo {
    private String FName;
    private String LName;
    private String UName;
    private String StaffID;
    private String SBranch;
    private String SPassword;

    public StaffInfo() {
    }

    public StaffInfo(String FName, String LName, String UName, String staffID, String SBranch, String SPassword) {
        this.FName = FName;
        this.LName = LName;
        this.UName = UName;
        StaffID = staffID;
        this.SBranch = SBranch;
        this.SPassword = SPassword;

    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getLName() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }

    public String getUName() {
        return UName;
    }

    public void setUName(String UName) {
        this.UName = UName;
    }

    public String getStaffID() {
        return StaffID;
    }

    public void setStaffID(String staffID) {
        StaffID = staffID;
    }

    public String getSBranch() {
        return SBranch;
    }

    public void setSBranch(String SBranch) {
        this.SBranch = SBranch;
    }

    public String getSPassword() {
        return SPassword;
    }

    public void setSPassword(String SPassword) {
        this.SPassword = SPassword;
    }
}
