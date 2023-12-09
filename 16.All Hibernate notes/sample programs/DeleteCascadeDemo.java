package com.tcs.util;

import com.tcs.model.Department;
import com.tcs.model.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class DeleteCascadeDemo {
    @Autowired
    SessionFactory sf;

    @PostConstruct
    public void init() throws InterruptedException {
//        performCollectionMapping();
        CascadeDeletingParent();
//        CascadeDeletingAllChildsAlone();
    }

    /**
     * If u delete parent all childs will be deleted automatically
     */

    private void CascadeDeletingParent() {

        Session ses = sf.openSession();
        Transaction tx = ses.getTransaction();
        if (!tx.isActive()) {
            tx = ses.beginTransaction();
            System.out.println("old txn is not active so opening a new transaction");
        }

        try {
            IntStream.rangeClosed(8, 11).forEach(e -> {

                Department d1 = ses.load(Department.class, e);
                System.out.println("loaded department");
                System.out.println("Employees size is " + d1.getEmployeesList().size());
                ses.delete(d1);
                System.out.println("all objects deleted successfully");
            });
            tx.commit();
        } catch (Exception ex1) {
            tx.rollback();
            System.out.println("transaction rollback");
        }
    }


}
