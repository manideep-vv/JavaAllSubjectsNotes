package helpers;

import com.github.javafaker.Faker;

public class EmpFaker {

    public static String getLoginUserName(){
        return  "mani";
    }
    public String getLoginPassword(){
        return "santu";
    }

    public String getEmployeeName(){
        Faker f=new Faker();
        return f.name().firstName().toString();
    }
    public int getEmpNumber(){
        return 15;
    }
    public  static int getEmployeeMarks(){
        return 100;
    }
}
