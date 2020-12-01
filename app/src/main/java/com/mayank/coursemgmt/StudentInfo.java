package com.mayank.coursemgmt;

public class StudentInfo {
    private String FName;
    private String LName;
    private int age;
    private String Branch;
    private String Batch;
    private String StudPass;
    private String StudentID;
    private String UName;

    public StudentInfo() {
    }

    public StudentInfo(String FName, String LName, int age, String branch, String batch, String studPass, String studentID, String UName) {
        this.FName = FName;
        this.LName = LName;
        this.age = age;
        Branch = branch;
        Batch = batch;
        StudPass = studPass;
        StudentID = studentID;
        this.UName = UName;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBranch() {
        return Branch;
    }

    public void setBranch(String branch) {
        Branch = branch;
    }

    public String getBatch() {
        return Batch;
    }

    public void setBatch(String batch) {
        Batch = batch;
    }

    public String getStudPass() {
        return StudPass;
    }

    public void setStudPass(String studPass) {
        StudPass = studPass;
    }

    public String getStudentID() {
        return StudentID;
    }

    public void setStudentID(String studentID) {
        StudentID = studentID;
    }

    public String getUName() {
        return UName;
    }

    public void setUName(String UName) {
        this.UName = UName;
    }
}
