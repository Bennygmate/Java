/**
 * COMP2911 Assignment 1
 * @author Benjamin Cheung
 * 
 */
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Scanner;
/**
 * Main java file for the Van Rental System.
 * Usage: java VanRentalSystem input.txt
 */

public class VanRentalSystem {
	private ArrayList<Depot> Depots;
	private Customer customer;
	private ArrayList<String> CampervanNames = new ArrayList<String>();
	
	/**
	 * Constructor which sets the customer and depot
	 */
	public VanRentalSystem() {
		this.customer = new Customer();
		this.Depots = new ArrayList<Depot>();
	}
	
	public static void main(String[] args){
		VanRentalSystem vrs = new VanRentalSystem();
		vrs.processInput(args);
	}
	
	/**
	 * Method which processes input from txt file matching commandline arguments
	 * preconditions: VanRentalSystem has been created, file has been given in the command line
	 * @param args - Argument given in the command line
	 */
	public void processInput(String[] args) {
		Scanner sc = null;
		try {
			sc = new Scanner(new FileReader(args[0]));
			while(sc.hasNextLine()) {
				String[] line = sc.nextLine().split(" "); // splits according to space
				if (line[0].equals("Location")) {
					addLocation(Depots, line[1], line[2], line[3]); //line[1,2,3] is depot, name, type
				} else if (line[0].equals("Request") || line[0].equals("Change")) {
					int bookId = Integer.parseInt(line[1]); //id
					int startHour = Integer.parseInt(line[2]); //hour1
					String startMonth = line[3]; //month1
					int startDay = Integer.parseInt(line[4]); //date1
					int endHour = Integer.parseInt(line[5]); //hour2
					String endMonth = line[6]; //month2
					int endDay = Integer.parseInt(line[7]); //date2
					Calendar startDate = setDate(startHour, startMonth, startDay); // Calendar version of the time
					Calendar endDate = setDate(endHour, endMonth, endDay); // Calendar version of the time
					int num1 = Integer.parseInt(line[8]); //num1
					String type1 = line[9]; //type1
					int num2; //num2
					String type2; //type2
					try {
						if (line[10].contains("#")) {
					        num2 = 0;                          
					        type2 = null;							
						} else {
					       	num2 = Integer.parseInt(line[10]);  
					        type2 = line[11];     							
						}                
				    } catch (IndexOutOfBoundsException r){                
				        num2 = 0;                          
				        type2 = null;
				    }
					int vanNums = num1 + num2; //total num of vans to book
					if (line[0].equals("Request")) {
						if (bookingFulfill(Depots, startDate, endDate, num1, type1, num2, type2) == true) {
							customer.makeBooking(Depots, CampervanNames, vanNums, bookId, startDate, endDate);
						} else {
							System.out.println("Booking rejected");
						}					
					} else if (line[0].equals("Change")) {
						if (bookingFulfill(Depots, startDate, endDate, num1, type1, num2, type2) == true) {
							customer.changeBooking(Depots, CampervanNames, vanNums, bookId, startDate, endDate);
						} else {
							System.out.println("Change rejected");
						}
					}
				} else if (line[0].equals("Cancel")) { 
					if (customer.cancelBooking(Depots, Integer.parseInt(line[1])) == true) {
						System.out.println("Cancel " + line[1]); //line[1] is the bookID
					} else {
						System.out.println("Cancel rejected");
					}   
				} else if (line[0].equals("Print")) {
					print(Depots, line[1]);
				}
			}
		}
		catch (FileNotFoundException e){}
		finally {
			if (sc != null) sc.close();
		}	 		
	}
	
	/**
	 * Method which adds the depots and campervans to the object
	 * @param depots - ArrayList of depot
	 * @param newDepot - String location of depot to be added
	 * @param vanName - String name of campervan to be added
	 * @param vanType - String type of campervan to be added
	 */
	public void addLocation (ArrayList<Depot> depots, String newDepot, String vanName, String vanType) {
		boolean depotinList = false;
	  		Depot nd = new Depot(newDepot); 
	  		Campervan c = new Campervan(vanName, vanType); 	
			for (Depot d: Depots) {
		 		if(nd.getDepotLocation().equals(d.getDepotLocation())) { //if depot already added
		  			d.addCampervan(c); // just add van
		  			depotinList = true;
		  		}
		  	}
		  	if (depotinList == false) { // else add both
		  		nd.addCampervan(c);  
		  		Depots.add(nd); 
		  	}	
	}
	
	/**
	 * Method which sets the Calendar date given the hour, month and day
	 * @param hour - Integer of hour of day
	 * @param month - 3 letter String of month
	 * @param day - Integer of day of month
	 * @return - Returns the date in Calendar format
	 */
	public Calendar setDate(int hour, String month, int day) {
		Calendar date = new GregorianCalendar();
		date.setTimeInMillis(0);
		switch(month) { //sets month accordingly to calendar format
			case "Jan": date.set(2017, Calendar.JANUARY, day, hour, 0); break;
			case "Feb": date.set(2017, Calendar.FEBRUARY, day, hour, 0); break;
			case "Mar": date.set(2017, Calendar.MARCH, day, hour, 0); break;
			case "Apr": date.set(2017, Calendar.APRIL, day, hour, 0); break;
			case "May": date.set(2017, Calendar.MAY, day, hour, 0); break;
			case "Jun": date.set(2017, Calendar.JUNE, day, hour, 0); break;
			case "Jul": date.set(2017, Calendar.JULY, day, hour, 0); break;
			case "Aug": date.set(2017, Calendar.AUGUST, day, hour, 0); break;
			case "Sep": date.set(2017, Calendar.SEPTEMBER, day, hour, 0); break;
			case "Oct": date.set(2017, Calendar.OCTOBER, day, hour, 0); break;
			case "Nov": date.set(2017, Calendar.NOVEMBER, day, hour, 0); break;
			case "Dec": date.set(2017, Calendar.DECEMBER, day, hour, 0); break;
		}
		return date;
	}
	
	/**
	 * Method which determines if booking request can be fulfilled in order of
	 * Depot then within each depot the van, to see if matches type and booking available
	 * @param Depots - ArrayList of depot
	 * @param startDate - Calendar version of booking start date
	 * @param endDate - Calendar version of booking end date
	 * @param num1 - Integer of van num requested (first)
	 * @param type1 - String of van type requested (first)
	 * @param num2 - Integer of van num requested (second)
	 * @param type2 - String of van type requested (second)
	 * @return - Returns true if request can be fulfilled, false if not
	 */
	public boolean bookingFulfill(ArrayList<Depot> Depots, Calendar startDate, Calendar endDate, int num1, String type1, int num2, String type2) {
		int bookingGranted = num1 + num2;
		this.CampervanNames.clear(); //clear string array
		for (Depot d: Depots) { //curr depot
			for (Campervan c: d.getCampervans()) { //curr van in depot
				boolean bookingAvaliable = true; //assume true
    			for (Booking b: c.getBookings()) {
    				if (endDate.before(b.getStartDate())) { //booking Req 1 hr before other booking
    				} else if (startDate.after(b.getEndDate())) { //booking Req 1 hr after other booking
    				} else {
    					bookingAvaliable = false; // if other 2 not satisfied can't book
    				}
    			}
        		if (c.getCampervanTransmission().equals(type1) && num1 != 0) {  //Campervan type matches 1st request & not all booked
        			if (bookingAvaliable == true) {
        				this.CampervanNames.add(c.getCampervanName()); //add to ArrayList of van name strin
        				bookingGranted--;
        				num1--;
        			}
        		}
        		if (c.getCampervanTransmission().equals(type2) && num2 != 0) {
        			if (bookingAvaliable == true) { 
        				this.CampervanNames.add(c.getCampervanName());
        				bookingGranted--;
        				num2--;
        			} 			
        		}
        		if (bookingGranted == 0) return true; //if total num of vans booked return true
			}
		}
		return false;
	}
	

	
	/**
	 * Method which prints the vans held in the depot in order of bookings
	 * @param depots - ArrayList of depot
	 * @param depotName - String name of depot location
	 */
	public void print(ArrayList<Depot> depots, String depotName) {
		for (Depot d: depots) { //curr depot
			if (!d.getDepotLocation().equals(depotName)) continue; //if not match depot to print
			for (Campervan c: d.getCampervans()) { // curr van in depot name
				Collections.sort(c.getBookings()); //sort the booking times first
				for (Booking b: c.getBookings()) { // curr booking for van
					SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm MMM d"); //convert date to suitable format
					String formatted = dateFormat.format(b.getStartDate().getTime()) + " " + dateFormat.format(b.getEndDate().getTime());
					System.out.println(depotName + " " + c.getCampervanName() + " " + formatted);
				}
			}
		}
	}

}