import java.util.ArrayList;

import javax.swing.JOptionPane;


public class Monopoly {

	private static final int START_MONEY = 1500;
	private static final int GO_MONEY = 200;
	
	private Players players = new Players();
	private Player currPlayer;
	private Dice dice = new Dice();
	private Board board = new Board(dice);
	private UI ui = new UI(players, board);
	Card s = new Card();
	private boolean gameOver = false;
	private boolean onlyOneNotBankrupt = false;
	private boolean turnFinished;
	private boolean inJail = false;
	private boolean rollDone;
	private boolean rentOwed;
	private boolean rentPaid;
	int i=0;
	Monopoly () {
		ui.display();
		return;
	}
		
	public void inputNames () {
		int playerId = 0;
		do {
			ui.inputName(playerId);
			if (!ui.isDone()) {
				boolean duplicate = false;
				for (Player p : players.get()) {
					if (ui.getString().toLowerCase().equals(p.getName().toLowerCase())) {
						duplicate = true;
					}
				}
				if (!duplicate) {
					players.add(new Player(ui.getString(),ui.getTokenName(playerId),playerId));
					playerId++;
				} else {
					ui.displayError(UI.ERR_DUPLICATE);
				}
			}
		} while (!ui.isDone() && players.canAddPlayer());
		return;
	}
	
	public void giveStartMoney () {
		for (Player p : players.get()) {
			p.doTransaction (START_MONEY);
			ui.displayBankTransaction (p);
		}
		return;
	}
	
	public void decideStarter () {
		Players inPlayers = new Players(players), selectedPlayers = new Players();
		boolean tie = false;
		do {
			int highestTotal = 0;
			for (Player p : inPlayers.get()) {
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
				inPlayers = new Players(selectedPlayers);
				selectedPlayers.clear();
			}
		} while (tie);
		currPlayer = selectedPlayers.get(0);
		ui.displayRollWinner(currPlayer);
		ui.display();
		return;
	}
	
//	private void GoToJail (){
//		if(board.getSquare(currPlayer.getPosition()).equals("GO TO JAIL")){
//			System.out.println("YUP GO TO JAIL..");
//		}
//	}
//	
	private void processRoll () {
		if (!rollDone) {
			if (currPlayer.getBalance() >= 0){
				if (currPlayer.getAssets() >= 0 && currPlayer.getBalance() >=0){
					if (!rentOwed) {
						//mock method to test 3 doubles
						//dice.roll();
						dice.mockRoll();
						ui.displayDice(currPlayer, dice);
						currPlayer.move(dice.getTotal());
						ui.display();
						if (currPlayer.passedGo()) {
						currPlayer.doTransaction(+GO_MONEY);
						ui.displayPassedGo(currPlayer);
						ui.displayBankTransaction(currPlayer);
						}
							ui.displaySquare(currPlayer, board, dice);
							if (board.getSquare(currPlayer.getPosition()) instanceof Property && 
									((Property) board.getSquare(currPlayer.getPosition())).isOwned() &&
									!((Property) board.getSquare(currPlayer.getPosition())).getOwner().equals(currPlayer) ) {
										rentOwed = true;
										rentPaid = false;
										// Pay rent
										Property property = (Property) board.getSquare(currPlayer.getPosition());
										// Mortgage
											if(property.isMortgaged()&&!(property.getOwner().equals(currPlayer))){
												rentOwed = false;
												rollDone = false;
												ui.displayString(property + " is mortgaged, therefore no rent is to be paid.");
												ui.displayString("Your balance is: " + currPlayer.getBalance() + " pounds.");
												}
											else{
												processPayRent();
												ui.displayString("You have landed on an owned property, therefore " + ((Property) board.getSquare(currPlayer.getPosition())).getRent() + " pounds is deducted out of your account.");
												ui.displayString("Your balance is now: " + currPlayer.getBalance() + " pounds.");
												}
							
												} else {
													rentOwed = false;
													}
														if (!dice.isDouble()) {
															rollDone = true;
															}
																// 3 doubles in a row
																if(dice.noOfDoubles()==3){
																	ui.displayString("You have rolled 3 doubles in a row. You must go to jail.");
																	currPlayer.goToJail();
																	rollDone = true;
																	turnFinished = true;
																	inJail = true;
																	}
																if (board.getSquareName(currPlayer.getPosition()).equals("Chance")) {

																	Chance.CardCond(j, Chance.ShuffleDeck());

																}

																// community chest
																if (board.getSquareName(currPlayer.getPosition()).equals("Community Chest")) {
																	s.CardCond(i, s.ShuffleDeck());

																}
				
																										// go to jail
																	if (board.getSquareName(currPlayer.getPosition()).equals("Go To Jail")){
																		currPlayer.goToJail();
																		ui.displayString("You landed on Go To Jail. Therefore you must go to jail.");
																		}	
																		// penalties.. balance deducted
																		if(board.getSquareName(currPlayer.getPosition()).equals("Income Tax")){
																			currPlayer.doTransaction(-200);
																			ui.displayString("You have landed on Income Tax, and therefore 200 has been deducted from your balance.");
																			ui.displayString("Your balance is now: " + currPlayer.getBalance() + " pounds.");
																			}
																			else if(board.getSquareName(currPlayer.getPosition()).equals("Super Tax")){
																					currPlayer.doTransaction(-100);
																					ui.displayString("You have landed on Super Tax, and therefore 100 has been deducted from your balance.");
																					ui.displayString("Your balance is now: " + currPlayer.getBalance() + " pounds.");
																					}
								
								} else {
									ui.displayError(UI.ERR_RENT_OWED);	
								}
						} else {
							ui.displayError(UI.ERR_BANKRUPTCY);
							}
				}	else {
					ui.displayError(UI.ERR_MORTGAGE_SELL);
					}		
			} else {
				ui.displayError(UI.ERR_DOUBLE_ROLL);
				}
		return;
	}
	
	private void processPayRent () {
		if (board.getSquare(currPlayer.getPosition()) instanceof Property) {
			Property property = (Property) board.getSquare(currPlayer.getPosition());
			if (property.isOwned()) {
				if (!property.getOwner().equals(currPlayer)) {
					if (!rentPaid) {
						int rent = property.getRent();
						if (currPlayer.getBalance() >= 0) {
							Player owner = property.getOwner();
							currPlayer.doTransaction(-rent);
							owner.doTransaction(+rent);
							ui.displayTransaction(currPlayer, owner);
							rentPaid = true;	
							rentOwed = false;
						} else {
							ui.displayError(UI.ERR_INSUFFICIENT_FUNDS);										
						} 
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
		return;
	}

	private void processBuy () {
		if (board.getSquare(currPlayer.getPosition()) instanceof Property) {
			Property property = (Property) board.getSquare(currPlayer.getPosition());
			if (!property.isOwned()) {
				if (currPlayer.getBalance() >= property.getPrice()) {				
					currPlayer.doTransaction(-property.getPrice());
					ui.displayBankTransaction(currPlayer);
					currPlayer.addProperty(property);
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
		return;
	}
	
	private void processBuild () {
		Property property = ui.getInputProperty();
		if (property.isOwned() && property.getOwner().equals(currPlayer)) {
			if (property instanceof Site) {
				Site site = (Site) property;
				if (currPlayer.isGroupOwner(site)) {
					if (!site.isMortgaged()) {
						int numBuildings = ui.getInputNumber();
						if (numBuildings>0) {
							if (site.canBuild(numBuildings)) {
								int debit = numBuildings*site.getBuildingPrice();
								if (currPlayer.getBalance()>debit) {
									site.build(numBuildings);
									currPlayer.doTransaction(-debit);
									ui.displayBuild(currPlayer,site,numBuildings);
								} else {
									ui.displayError(UI.ERR_INSUFFICIENT_FUNDS);
								}
							} else {
								ui.displayError(UI.ERR_TOO_MANY_BUILDINGS);
							}
						} else {
							ui.displayError(UI.ERR_TOO_FEW_BUILDINGS);
						}
					} else {
						ui.displayError(UI.SITE_IS_MORTGAGED);
					}
				} else {
					ui.displayError(UI.ERR_DOES_NOT_HAVE_GROUP);
				}
			} else {
				ui.displayError(UI.ERR_NOT_A_SITE);
			}
		} else {
			ui.displayError(UI.ERR_NOT_YOURS);
		}
		return;
	}
	
	private void processDemolish () {
		Property property = ui.getInputProperty();
		if (property.isOwned() && property.getOwner().equals(currPlayer)) {
			if (property instanceof Site) {
				Site site = (Site) property;
				int numBuildings = ui.getInputNumber();
				if (numBuildings>0) {
					if (site.canDemolish(numBuildings)) {
						site.demolish(numBuildings);
						int credit = numBuildings * site.getBuildingPrice()/2;
						currPlayer.doTransaction(+credit);
						ui.displayDemolish(currPlayer,site,numBuildings);
					} else {
						ui.displayError(UI.ERR_TOO_MANY_BUILDINGS);
					}
				} else {
					ui.displayError(UI.ERR_TOO_FEW_BUILDINGS);
				}
			} else {
				ui.displayError(UI.ERR_NOT_A_SITE);
			}
		} else {
			ui.displayError(UI.ERR_NOT_YOURS);
		}
		return;		
	}
	
	public void processCheat () {
		switch (ui.getInputNumber()) {
			case 1 :       // acquire colour group
				Property property = board.getProperty("kent");
				currPlayer.addProperty(property);		
				property = board.getProperty("whitechapel");
				currPlayer.addProperty(property);
				break;
			case 2 :	   // make zero balance
				currPlayer.doTransaction(-currPlayer.getBalance());
				break;
		}
		return;
	}
	
	public void processBankrupt () {
		ui.displayBankrupt(currPlayer);
		Player tempPlayer = players.getNextPlayer(currPlayer);
		players.remove(currPlayer);
		currPlayer = tempPlayer;
		if (players.numPlayers()==1) {
			gameOver = true;
			onlyOneNotBankrupt = true;
		}
		ui.display();
		return;
	}
	
	public void processMortgage () {
		Property property = ui.getInputProperty();
		if (property.isOwned() && property.getOwner().equals(currPlayer)) {
			if ((property instanceof Site) && !((Site) property).hasBuildings() || (property instanceof Station) || (property instanceof Utility)) {
				if (!property.isMortgaged()) {
					property.setMortgaged();
					currPlayer.doTransaction(+property.getMortgageValue());
					ui.displayMortgage(currPlayer,property);
				} else {
					ui.displayError(UI.ERR_IS_MORTGAGED);
				}
			} else {
				ui.displayError(UI.ERR_HAS_BUILDINGS);
			}
		} else {
			ui.displayError(UI.ERR_NOT_YOURS);
		}
		return;		
	}
	
	public void processRedeem () {
		Property property = ui.getInputProperty();
		if (property.isOwned() && property.getOwner().equals(currPlayer)) {
			if (property.isMortgaged()) {
				int price = property.getMortgageRemptionPrice();
				if (currPlayer.getBalance() >= price) {
					property.setNotMortgaged();
					currPlayer.doTransaction(-price);
					ui.displayMortgageRedemption(currPlayer,property);
				} else {
					ui.displayError(UI.ERR_INSUFFICIENT_FUNDS);
				}
			} else {
				ui.displayError(UI.ERR_IS_NOT_MORTGAGED);
			}
		} else {
			ui.displayError(UI.ERR_NOT_YOURS);
		}
		return;			
	}

	private void processDone () {
		if (rollDone) {
			if (!rentOwed || (rentOwed && rentPaid)) {
				turnFinished = true;
			} else {
				ui.displayError(UI.ERR_RENT_OWED);
			}
		} else {
			ui.displayError(UI.ERR_NO_ROLL);
		}
		return;
	}
	
	public void processTurn () {
		turnFinished = false;
		rollDone = false;
		rentOwed = false;
		rentPaid = false;
		do {
			ui.inputCommand(currPlayer);
			switch (ui.getCommandId()) {
				case UI.CMD_ROLL :
					processRoll();
				//	s.CardCond(s.ShuffleDeck());
					break;
				case UI.CMD_BUY :
					processBuy();
					break;
				case UI.CMD_BALANCE :
					ui.displayBalance(currPlayer);
					break;
				case UI.CMD_PROPERTY :
					ui.displayProperty(currPlayer);
					break;
				case UI.CMD_BANKRUPT :
					processBankrupt();
					turnFinished = true;
					break;
				case UI.CMD_BUILD :
					processBuild();
					break;
				case UI.CMD_DEMOLISH :
					processDemolish();
					break;
				case UI.CMD_REDEEM :
					processRedeem();
					break;
				case UI.CMD_MORTGAGE :
					processMortgage();
					break;
				case UI.CMD_CHEAT :
					int steps = Integer.parseInt(JOptionPane.showInputDialog("Please enter the amount you would like to move: "));
					currPlayer.move(+steps);
					ui.display();
					break;
				case UI.CMD_HELP :
					ui.displayCommandHelp();
					break;
				case UI.CMD_DONE :
					processDone();
					break;
				case UI.CMD_QUIT : 
					turnFinished = true;
					gameOver = true;
					break;
			}
		} while (!turnFinished);
			if(i<=s.numbers.size())
		{
		i++;
		if(i==s.numbers.size())
		{
			i=0;
		}
		}
	
		
		return;
	}
	
	public void GoToGo()
	{
		if (currPlayer.passedGo()) {
			currPlayer.doTransaction(+GO_MONEY);
			ui.displayPassedGo(currPlayer);
			ui.displayBankTransaction(currPlayer);
		}
	}
	
	
	public void nextPlayer () {
		currPlayer = players.getNextPlayer(currPlayer);
		return;
	}
	
	public void decideWinner () {
		if (onlyOneNotBankrupt) {
			ui.displayWinner(currPlayer);			
		} else {
			ArrayList<Player> playersWithMostAssets = new ArrayList<Player>();
			int mostAssets = players.get(0).getAssets();
			for (Player player : players.get()) {
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
