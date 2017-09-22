import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Monopoly {

	public static final int MAX_NUM_PLAYERS = 6;
	public static final int NUM_SQUARES = 40;
	
	public static ArrayList<Player> players = new ArrayList<Player>();
	private UI ui = new UI(players);
	DiceRoll die= new DiceRoll();
	static int  dieee;
	
	Monopoly () {
		ui.display();
		return;
	}
	
	private void tour (int n) {
		//ui.displayString("TOUR MODE");
		ui.display();
		for (int p=0; p<n; p++) {
			
			if(ui.getCommand().equals("roll"))
			{ ui.display();
				die.rollOfDice();
			dieee=die.getTotal();
			
			}
			for (int i=0; i<dieee; i++) {
			
				players.get(p).move(1);
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
	
	
	private void echo (int n) {
		String command;
		ui.display();
		
		for(int i=0;i<n;i++){
		ui.displayString(players.get(i).Getname() + "\n");
		}
		//ui.displayString("ECHO MODE");
		do {
			command = ui.getCommand();
			ui.displayString(command);
			
		} while (!command.equals("quit"));
		return;
	}
	public void setPeople(int n)
	{
		for(int i=0;i<n;i++)
		{
	String y=JOptionPane.showInputDialog(null,"Enter your name? player ");
	players.add(i, new Player());
		players.get(i).Setname(y);
		}

	}
	
	public static void main (String args[]) {	
			
		int noOfPlayers = Integer.parseInt(JOptionPane.showInputDialog("How many players are going to play this game? "));
		Monopoly game = new Monopoly();
		game.setPeople(noOfPlayers);
		//game.echo();
		game.tour(noOfPlayers);
		game.echo(noOfPlayers);
		
		
		return;
	}
}