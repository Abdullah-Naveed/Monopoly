package src;
/* This class controls player conditions such as, the players initial balance, the players property etc  */
import java.util.ArrayList;

public class Player {
    public int balance;
	public int position;
	public String name;
	public int diceee;
	private  String PlayersProperty;
	int z= 0;
	 ArrayList<property> area = new ArrayList<property>();	
	 String p = "";

	Player () {
		balance = 1500; // This is the initial balance of each player upon starting the game
		position = 0; // This is also the initial position of each player upon starting the game which is the position GO
		return;
	}
	

	public void SetPlayersProperty(String property) { // This function returns the players properties
	
		
	PlayersProperty=property;
	
	p = p + " "+ PlayersProperty;
		
	}
	public String GetProperty()
	{
		return p ;
		
	}

	public void move (int squares) 
	{
		position = position + squares;
		if (position < 0) 
		{
			area.get(squares);
			position = position + property.NUM_SQUARES;
		} 
		else if 
		(position >= property.NUM_SQUARES) 
		{
			position = position % property.NUM_SQUARES;
			balance += 200; // This line adds 200 ton the users balance once they pass go again
		}
		return;
	}
	
	public int getPosition () { // These functions repectively return the position, the name of the position, the name, the user and also the value of the dice and sets them.
		return position;
	}
	public String getPlace()
	{
		return property.ALL_BOARD_NAMES[position];
	}
	public String Getname()
	{
		return name;
		
	}
	public void Setname(String user)
	{
		this.name =user;
		
		
	}
	public int Getdice()
	{
		return diceee;
		
	}
	
	public void diceNum(int dice)
	{
		this.diceee=dice;
	}
	
	public  void setPBalance() // This function sets the players balance as a new value depending on whether they have purchased a property
	{
	balance=balance-PropertyValues.GetValue(); 
	
	}
	
	public  int GetPlayerBalance() {
		return balance;
	}
	
	public void payRent(){ // This function decucts 25 from a players balance depending on whether they land on owned property
		balance = balance - 25;
	}
	public int assets()
	{
		
	
	z=balance;
		
		return z;
	}
	
}