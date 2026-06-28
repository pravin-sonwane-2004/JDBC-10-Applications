package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * Hello world!
 *
 */
public class App {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/ind";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "0000";


    public static void main( String[] args ) throws Exception {
        try {
            Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement("update students set email = 'pravinson@gami.com' where id = ?");
            preparedStatement.setInt(1,1);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
