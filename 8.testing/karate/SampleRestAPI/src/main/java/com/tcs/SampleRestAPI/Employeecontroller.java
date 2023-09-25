package com.tcs.SampleRestAPI;

import com.tcs.SampleRestAPI.pojo.Address;
import com.tcs.SampleRestAPI.pojo.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employeeApi")
public class Employeecontroller {
    List<Employee> empList = new ArrayList<>();
    List<Student> studentsList=new ArrayList<>();
    String token;

    {
        init();
    }

    private void init() {
        token = UUID.randomUUID().toString();
        empList = new ArrayList<>();
        Employee s1 = new Employee(1, "manideep", 200, "2023-09-22T07:33:53.647");
        Employee s2 = new Employee(2, "santu", 300, "2022-09-22T07:33:53.647");
        Employee s3 = new Employee(3, "sailu", 400, "2022-09-22T07:33:53.647");
        empList.add(s1);
        empList.add(s2);
        empList.add(s3);
        empList.add(new Employee(4, "charan", 500,null));
        empList.add(new Employee(5, "ramadevi", 600, "2023-09-22T07:33:53.647"));
        empList.add(new Employee(6, "ranga rao", 700, "2023-09-22T07:33:53.647"));
        Address a=new Address("luxemborg","europe");

        studentsList.add(new Student("manideep",101,new Address("luxemborg","europe"),"male"));
        studentsList.add(new Student("santoor",201,new Address("hyderabad","india"),"male"));
        studentsList.add(new Student("charan",301,new Address("chicago","America"),"male"));
        studentsList.add(new Student("sai",100,new Address(null,"America"),"male"));
    }

    @GetMapping("/getAllEmployees")
    public List<Employee> getAllStudents() {
        return empList;
    }
    @GetMapping("/getEmpNames")
    public List<String> getAllEmpNames() {
        return empList.stream().map(e->e.empname).collect(Collectors.toList());
    }
 @GetMapping("/getStudents")
    public List<Student> getAllStudentNames() {
        return studentsList;
    }

    @GetMapping("/reset")
    public List<Employee> reset() {
        init();
        return empList;
    }

    @PostMapping("/login")
    public ResponseEntity<Auth> login(@RequestBody Auth auth) {
        if (auth.getUser().equals("mani") && auth.getPassword().equals("santu")) {
            auth.setToken(token);
            return ResponseEntity.ok(auth);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/saveEmp")
    public ResponseEntity<List<Employee>> saveEmp(@RequestBody Employee employee, @RequestHeader("Authorization") String tokenPassed) {
        ResponseEntity<List<Employee>> authStatus = performVaidation(tokenPassed);
        if (authStatus != null) {
            return authStatus;
        }
        empList.add(employee);
        return ResponseEntity.ok(empList);
    }

    @DeleteMapping("/deleteEmp")
    public ResponseEntity<List<Employee>> delete(@RequestBody Employee employee, @RequestHeader("Authorization") String tokenPassed) {
        ResponseEntity<List<Employee>> authStatus = performVaidation(tokenPassed);
        if (authStatus != null) {
            return authStatus;
        }
        boolean flag=false;
        for(int i = 0; i< empList.size(); i++){
            if (empList.get(i).getEmpno()== employee.getEmpno()){
                flag=true;
                empList.remove(i);
                break;
            }
        }
        if(flag==true){
            return ResponseEntity.ok(empList);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    private ResponseEntity<List<Employee>> performVaidation(String tokenPassed) {
        if (!tokenPassed.equals(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return null;
    }

}


