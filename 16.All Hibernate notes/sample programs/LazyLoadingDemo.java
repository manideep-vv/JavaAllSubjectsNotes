package com.tcs.util;

import com.tcs.model.City;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class LazyLoadingDemo {
    @Autowired
    SessionFactory sf;

    @PostConstruct
    private void lazyLoadingDemoInTransaction() {
        Session ses = sf.openSession();
        Transaction tx = ses.getTransaction();
        if (!tx.isActive()) {
            tx = ses.beginTransaction();
            System.out.println("old txn is not active so opening a new transaction");
        }
        City city = ses.load(City.class, 1);
        System.out.println("child is loaded got city name as");
        System.out.println("got city name as "+city.getCityName());
        System.out.println("as we didnt use parent object, no query for state was fired," +
                "\n  but now we are going use that obj and now query will be fired to fetch state");
        System.out.println("state info is  "+city.getState());
    }

}
