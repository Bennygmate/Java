import java.util.ArrayList;

public class Campervan {
	
	private String vanName;
	private String vanTransmission;
	private ArrayList<Booking> Bookings = new ArrayList<Booking>();

	/**
	 * Constructor sets Campervan name and type
	 * @param vanName - String containing van name
	 * @param vanTransmission - String containing van type
	 */
	public Campervan (String vanName, String vanTransmission) {
		this.vanName = vanName;
		this.vanTransmission = vanTransmission;
	}
	
	/**
	 * @return - Return name of van
	 */
	public String getCampervanName() {
		return this.vanName;
	}
	
	/**
	 * @return - Return type of van
	 */
	public String getCampervanTransmission() {
		return this.vanTransmission;
	}

	/**
	 * @return - Return ArrayList of bookings with the Campervan
	 */
	public ArrayList<Booking> getBookings() {
		return this.Bookings;
	}

	/**
	 * Method adds a booking to the van
	 * @param b - Booking object
	 */
	public void addBooking(Booking b) {
		this.Bookings.add(b);
	}

	/**
	 * Method removes a booking to the van
	 * @param b - Booking object
	 */
	public void removeBooking(Booking b) {
		this.Bookings.remove(b);
	}
}