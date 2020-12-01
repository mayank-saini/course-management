package com.mayank.coursemgmt;

public class Attendance {
    String SubjectName;
    int studentAttendance;
    int totalAttendanceTaken;
    float percentage;

    public Attendance() {
        SubjectName = "";
        studentAttendance = 0;
        totalAttendanceTaken = 0;
    }

    public Attendance(String str) {
        SubjectName = str;
        studentAttendance = 0;
        totalAttendanceTaken = 0;
        percentage = 0;
    }

    public Attendance(String subjectName, int studentAttendance, int totalAttendanceTaken) {
        SubjectName = subjectName;
        this.studentAttendance = studentAttendance;
        this.totalAttendanceTaken = totalAttendanceTaken;
        if (totalAttendanceTaken == 0) {
            percentage = 0;
        } //To address the divide by 0 problem
        else {
            percentage = (float) studentAttendance / totalAttendanceTaken * 100;
        }
    }

    public String getSubjectName() {
        return SubjectName;
    }

    public void setSubjectName(String subjectName) {
        SubjectName = subjectName;
    }

    public int getStudentAttendance() {
        return studentAttendance;
    }

    public void setStudentAttendance(int studentAttendance) {
        this.studentAttendance = studentAttendance;
    }

    public int getTotalAttendanceTaken() {
        return totalAttendanceTaken;
    }

    public void setTotalAttendanceTaken(int totalAttendanceTaken) {
        this.totalAttendanceTaken = totalAttendanceTaken;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

}
