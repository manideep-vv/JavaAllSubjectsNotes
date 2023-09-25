package com.tcs.SampleRestAPI;

import java.time.LocalDateTime;

public class Employee {
    int empno;
    String empname;
    int marks;
    String doj;

    public Employee() {
    }

    public int getEmpno() {
        return empno;
    }

    public void setEmpno(int empno) {
        this.empno = empno;
    }

    public String getDoj() {
        return doj;
    }

    public void setDoj(String doj) {
        this.doj = doj;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public Employee(int empno, String empname, int marks, String doj) {
        this.empno = empno;
        this.empname = empname;
        this.marks = marks;
        this.doj = doj;
    }

    @Override
    public String toString() {
        return "Student{" +
                "empno=" + empno +
                ", empname='" + empname + '\'' +
                ", marks=" + marks +
                '}';
    }

    public static void main(String[] args) {
        System.out.println( LocalDateTime.now().toString());
    }
}
