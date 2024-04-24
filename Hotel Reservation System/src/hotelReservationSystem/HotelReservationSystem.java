package hotelReservationSystem;

import java.util.Scanner;

public class HotelReservationSystem {


	public static void main(String[] args) {
	    Logic logic = new Logic();
	    logic.createReservationsTableIfNotExists();
	    Scanner scanner = new Scanner(System.in);

	    boolean continueReserving = true;

	    while (continueReserving) {
	        System.out.println("Welcome to the Hotel Reservation System!");
	        System.out.print("Enter check-in date (MM/DD/YYYY): ");
	        String checkInDate = scanner.nextLine();

	        System.out.print("Enter check-out date (MM/DD/YYYY): ");
	        String checkOutDate = scanner.nextLine();

	        System.out.println("Available Room Categories:");
	        System.out.println("1. Standard Room");
	        System.out.println("2. Deluxe Room");
	        System.out.println("3. Suite");

	        System.out.print("Select room category: ");
	        int roomCategory = scanner.nextInt();
	        scanner.nextLine(); // Consume newline character

	        boolean isRoomAvailable = logic.checkRoomAvailability(roomCategory, checkInDate, checkOutDate);

	        if (isRoomAvailable) {
	            System.out.println("Room available! Proceed with reservation.");
	            int reservationId = logic.makeReservation(checkInDate, checkOutDate, roomCategory);
	            logic.viewBookingDetails(reservationId);
	        } else {
	            System.out.println("Room not available for the selected dates. Please try again.");
	        }

	        System.out.print("Do you want to make another reservation? (Y/N): ");
	        String choice = scanner.nextLine();
	        if (!choice.equalsIgnoreCase("Y")) {
	            continueReserving = false;
	        }
	    }

	    System.out.println("Thank you for using the Hotel Reservation System!");
	    scanner.close();
	}



}