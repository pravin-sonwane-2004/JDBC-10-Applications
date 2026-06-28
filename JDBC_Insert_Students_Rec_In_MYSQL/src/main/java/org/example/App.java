package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * Hello world!
 *
 */
public class App {
    static String url = "jdbc:mysql://localhost:3306/ind";
    static String user = "root";
    static String password = "0000";

    public static void main( String[] args ) throws Exception {
        File file = new File("StudentData.csv");
        BufferedReader br = new BufferedReader(new FileReader(file));
//        BufferedWriter br = new BufferedWriter(new BufferedWriter(new FileWriter(file)));
        Connection conn = DriverManager.getConnection(url,user,password);
        if(conn!=null)
        {
//            if the students database is not present
            String dropSQL = "DROP TABLE IF EXISTS students";
            String createSQL = "CREATE TABLE IF NOT EXISTS students (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "email VARCHAR(100) NOT NULL" +
                    ")";

// Execute dropSQL first, then execute createSQL


            PreparedStatement ps = conn.prepareStatement(dropSQL);
            ps.executeUpdate();
            ps.close();

            ps = conn.prepareStatement(createSQL);
            ps.executeUpdate();   // Create the table
            ps.close();

            PreparedStatement ps2 = conn.prepareStatement("INSERT INTO students VALUES (?,?,?)");
                String line;
             while((line = br.readLine() )!= null) {
                int s1 =  Integer.parseInt(line.split(",")[0]);
                String s2 = line.split(",")[1];
                String s3 =  line.split(",")[2];
                ps2.setInt(1,s1);
                ps2.setString(2,s2);
                ps2.setString(3,s3);
                ps2.executeUpdate();
             }
             IO.println("DATA INSERTED SUCCESSFULLY");
        }
        else {
            IO.println("There is no connection to the database!");
        }
    }
}
