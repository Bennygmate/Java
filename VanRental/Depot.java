import java.util.ArrayList;

public class Depot {

	private String depotLocation;
	private ArrayList<Campervan> Campervans = new ArrayList<Campervan>();
	
	/**
	 * Constructor sets Depot location
	 * @param location - Location of depot
	 */
	public Depot (String location) {
		this.depotLocation = location;
	}
	
	/**
	 * @return - Return depot location
	 */
	public String getDepotLocation() {
		return this.depotLocation;
	}
	
	/**
	 * Method adds a Campervan to the depot
	 * @param c - Campervan object
	 */
	public void addCampervan(Campervan c) {
		this.Campervans.add(c);
	}
	
	/**
	 * @return - Return ArrayList of Campervans with the Depot
	 */
	public ArrayList<Campervan> getCampervans(){
		return this.Campervans;
	}
}