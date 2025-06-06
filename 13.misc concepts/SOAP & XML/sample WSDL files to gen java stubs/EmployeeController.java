package com.ampf.soapDemo.controller;

import com.ampf.soapDemo.dto.Employee;
import org.apache.cxf.feature.Features;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.Collections;
import java.util.List;

@WebService
@Component
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class EmployeeController {
    List<Employee> employeeList;
    @WebMethod
    public String empDetails(String empName){
        return "Best Employee name is santhoshi and " + empName;
    }

    @WebMethod
    public List<Employee> getAllEmployees(){
        return employeeList;
    }

    @PostConstruct
    public  void init(){
        System.out.println("firing init method");
        employeeList=List.of(
                new Employee("mohan",200),
                new Employee("mani",201));
    }
}
