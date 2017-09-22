package src;

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Monopoly {

	public static final int MAX_NUM_PLAYERS = 6;
	public static final int NUM_SQUARES = 40;
	protected static int User = 0;
	public static ArrayList<Player> players = new ArrayList<Player>();
	private static UI ui = new UI(players);
	static int noOfPlayers;
	static int  dieee;
	
	Monopoly () 
	{
		ui.display();
		return;
	}
	
	private static void tour (Player c) // This is the function that controls the movement of the player token around the board
	{
		DiceRoll die= new DiceRoll();
	
		for (int p=0; p<1; p++) //
		{
			die.rollOfDice(p);
			dieee=die.getTotal();
			ui.display();
			for (int i=0; i<dieee; i++) 
			{
				c.move(+1);
			//	players.get(p).move(+1);
				ui.display();
				try {
					Thread.sleep(500); /// This line adjusts the speed of the token as it moves around the board
				} 
				catch (InterruptedException e) 
				{
					System.out.println("Sleep exeception.");
				} 
			}
		}
		
		return;
	}
	
	
	public static void game() // This function is the actual game itself that we end up calling in our main, it includes the welcome message, calls the functions to prompt user for input and also sets the amount of players 
	{ 
		ui.displayString("Enter roll to begin \nList of acceptable commands are: \n - balance \n - position \n - roll \n - property \n - buy \n - done \n - quit");	
		// The above line displayes a the message as soon as the program is run, informing the user of acceptable commands
	noOfPlayers();
	setPeople(noOfPlayers);
	ui.displayString("List of current players include: ");
	for (int i = 0; i<players.size(); i++) // This for loop prints out each player name dependind on the amount of players
		{ 
		ui.displayString(" - Player " + (i+1) + ": " + players.get(i).Getname()); // This line is just the format in which the players names will be printed in the panel
		}
	String command = "";
	while(!command.equals("quit"))
	{
		for (int i=0; i < noOfPlayers; i++)
		{
		echo(i); // We then call for it to be printed within our information panel
		}
		
	}
	
	}
	
	
	private static void echo (int i)  // This function contains the main operation a player can carry out when it is their turn during the course of the game
	{
	//	ui.display();
		String command =null;
		
		do {
			
			command = ui.getCommand();
			ui.displayString(command);
			
			switch (command) 
			{
			case "balance": // In the case where the user types in balance they will get their current balance returned to them
				ui.displayString(" - Your balance is: " + players.get(i).GetPlayerBalance() + "\n"); // Format
				break;
			
			case "position": // If the user types in position then they will get a message informing them of their position number, name and price if it is buyable property
				property.settingProperty(players.get(i).getPlace());
				ui.displayString(" - " + players.get(i).Getname() + "'s position is "  +
				Monopoly.players.get(i).getPosition() + " and location is "+ players.get(i).getPlace()+
				" The price of this site is "+  PropertyValues.GetValue()+ "\n");
	
				break;
			case "help": // In the case of help, the user gets hit with a list of acceptable commands
				ui.displayString("List of acceptable commands are: \n - balance \n - position \n - roll \n - property \n - buy \n - done \n - quit \n");
				break;
				
			case "buy": // If the player chooses to buy in the case where the postion they have fallen on is buyable then the costof that is taken away from their balance and if the position has already been bought then they must pay rent
				if (property.OWNED[players.get(i).getPosition()] == false) 
				{
					property.settingProperty(players.get(i).getPlace());
					players.get(i).SetPlayersProperty(players.get(i).getPlace());
					players.get(i).setPBalance();
					ui.displayString(" \n - You now have " + players.get(i).GetPlayerBalance() + " remaining \n ");
					property.OWNED[players.get(i).getPosition()] = true;

				} 
				else if (property.OWNED[Monopoly.players.get(i).getPosition()] == true) 
				{
					// minus balance of rent from player here...
					if (property.OWNED[Monopoly.players.get(i).getPosition()] == true) 
						{
						   JOptionPane.showMessageDialog(null, "This property is already bought!");
                    	   JOptionPane.showMessageDialog(null, "You must now pay rent.");
                    	   Monopoly.players.get(i).payRent();
						}
					}
				break;
				
			case "property":
				ui.displayString(players.get(i).GetProperty()+"    "+	" \n ");
	        	break;
	        	
			case "roll":
				
				tour(players.get(i));
				property.settingProperty(players.get(i).getPlace());
				ui.displayString(" - " + players.get(i).Getname() + "'s position is "  +
						Monopoly.players.get(i).getPosition() + " and location is "+ players.get(i).getPlace()+
						" The price of this site is "+  PropertyValues.GetValue()+ "\n");

	        	if(players.get(i).GetPlayerBalance() <= 0)
	    		{
	    			players.remove(i);
	    		}
	        	break;
	        	
			 
			}	
			
			if (!command.equals("balance") && !command.equals("position") && !command.equals("help") && !command.equals("roll") && !command.equals("property") && !command.equals("buy") && !command.equals("done")&& !command.equals("quit"))
			{
			ui.displayString(" - Error - The command you've entered is invalid \n - Type in the word ''help'' for a list of acceptable commands"); 
			}	// In the case where the user does not input an acceptable command then the above message is printed prompting them to input the word help to guide them towards a list of acceptable commands
			
		} while (!command.equals("quit")&&!command.equals("done"));
		if(command.equals("quit"))
		{ 
		for (int j = 0; j<players.size(); j++) // This for loop prints out each player name dependind on the amount of players
		{ 
		ui.displayString(" - Player " + (j+1) + ": " + players.get(j).Getname()); // This line is just the format in which the players names will be printed in the panel
		ui.displayString( "the players properties are \n"+" "+players.get(j).GetProperty()+ " the balance is"+" "+players.get(j).assets()+"\n");
		
	
		
		}}
		
		return;
	}
	
	public static void noOfPlayers()
	{
		do 
		{
			noOfPlayers = Integer.parseInt(JOptionPane.showInputDialog("How many players are going to play this game? ")); // Here we determine the amount of players to play the game 
		} 
		while (noOfPlayers < 2 || noOfPlayers > 6); // This ensures that the no of players is between 2 and 6 
	}

	
	
	
	public static void setPeople(int n) 
	{

		for (int i = 0; i < n; i++) 
		{
			
			String y = JOptionPane.showInputDialog(null, "Enter your name please: "); // This loop ensure that the user does not enter a blank as their name, and will continue to run until the name is not blank
			while(y.equals(""))
			{
				y = JOptionPane.showInputDialog(null, "Name can not be blank. Please enter a valid name: "); 
			}
			players.add(i, new Player());
			players.get(i).Setname(y);
			
		}
		int[] numbers= new int[n];
		DiceRoll d= new DiceRoll();
		for(int j=0;j<n;j++)
		{
		
		d.rollingfirst(j);
		players.get(j).diceNum(d.getTotal());
	
	
     numbers[j]=players.get(j).Getdice();
     
		}	
//		int max=numbers[0];
//		int l;
//	for(int k=0;k<n;k++)
//	{
//		if(max<numbers[k])
//		{
//			numbers[k]=max; 
//			l=k;
//		}
//		
//	}
//	
//	
//	}
//	public static void goingfirst(int l)
//	{
//		switch (l) 
//		{
//		case 1:
//			
//			break;
//				
//       case 2:
//			
//			break;
//			
//       case 3:
//	
//	break;
//	
//       case 4:
//	
//	break;
//	
//       case 5:
//	
//	break;
//	
//       case 6:
//	
//	break;
//			
//		}
		
	}
	
	public static void main (String args[]) // This is our main and in this we simply call the function game
	{	
		game();
		
		
		return;
	}
}
