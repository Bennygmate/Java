import java.util.ArrayList;
import java.util.Calendar;

public class Customer {
	
	/**
	 * This method will create a booking
	 * preconditions: That the request is already satisfied
	 * postconditions: Booking has been made
	 * @param DepotList - ArrayList of Depot objects
	 * @param vanBooking - ArrayList of strings of van names assigned to be booked
	 * @param vanNums - Total number of vans to book
	 * @param bookId - Id number of booking
	 * @param startDate - Calendar version of booking starting date
	 * @param endDate - Calendar version of booking starting date
	 */
	public void makeBooking(ArrayList<Depot> DepotList, ArrayList<String> vanBooking, int vanNums, int bookId, Calendar startDate, Calendar endDate){
		Booking makeTime = new Booking(bookId, startDate, endDate); //make new booking
		System.out.print("Booking " + bookId + " ");
		for (Depot d: DepotList) { //curr depot
			boolean sameDepot = false; // assume not in same depot first
			for (Campervan c: d.getCampervans()) { // curr van
				for (String name: vanBooking) { // check in ArrayList of van string names
					if (c.getCampervanName().equals(name) && sameDepot == false) {
						System.out.print(d.getDepotLocation() + " " + name);
						sameDepot = true; // set curr depot as true
						c.addBooking(makeTime);
						vanNums--;
					} else if (c.getCampervanName().equals(name) && sameDepot == true) { //if curr depot still
						System.out.print(", " + name); // print with comma
						c.addBooking(makeTime);
						vanNums--;
					}
				}
			}
			if (sameDepot == true && vanNums != 0) { //if in same depot and all vans printed
				System.out.print("; "); //finish the curr van print in depot
			}
		}
		System.out.println("");
	}
	
	/**
	 * This method will deassign the existing booking, and create a new booking
	 * preconditions: That the request is already satisfied
	 * postconditions: Booking change has been made or cancelled
	 * @param DepotList - ArrayList of Depot objects
	 * @param vanBooking - ArrayList of strings of van names assigned to be booked
	 * @param vanNums - Total number of vans to book
	 * @param bookId - Id number of booking
	 * @param startDate - Calendar version of booking starting date
	 * @param endDate - Calendar version of booking starting date
	 */
	public void changeBooking(ArrayList<Depot> DepotList, ArrayList<String> vanBooking, int vanNums, int bookId, Calendar startDate, Calendar endDate) {
		Booking changedTime = new Booking(bookId, startDate, endDate);
		if(cancelBooking(DepotList, bookId) == true) { // if able to cancel, then add the booking
			System.out.print("Change " + bookId + " ");
			for (Depot d: DepotList) { //curr depot
				boolean sameDepot = false; // assume not in same depot first
				for (Campervan c: d.getCampervans()) { // curr van
					for (String name: vanBooking) { // check in ArrayList of van string names
						if (c.getCampervanName().equals(name) && sameDepot == false) {
							System.out.print(d.getDepotLocation() + " " + name);
							sameDepot = true; // set curr depot as true
							vanNums--;
							c.addBooking(changedTime);
						} else if (c.getCampervanName().equals(name) && sameDepot == true) { //if curr depot still
							System.out.print(", " + name); // print with comma
							vanNums--;
							c.addBooking(changedTime);
						}
					}
				}
				if (sameDepot == true && vanNums != 0) { //if in same depot and all vans printed
					System.out.print("; "); //finish the curr van print in depot
				}
			}
		} else { //If not able to cancel
			System.out.print("Change rejected");
		}
		System.out.println("");
	}

	/**
	 * This method will attempt to cancel the booking ID
	 * @param DepotList - ArrayList of Depot objects
	 * @param bookId - Id number of booking
	 * @return - Return true if booking ID cancelled, false if not
	 */
	public boolean cancelBooking(ArrayList<Depot> DepotList, int bookId){
		boolean removedBooking = false;
		for (Depot d: DepotList) { //curr depot
			for (Campervan c: d.getCampervans()) { // curr van
				for (Booking b: c.getBookings()) { // curr booking
					if (bookId == b.getBookingId()) { // matches the id
						c.removeBooking(b); // remove
						removedBooking = true;
						break;
					}
				}
			}
		}
		if (removedBooking == true) {
			return true;
		} else {
			return false;			
		}
	}
}
