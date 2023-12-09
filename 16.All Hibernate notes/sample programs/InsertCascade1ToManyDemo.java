package com.tcs.util;

import com.tcs.model.Department;
import com.tcs.model.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class InsertCascade1ToManyDemo {
    @Autowired
    SessionFactory sf;

    @Autowired
    ApplicationContext ac;

    @PostConstruct
    public void init() throws InterruptedException {
        CascadeInsert1ToMany();
    }

    public void CascadeInsert1ToMany() {
        //Due to cascade 1 to many insert in parent table will insert records in child table also
        //Here dept is parent employee is child
        Department cseDept = Department.builder()
                .deptId(21)
                .deptName("CSE").build();
        Department eceDept= Department.builder()
                .deptId(20)
                .deptName("ECE").build();
        List<Employee> cseList = new ArrayList<>();
        List<Employee> eceList = new ArrayList<>();

        //here we have to set the parent to child, then only in child table that fk identifier will refer to parent table else it will be null
        Employee e = Employee.builder().empName("charan").salary(80000L).dept(cseDept).build();
        Employee e1 = Employee.builder().empName("prakesh").salary(20000L).dept(cseDept).build();
        cseList.add(e); cseList.add(e1);

        Employee e2 = Employee.builder().empName("mani").salary(180000L).dept(eceDept).build();
        Employee e3 = Employee.builder().empName("santhoshi").salary(210000L).dept(eceDept).build();
        eceList.add(e2); eceList.add(e3);

        cseDept.setEmployeesList(cseList);
        eceDept.setEmployeesList(eceList);

        Session ses = sf.openSession();
        Transaction tx = ses.getTransaction();
        if (!tx.isActive()) {
            tx = ses.beginTransaction();
            System.out.println("old txn is not active so opening a new transaction");
        }

        try {
            ses.save(cseDept);
            ses.save(eceDept);
            tx.commit();
            System.out.println("Entire dept with childs is saved");
        } catch (Exception ex1) {
            tx.rollback();
            System.out.println("transaction rollback");
        }


    }

}
