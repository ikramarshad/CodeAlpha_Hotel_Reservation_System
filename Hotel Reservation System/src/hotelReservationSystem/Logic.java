package hotelReservationSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Logic extends DatabaseHelper{
     void createReservationsTableIfNotExists() {
        try ( Connection con = getConnection()) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS reservations (id INT AUTO_INCREMENT PRIMARY KEY, check_in_date VARCHAR(20), check_out_date VARCHAR(20), room_category INT)";
            Statement statement = con.createStatement();
            statement.execute(createTableQuery);
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }
      boolean checkRoomAvailability(int roomCategory, String checkInDate, String checkOutDate) {
        boolean isAvailable = false;
        try ( Connection con = getConnection()) {
            String query = "SELECT COUNT(*) AS count FROM reservations WHERE room_category = ? AND ((check_in_date <= ? AND check_out_date >= ?) OR (check_in_date <= ? AND check_out_date >= ?))";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, roomCategory);
            statement.setString(2, checkInDate);
            statement.setString(3, checkInDate);
            statement.setString(4, checkOutDate);
            statement.setString(5, checkOutDate);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                if (count == 0) {
                    isAvailable = true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error checking room availability: " + e.getMessage());
        }
        return isAvailable;
    }
     int makeReservation(String checkInDate, String checkOutDate, int roomCategory) {
        int reservationId = -1;
        try ( Connection con = getConnection()) {
            String query = "INSERT INTO reservations(check_in_date, check_out_date, room_category) VALUES (?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, checkInDate);
            statement.setString(2, checkOutDate);
            statement.setInt(3, roomCategory);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    reservationId = generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error making reservation: " + e.getMessage());
        }
        return reservationId;
    }

     void viewBookingDetails(int reservationId) {
        try ( Connection con = getConnection()) {
            String query = "SELECT * FROM reservations WHERE id = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, reservationId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                System.out.println("\n--- Booking Details ---");
                System.out.println("Reservation ID: " + resultSet.getInt("id"));
                System.out.println("Check-in Date: " + resultSet.getString("check_in_date"));
                System.out.println("Check-out Date: " + resultSet.getString("check_out_date"));
                System.out.println("Room Category: " + resultSet.getInt("room_category"));
            } else {
                System.out.println("Reservation not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error viewing booking details: " + e.getMessage());
        }
    }

	
}
