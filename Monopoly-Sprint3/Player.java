import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Player {
	
	private String name;
	private int position;
	private int balance;
	private int amount;
	private String token;
	private boolean passedGo;
	protected ArrayList<Property> properties = new ArrayList<Property>();
	protected ArrayList<Property> houses = new ArrayList<Property>();
	protected ArrayList<Property> hotels = new ArrayList<Property>();
	
	Player (String name, String token) {
		this.name = name;
		this.token = token;
		position = 0;
		balance = 0;
		passedGo = false;
		return;
	}
	public void bankTransaction()
	{
		
	}

	public void move (int squares) {
		position = position + squares;
		if (position >= Board.NUM_SQUARES) {
			position = position - Board.NUM_SQUARES;
			passedGo = true;
		} else {
			passedGo = false;
		}
		if (position < 0) {
			position = position + Board.NUM_SQUARES;
		} 
		return;
	}
	
	public void doTransaction (int amount) {
		balance = balance + amount;
		this.amount = amount;
		return;
	}
	
	public int getPosition () {
		return position;
	}
	
	public String getName () {
		return name;
	}
	
	public int getTransaction () {
		return amount;
	}
	
	public int getBalance () {
		return balance;
	}
	
	public boolean passedGo () {
		return passedGo;
	}
	
	public void boughtProperty (Property property) {
		property.setOwner(this);
		properties.add(property);
		return;
	}
	
	public void boughtHouse (Property house, int n) {
		int i=0;
		house.setHouseOwner(this);
		while(i<n){
			houses.add(house);
			i++;
		}
		return;
	}
	
	public void boughtHotel (Property hotel, int n) {
		int i=0;
		hotel.setHotelOwner(this);
		while(i<n){
			hotels.add(hotel);
			i++;
		}
		return;
	}
	
	public void removeHouses (Property house, int n){
		int i=0;
		while(i<n){
			houses.remove(house);
			i++;
		}
		return;
	}
	
	public void removeProperties(){
		properties.removeAll(properties);
	}
	

	
	public Property getLatestProperty () {
		return properties.get(properties.size()-1);
	}
	
	public ArrayList<Property> getProperties () {
		return properties;
	}
	
	public ArrayList<Property> getHouses () {
		return houses;
	}
	
	public int getNoOfHouses(){
		return houses.size();
	}
	
	public ArrayList<Property> getHotels () {
		return hotels;
	}
	
	public Property getLatestHouse () {
		return houses.get(houses.size()-1);
	}
	
	public int getAssets () {
		int assets = balance;
		for (Property property: properties) {
			assets = assets + property.getValue();
		}
		return assets;
	}
	
	public String toString () {
		return name + " (" + token + ")";
	}
}