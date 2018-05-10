package Model;

import com.github.javafaker.Faker;

import java.sql.*;


public class Pripojenie {

    public static ResultSet executeQuery(String query) throws SQLException {
        ResultSet rs = null;
        Connection con = createConnection();

        PreparedStatement ps = con.prepareStatement(query);
        try {
            rs = ps.executeQuery();
            con.close();
            return rs;
        }
        catch (Exception e) {}
        con.close();
        return null;
    }

    public static Connection createConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbs_odovzdanie2" , "postgres", "tapkaaa");
            return con;
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return con;
    }
}
