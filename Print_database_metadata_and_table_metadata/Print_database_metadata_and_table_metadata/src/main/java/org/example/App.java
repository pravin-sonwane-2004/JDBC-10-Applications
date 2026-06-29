package org.example;

import java.sql.*;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ind", "root", "0000");
        System.out.println(conn.getMetaData().getDatabaseProductName());
        IO.println(conn.getMetaData().getConnection());
        IO.println(conn.getMetaData().getDatabaseProductName());

        PreparedStatement stmt = conn.prepareStatement("select * from emp");
        ResultSet rs = stmt.executeQuery();
        IO.println(rs.getRow());
        IO.println(rs.getMetaData().getColumnCount());

    }
}
