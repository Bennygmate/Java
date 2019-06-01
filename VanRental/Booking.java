import java.util.Calendar;
import java.util.GregorianCalendar;

public class Booking implements Comparable<Booking> {
	private int bookId;
	private Calendar startDate = new GregorianCalendar();
	private Calendar endDate = new GregorianCalendar();

	/**
	 * Constructor which sets the bookID
	 * @param bookId - Integer containing ID
	 * @param startDate - Calendar object for start date
	 * @param endDate - Calendar object for end date
	 */
	public Booking(int bookId, Calendar startDate, Calendar endDate) {
		this.bookId = bookId;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	/**
	 * @return - Returns booking ID
	 */
	public int getBookingId() {
		return this.bookId;
	}
	
	/**
	 * @return - Returns start date of booking
	 */
	public Calendar getStartDate() {
		return this.startDate;
	}
		
	/**
	 * @return - Returns end date of booking
	 */
	public Calendar getEndDate() {
		return this.endDate;
	}

	/**
	 * This method compares the startDate of booking objects so they can be sorted
	 * @param object - Booking object
	 * @return - Returns -1 if before, 1 if after, 0 if equal
	 */
	@Override
	public int compareTo(Booking object) {
		if (this.startDate.before(object.startDate)) {
			return -1;
		} else if (this.startDate.after(object.startDate)) {
			return 1;   
		} else  {
			return 0; 
		}
	}
}
