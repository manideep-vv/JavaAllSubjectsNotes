package guru.learningjournal.kafka.examples;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;

public class JacksonDemo {

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Employee emp=new Employee("manideep",12,null);
        String json = mapper.writeValueAsString(emp);
        System.out.println(json);
    }
}
@JsonInclude(JsonInclude.Include.NON_NULL)
class Employee  {
    @JsonProperty("employeeName")
    String empname;
    Integer empid;
    String address;

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public void setEmpid(Integer empid) {
        this.empid = empid;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmpname() {
        return empname;
    }

    public Integer getEmpid() {
        return empid;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empname='" + empname + '\'' +
                ", empid=" + empid +
                ", address='" + address + '\'' +
                '}';
    }

    public Employee(String empname, Integer empid, String address) {
        this.empname = empname;
        this.empid = empid;
        this.address = address;
    }
}