package org.example;

import java.sql.*;

/**
 * Hello world!
 *
 */
public class App {
    void main() throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ind", "root", "0000");
        PreparedStatement ps = conn.prepareStatement("select * from students");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            IO.println(rs.getInt(1));
            IO.println(rs.getString(2));
            IO.println(rs.getString(3));
            IO.println();
        }
    }
}
