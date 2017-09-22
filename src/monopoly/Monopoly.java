package monopoly;

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Monopoly {

	public static final int MAX_NUM_PLAYERS = 6;
	public static final int NUM_SQUARES = 40;
	public static final DiceRoll die = new DiceRoll();
	private ArrayList<Player> players = new ArrayList<Player>();
	private UI ui = new UI(players);

	Monopoly() {
		ui.display();
		return;
	}

	private void tour(int dice) {
		ui.displayString("TOUR MODE");
		
		for (int p = 0; p <1; p++) {
			for (int i = 0; i <dice ; i++) {
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

	private void echo() {
		String command;
		ui.display();
		// ui.displayString("ECHO MODE");
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

	/*public void goingFirst(int noOfPlayers)
	{
		for(int i=0;i< noOfPlayers;i++)
		{
			
		Player.user1.
			
		}*/
		
		
		
		
		
		
		
		
//	}
	
	
	public static void main(String args[]) {
		Monopoly game = new Monopoly();
		// DiceRoll die= new DiceRoll();
		int noOfPlayers = Integer.parseInt(JOptionPane.showInputDialog("How many players are going to play this game? "));
		game.nameQuery(noOfPlayers);
		game.createTokens(noOfPlayers);
		//die.rollOfDice();
		
	
	//	int dice =die.getTotal();
		//game.tour(dice);
	//	game.echo();
     		return;
	}
}