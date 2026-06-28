package org.example;//package org.example;
//
//import java.io.BufferedWriter;
//import java.io.FileWriter;
//
///**
// * Hello world!
// *
// */
//
//public class App {
//     void main() throws Exception {
////         Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ind", "root", "0000");
////         if(con != null) {
////             IO.println("Connecting to database...");
////             IO.println("Database Connected!");
////             try {
////                 PreparedStatement st = con.prepareStatement("SELECT * FROM emp");
////                 ResultSet rs = st.executeQuery();
////                 while(rs.next()) {
////                     System.out.println(rs.getInt("id"));
////                     System.out.println(rs.getString("name"));
////                     System.out.println(rs.getString("email"));
////                     System.out.println(rs.getDouble("sal"));
////                 }
////             } catch (SQLException e) {
////                 throw new RuntimeException(e);
////             }
////         }
////         else {
////             IO.println("Database Not Connected!");
////         }
////
////          try {
////              PreparedStatement st = con.prepareStatement("INSERT INTO emp VALUES (?, ?, ?,?)");
////              st.setInt(1, 2);
////              st.setString(2, "John");
////              st.setString(3, "preavinsdl2@gmail.com");
////              st.setDouble(4, 20000.5);
////
////              st.executeUpdate();
////              System.out.println("Data inserted successfully!");
////
////          }
////          catch (SQLException e) {
////              IO.println("SQLException caught!");
////          }
////
//
//         FileWriter fw = new FileWriter("sqldata.csv");
//         try (BufferedWriter writer = new BufferedWriter(fw)) {
//             writer.write(4);
//             writer.newLine();
//
//             writer.write("Name,Age,City");
//             writer.newLine(); // Creates a new line/row
//
//             // 2. Insert your first rows of data
//             writer.write("Alice,28,New York");
//             writer.newLine();
//
//             writer.write("Bob,34,London");
//             writer.newLine();
//
//             writer.write("Charlie,22,Tokyo");
//             writer.newLine();
//
//             IO.println("DATA INJECTION");
//         } catch (RuntimeException e) {
//             throw new RuntimeException(e);
//         }
//    }
//}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CsvToSqlInserter {

    // Database configuration credentials
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/ind";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "0000";

    // File path and batch control settings
    private static final String CSV_FILE_PATH = "sqldata.csv";
    private static final int BATCH_SIZE = 1000;

    public static void main(String[] args) {
        String insertSql = "INSERT INTO users (name, email, age) VALUES (?, ?, ?)";

        // Use try-with-resources to guarantee connection closures
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
             BufferedReader bufferedReader = new BufferedReader(new FileReader(CSV_FILE_PATH))) {

            // 1. Turn off auto-commit for transaction control and batch performance
            connection.setAutoCommit(false);

            String line;
            int rowCount = 0;

            // 2. Skip the header row if your CSV contains one
            String headerLine = bufferedReader.readLine();

            // 3. Process the file line by line to keep memory consumption low
            while ((line = bufferedReader.readLine()) != null) {
                // Split line by comma, considering optional quotes requires regex or a library
                String[] dataColumns = line.split(",");

                // Extract and map columns to parameters
                String name = dataColumns[0].trim();
                String email = dataColumns[1].trim();
                int age = Integer.parseInt(dataColumns[2].trim());

                // 4. Bind parameters securely to the PreparedStatement
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, email);
                preparedStatement.setInt(3, age);

                // 5. Add to the local batch payload
                preparedStatement.addBatch();
                rowCount++;

                // 6. Execute batch periodically when it hits the size limit
                if (rowCount % BATCH_SIZE == 0) {
                    preparedStatement.executeBatch();
                    connection.commit(); // Commit to clear transaction logs
                }
            }

            // 7. Flush out remaining records that didn't hit the exact batch limit
            if (rowCount % BATCH_SIZE != 0) {
                preparedStatement.executeBatch();
                connection.commit();
            }

            System.out.println("Data injection successful! Total records inserted: " + rowCount);

        } catch (IOException e) {
            System.err.println("File Reading Error: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Database Transaction Error: " + e.getMessage());
        }
    }
}
