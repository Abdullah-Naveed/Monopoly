import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Monopoly extends UI {

	public static final int MAX_NUM_PLAYERS = 6;
	private static final int START_MONEY = 1500;
	private static final int GO_MONEY = 200;
	
	private ArrayList<Player> players = new ArrayList<Player>();
	private Player currPlayer;
	private UI ui = new UI(players);
	private int numPlayers;
	private Dice dice = new Dice();
	private boolean gameOver = false;
	private Board board = new Board();

	Monopoly () {
		numPlayers = 0;
		ui.display();
		return;
	}
	
	public void inputNames () {
		do {
			ui.inputName(numPlayers);
			if (!ui.isDone()) {
				players.add(new Player(ui.getString(),ui.getTokenName(numPlayers)));
				numPlayers++;
			}
		} while (!ui.isDone() && numPlayers!=MAX_NUM_PLAYERS);
		return;
	}
	
	public void giveStartMoney () {
		for (Player p : players) {
			p.doTransaction (START_MONEY);
			ui.displayBankTransaction (p);
		}
		return;
	}
	
	public void decideStarter () {
		ArrayList<Player> inPlayers = new ArrayList<Player>(players), 
				selectedPlayers = new ArrayList<Player>();
		
		boolean tie = false;
		do {
			int highestTotal = 0;
			for (Player p : inPlayers) {
				dice.roll();
				ui.displayDice(p,dice);
				if (dice.getTotal() > highestTotal) {
					tie = false;
					highestTotal = dice.getTotal();
					selectedPlayers.clear();
					selectedPlayers.add(p);
				} else if (dice.getTotal() == highestTotal) {
					tie = true;
					selectedPlayers.add(p);
				}
			}
			if (tie) {
				ui.displayRollDraw();
				inPlayers = new ArrayList<Player>(selectedPlayers);
				selectedPlayers.clear();
			}
		} while (tie);
		currPlayer = selectedPlayers.get(0);
		ui.displayRollWinner(currPlayer);
		ui.display();
		return;
	}


	public void processTurn () {
		Property p = new Property();
		boolean turnFinished = false;
		boolean rollDone = false;
		boolean rentOwed = false;
		boolean rentPaid = false;
//		boolean redo = false;
		int noOfHouses = 0;
		do {
			ui.inputCommand(currPlayer);
			switch (ui.getCommandId()) {
				case UI.CMD_BUILD :
					ui.displayString(currPlayer + " which property would you like to build on? ");
					String str = ui.InputOutput();
					for(int i=0;i<currPlayer.getProperties().size(); i++){
					
						if(checking(currPlayer.getProperties().get(i), str))
						{
							if(colorMatching(currPlayer.getProperties().get(i),currPlayer.getProperties()) )
							{
//								while(!redo){
									ui.displayString(currPlayer + " how many houses would you like to build on " + currPlayer.getProperties().get(i).getName() + "? ");
									noOfHouses = Integer.parseInt(ui.InputOutput());
									p.NoOfHousesPerProperty(noOfHouses);
									currPlayer.boughtHouse(currPlayer.getProperties().get(i), noOfHouses);
									ui.displayNoOfHouses(currPlayer, noOfHouses);
									ui.househotelCost(currPlayer, currPlayer.getProperties().get(i).getColor(), noOfHouses);
									
									if(currPlayer.getNoOfHouses()>=4){
										ui.displayString("Would you like to build a hotel on " + currPlayer.getProperties().get(i).getName() + "? ('yes' or 'no')");
										String yesHotel = ui.InputOutput();
										if(yesHotel.equals("yes") && currPlayer.getNoOfHouses()>=4){
											currPlayer.removeHouses(currPlayer.getProperties().get(i), 4); //removes 4 houses
											ui.displayString("How many hotels would you like to build on " + currPlayer.getProperties().get(i).getName() + "?");
											int noOfHotels = Integer.parseInt(ui.InputOutput());
											p.NoOfHotelsPerProperty(noOfHotels); //adds hotels
											currPlayer.boughtHotel(currPlayer.getProperties().get(i), noOfHotels);
											p.setHotelOwner(currPlayer);
											ui.displayString(currPlayer + " has built " + noOfHotels + " hotel/hotels on: " + currPlayer.getProperties().get(i));
											ui.househotelCost(currPlayer, currPlayer.getProperties().get(i).getColor(), noOfHotels);
										} 
										else{
											ui.displayString("You do not have enough houses on the property to build a house yet.");
										}
									}
//								}
							}
							else{
								ui.displayError(UI.ERR_BUILD);
							}
						}
//							else{
//								ui.displayString("The property you entered is not owned by you/ or is not a property. "
//									+ "Please try again, or enter 'continue' to continue.");
//								String query = ui.InputOutput();
//								if(!query.equals("continue")){
//									redo = true;
//								}
//							}
					}
					
					break;
				case UI.CMD_CHEAT :
					int steps = Integer.parseInt(JOptionPane.showInputDialog("Please enter the amount you would like to move: "));
					currPlayer.move(+steps);
					ui.display();
					break;
				case UI.CMD_ROLL :
					if (!rollDone) {
						if (!rentOwed) {
							dice.roll();
							ui.displayDice(currPlayer, dice);
							currPlayer.move(dice.getTotal());
							ui.display();
							if (currPlayer.passedGo()) {
								currPlayer.doTransaction(+GO_MONEY);
								ui.displayPassedGo(currPlayer);
								ui.displayBankTransaction(currPlayer);
							}
							ui.displaySquare(currPlayer, board);
							if (board.isProperty(currPlayer.getPosition()) && 
									board.getProperty(currPlayer.getPosition()).isOwned() &&
									!board.getProperty(currPlayer.getPosition()).getOwner().equals(currPlayer) ) {
										rentOwed = true;
							} else {
								rentOwed = false;
							}
							if (!dice.isDouble()) {
								rollDone = true;
							}
						} else {
							ui.displayError(UI.ERR_RENT_OWED);	
						}
					} else {
						ui.displayError(UI.ERR_DOUBLE_ROLL);
					}
					break;
				case UI.CMD_PAY_RENT :
					if (board.isProperty(currPlayer.getPosition())) {
						Property property = board.getProperty(currPlayer.getPosition());
						if (property.isOwned()) {
							if (!property.getOwner().equals(currPlayer)) {
								if (!rentPaid) {
										Player owner = property.getOwner();
										currPlayer.doTransaction(-property.getRent());
										owner.doTransaction(+property.getRent());
										ui.displayTransaction(currPlayer, owner);
										rentPaid = true;	
										rentOwed = false;
								} else {
									ui.displayError(UI.ERR_RENT_ALREADY_PAID);									
								}
							} else {
								ui.displayError(UI.ERR_SELF_OWNED);								
							}
						} else {
							ui.displayError(UI.ERR_NOT_OWNED);							
						}
					} else {
						ui.displayError(UI.ERR_NOT_A_PROPERTY);
					}
					break;
				case UI.CMD_BUY :
					if (board.isProperty(currPlayer.getPosition())) {
						Property property = board.getProperty(currPlayer.getPosition());
						if (!property.isOwned()) {
							if (currPlayer.getBalance() >= property.getValue()) {				
								currPlayer.doTransaction(-property.getValue());
								ui.displayBankTransaction(currPlayer);
								currPlayer.boughtProperty(property);
								ui.displayLatestProperty(currPlayer);
							} else {
								ui.displayError(UI.ERR_INSUFFICIENT_FUNDS);
							}
						} else {
							ui.displayError(UI.ERR_IS_OWNED);
						}
					} else {
						ui.displayError(UI.ERR_NOT_A_PROPERTY);
					}
					break;
				case UI.CMD_BALANCE :
					ui.displayBalance(currPlayer);
					break;
				case UI.CMD_PROPERTY :
					ui.displayProperty(currPlayer);
					ui.displayHouse(currPlayer);
					ui.displayHotel(currPlayer);
					break;
				case UI.CMD_HELP :
					ui.displayCommandHelp();
					break;
				case UI.CMD_DONE :
					if (rollDone) {
						if (!rentOwed || (rentOwed && rentPaid)) {
							turnFinished = true;
						} else {
							ui.displayError(UI.ERR_RENT_OWED);
						}
					} else {
						ui.displayError(UI.ERR_NO_ROLL);
					}
					break;
				case UI.CMD_QUIT : 
					turnFinished = true;
					gameOver = true;
					break;
				case UI.CMD_BANKRUPT :										
					//so it removes the player, but the token color changes to a different player. hmmmmm
					//should only be if player has less than 0 balance.. create if statement when u get properties fixed.
					p.setOwnerToFalse();
					currPlayer.removeProperties();
				
					ui.displayString(currPlayer.getName() + " has been removed from this game.");
					
					turnFinished = true;
					break;
					case UI.CMD_DEMOLISH :
						ui.displayString(currPlayer + " which property would you like to demolish? ");
						String str1 = ui.InputOutput();
						
						for(int i=0;i<currPlayer.getProperties().size(); i++){
						
							if(checking(currPlayer.getProperties().get(i), str1))
							{
								if(colorMatching(currPlayer.getProperties().get(i),currPlayer.getProperties()) )
								{
//								displayDemolishM(currPlayer);
//								currPlayer.doTransaction(+((property1.getValue())/2));
									ui.displayString(currPlayer + " how many houses would you like to demolish? ");
									String str2 = ui.InputOutput();
									int result = Integer.parseInt(str2);
									p.demolish(result);
									ui.DemolishCost(currPlayer, currPlayer.getProperties().get(i).getColor(), result);
//									currPlayer.removeHouses(property1,result);
									
							}
						}
						
					else if (!colorMatching(currPlayer.getProperties().get(i),currPlayer.getProperties()))
 
					{
						ui.displayError(UI.ERR_DEMOLISH);
					}
						}
					break;
				
			}
		} while (!turnFinished);
		return;
	}
	
	public void nextPlayer () {
		currPlayer = players.get((players.indexOf(currPlayer) + 1) % players.size());
		return;
	}
	
	public void decideWinner () {
		ArrayList<Player> playersWithMostAssets = new ArrayList<Player>();
		int mostAssets = players.get(0).getAssets();
		for (Player player : players) {
			ui.displayAssets(player);
			if (player.getAssets() > mostAssets) {
				playersWithMostAssets.clear(); 
				playersWithMostAssets.add(player);
			} else if (player.getAssets() == mostAssets) {
				playersWithMostAssets.add(player);
			}
		}
		if (playersWithMostAssets.size() == 1) {
			ui.displayWinner(playersWithMostAssets.get(0));
		} else {
			ui.displayDraw(playersWithMostAssets);
		}
		return;
	}
	
	public void displayGameOver () {
		ui.displayGameOver ();
		return;
	}
	
	public boolean isGameOver () {
		return gameOver;
	}

}
