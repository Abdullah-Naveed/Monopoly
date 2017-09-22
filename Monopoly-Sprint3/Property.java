
public class Property extends Square {

	public boolean isOwned;
	public boolean houseOwned;
	public boolean hotelOwned;
	private int value;
	private int rent[];
	private String color;
	private Player owner;
	private int rentPrice = 0;
	private int housePerProperty = 0;
	private int hotelsPerProperty = 0;
	
	Property (String name, int value, int[] rent, String color) {
		super(name);
		this.value = value;
		this.rent = rent;
		isOwned = false;
		houseOwned = false;
		hotelOwned = false;
		this.color = color;
		return;
	}
	
	Property(){}

	public String getColor(){
		return color;
	}
	
	public int getValue () {
		return value;
	}
	
	public int getRent () {
		return rent[rentPrice];
	}
	
	public boolean isOwned () {
		return isOwned;
	}

	public boolean houseOwned(){
		return houseOwned;
	}
	
	public boolean hotelOwned(){
		return hotelOwned;
	}
	
	public void setOwner (Player inPlayer) {
		owner = inPlayer;
		isOwned = true;
		return;
	}
	
	public void setHouseOwner (Player inPlayer) {
		owner = inPlayer;
		houseOwned = true;
		return;
	}
	
	public void setHotelOwner (Player inPlayer) {
		owner = inPlayer;
		hotelOwned = true;
		return;
	}
	
	public void setOwnerToFalse() {
		owner= null;
		isOwned = false;
		return;
	}
	
	public Player getOwner () {
		return owner;
	}
	
	public int NoOfHousesPerProperty(int noOfHouses){
		return housePerProperty+=noOfHouses;
	}
	
	public int NoOfHotelsPerProperty(int noOfHotels){
		return hotelsPerProperty+=noOfHotels;
	}
	public int demolish(int noOfHouses){
		return housePerProperty-=noOfHouses;
	}
	
}
