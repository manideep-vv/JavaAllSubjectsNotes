package com.tcs.SampleRestAPI.pojo;

public class Student {
    String studentName;
    int marks;
    Address addrs;

    String gender;
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getMarks() {
        return marks;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }


    public Address getAddrs() {
        return addrs;
    }

    public void setAddrs(Address addrs) {
        this.addrs = addrs;
    }

    public Student(String studentName, int marks, Address addrs, String gender) {
        this.studentName = studentName;
        this.marks = marks;
        this.addrs = addrs;
        this.gender = gender;
    }
}



