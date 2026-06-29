package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Hello world!
 *
 */
public class App {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/ind";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "0000";

    public static void main(String [] args) throws SQLException {
        int pageSize = 5;     // number of records per page
        int pageNumber = 1;   // change this (1, 2, 3...)

        int offset = (pageNumber - 1) * pageSize;

        Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);

        PreparedStatement ps = conn.prepareStatement(
                "SELECT id, name, email FROM students LIMIT ? OFFSET ?"
        );

        ps.setInt(1, pageSize);
        ps.setInt(2, offset);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            System.out.println(rs.getInt("id"));
            System.out.println(rs.getString("name"));
            System.out.println(rs.getString("email"));
            System.out.println("-------------------");
        }
    }
}
