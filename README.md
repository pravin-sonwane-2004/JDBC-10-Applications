# JDBC-10-Applications

Now im going to make 10 applications of jdbc in single repo
i thought making too many repos its not good thing for me

Write a JDBC program to connect to a MySQL database.

import java.sql.*;
# Pravin JDBC Database Example
This class demonstrates how to connect to a MySQL database and retrieve student records using JDBC.

```java
import java.sql.*;

public class Pravin {
    // Main method must be public, static, and Exception capitalized
    public static void main(String[] args) throws Exception { 

        // Define your database connection variables
        String URL = "jdbc:mysql://localhost:3006/your_database";
        String Username = "root";
        String Pass = "password";

        // Establish connection
        Connection cn = DriverManager.getConnection(URL, Username, Pass); 
        
        // Create prepared statement
        PreparedStatement ps = cn.prepareStatement("select * from students"); 
        
        // Execute query
        ResultSet rs = ps.executeQuery(); 
        
        // Loop through results using next()
        while(rs.next()) { 
            // Use System.out.println and pass column names or indices
            System.out.println("Student name : " + rs.getString("name") + 
                               " | Student Roll no : " + rs.getInt("roll_no")); 
        }
        
        // Close resources
        rs.close();
        ps.close();
        cn.close();
    } 
}
```
Write a JDBC program to insert a student record into a database.
Write a JDBC program to display all employee records.
Write a JDBC program to update a student's marks.
Write a JDBC program to delete an employee record.
Write a JDBC program using PreparedStatement to fetch user details.
Write a JDBC program to perform batch insertion.
Write a JDBC program to demonstrate transaction management.
Write a JDBC program to call a stored procedure.
Write a JDBC program to display database metadata.
