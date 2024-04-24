package hotelReservationSystem;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseHelper {

	private static final String DB_URL = "jdbc:mysql://localhost:3306/hotelReservation";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    
    

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (Exception e) {
            System.out.println("Unable to connect to the database. Please make sure XAMPP is running.");
            System.out.println("Exception Occurred: " + e.getMessage());
        }
        return connection;
    }

}
