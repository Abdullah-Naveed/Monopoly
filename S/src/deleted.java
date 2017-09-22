/*

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class deleted {

	public static final int MAX_NUM_PLAYERS = 6;
	public static final int NUM_SQUARES = 40;
	private static  DiceRoll die = new DiceRoll();
	private ArrayList<Player> players = new ArrayList<Player>();
	private UI ui = new UI(players);
	static int diee = 0;
	static int noOfPlayers;
	deleted() {
		for (int p=0; p<2; p++) {
			players.add(new Player());
		}	
		ui.display();

		return;
	}

	public void tour() {
		
		 
			
	
	
		//ui.displayString("TOUR MODE");
		
		for (int p = 0; p < 2; p++) {
			
			for (int i = 0; i <10; i++) {
				
				players.get(p).move(+1);
				ui.display();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					System.out.println("Sleep exeception.");
				}
			}
		}

		return;
	}

	void echo() {
		String command = null;
		ui.display();
	//	 ui.displayString("ECHO MODE");
		
		
		command = ui.getCommand();
		ui.displayString(command);
			Monopoly game = new Monopoly();
		do{
			if(command.equals("roll"))
			{
				
				die.rollOfDice();
			   diee=die.getTotal();
				//game.tour();
			  
			}
		   command = ui.getCommand();
			ui.displayString(command);
			
		}
			while (!command.equals(null));  
		
		return;
	}

	private void echo () {
		String command;
		ui.display();
		ui.displayString("ECHO MODE");
		do {
			command = ui.getCommand();
			ui.displayString(command);
		} while (!command.equals("quit"));
		return;
	}
	public void nameQuery(int n) {
		Player getNames = new Player();

		while (n < 2 || n > 6) {
			JOptionPane.showMessageDialog(null, "Error! Players must be between 2 and 6. Please try again.");
			n = Integer.parseInt(JOptionPane.showInputDialog("How many players are going to play this game? "));
		}

		for (int i = 1; i <= n; i++) {
			String name = JOptionPane.showInputDialog("Please insert player " + i + "'s name: ");
			getNames.names(name);

		}
	}

	public void createTokens(int noOfPlayers) {
		for (int p = 0; p < noOfPlayers; p++) {
			players.add(new Player());
		}
	}

	public void goingFirst(int noOfPlayers)
	{
		for(int i=0;i< noOfPlayers;i++)
		{
			
		Player.user1.
			
		}
		
		
		
		
		
		
		
		
//	}
	
	
	
	public static void main(String args[]) {
		
		deleted game = new deleted();
	// noOfPlayers = Integer.parseInt(JOptionPane.showInputDialog("How many players are going to play this game? "));
		//game.nameQuery(6);
	//	game.createTokens(noOfPlayers);
	
	game.tour();
	game.echo();
		
		
     		return;
	}
}


import javax.swing.JOptionPane;

public class Player extends property {

	property P=new property();
	protected static int position;
	protected static int count = 1;
	protected static String user1 = "", user2 = "", user3 = "", user4 = "", user5 = "", user6 = "";

	Player() {
		position = 0;
	
		return;
	}

	public void names(String name) {

		switch (count) {
		case 1:
			user1 = name;
			break;
		case 2:
			user2 = name;
			if (user2.equals(user1)) {
				JOptionPane.showMessageDialog(null, "Error, name must be unique! Please try again.");
			}
			break;
		case 3:
			user3 = name;
			if (user3.equals(user1) || user3.equals(user2)) {
				JOptionPane.showMessageDialog(null, "Error, name must be unique! Please try again.");
			}
			break;
		case 4:
			user4 = name;
			if (user4.equals(user1) || user4.equals(user2) || user4.equals(user3)) {
				JOptionPane.showMessageDialog(null, "Error, name must be unique! Please try again.");
			}
			break;
		case 5:
			user5 = name;
			if (user5.equals(user1) || user5.equals(user2) || user5.equals(user3) || user5.equals(user4)) {
				JOptionPane.showMessageDialog(null, "Error, name must be unique! Please try again.");
			}
			break;
		case 6:
			user6 = name;
			if (user6.equals(user1) || user6.equals(user2) || user6.equals(user3) || user6.equals(user4)
					|| user6.equals(user5)) {
				JOptionPane.showMessageDialog(null, "Error, name must be unique! Please try again.");
			}
			break;
		}
		count++;
	}

	public void move(int squares) {
		position = position + squares;
		if (position < 0) {
			position = position + Monopoly.NUM_SQUARES;
		} else if (position >= Monopoly.NUM_SQUARES) {
			position = position % Monopoly.NUM_SQUARES;
		}
		return;
	}

	public String getPostion()
	{
		return "  " +P.PROPERTY_NAMES[P.SQUARE_TYPES[position]];
	}

	public int getPosition() {
		return position;
	}

}
*/