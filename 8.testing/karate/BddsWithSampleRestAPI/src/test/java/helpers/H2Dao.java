package helpers;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.sql.DriverManager.getConnection;

public class H2Dao {

    static Connection con =null;
    static {
        System.out.println("loaded static block");
        try {
            con= getConnection("jdbc:mysql://localhost:3306/mani","root","MANIdeep@123");
        } catch (SQLException e) {
            System.out.println("Exception occurred while conectig to databse"+e);
        }
        System.out.println("got connection");
//        loadData(3,"robertt","BLR");
    }
    public static boolean insertData(int id, String name, String address){
        try {
            String sql = "INSERT INTO student VALUES (?, ?, ? )";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,id);
            ps.setString(2,name);
            ps.setString(3,address);
            ps.execute();
            System.out.println("inserted successfully");
        } catch (SQLException e) {
            System.out.println("exception occurred "+e);
            throw new RuntimeException(e);
        }
        return true;
    }
    public static String fetchData(int empid)  {
        JSONObject obj = new JSONObject();

        try {
            String sql = "select * from  student where id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,empid);
            ResultSet rs = ps.executeQuery();
            rs.next();
            obj.put("empid",rs.getInt("id"));
            obj.put("empname",rs.getString("name"));
            obj.put("empaddress",rs.getString("address"));
            return obj.getString("empname");
        }  catch (SQLException | JSONException e) {
        System.out.println("exception occurred "+e);
        throw new RuntimeException(e);
    }
    }


}
