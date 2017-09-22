import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Monopoly {

	private static final int START_MONEY = 1500;
	private static final int GO_MONEY = 200;
	private static Players players = new Players();
	private static Player currPlayer;
	private static Dice dice = new Dice();
	private static Board board = new Board(dice);
	private static UI ui = new UI(players, board);
	static Community_Chest_Card s = new Community_Chest_Card();
	static Chance_Card Chance = new Chance_Card();
	private boolean gameOver = false;
	private boolean onlyOneNotBankrupt = false;
	private boolean turnFinished;
	private boolean rollDone;
	private boolean rentOwed;
	private boolean rentPaid;
	private boolean invalidInput = true;

	int i = 0;
static int j=0;
	Monopoly() {
		ui.display();
		return;
	}

	public void inputNames() {
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
					players.add(new Player(ui.getString(), ui.getTokenName(playerId), playerId));
					playerId++;
				} else {
					ui.displayError(UI.ERR_DUPLICATE);
				}
			}
		} while (!ui.isDone() && players.canAddPlayer());
		return;
	}

	public void giveStartMoney() {
		for (Player p : players.get()) {
			p.doTransaction(START_MONEY);
			ui.displayBankTransaction(p);
		}
		return;
	}

	public void decideStarter() {
		Players inPlayers = new Players(players), selectedPlayers = new Players();
		boolean tie = false;
		do {
			int highestTotal = 0;
			for (Player p : inPlayers.get()) {
				dice.roll();
				ui.displayDice(p, dice);
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

	private void processRoll() {
		if (currPlayer.freeFromJail == true) {
			
			if(currPlayer.getBalance()<0){
				rollDone=true;
				turnFinished=false;
				if(currPlayer.getAssets()>0){
					ui.displayString("You must mortgage or demolish your property. 'm' for mortgage, 'd' for demolish");
					ui.inputString();
					String input = ui.getStringC();
					if(input.equals("m")){
						ui.displayString("Which property would you like to mortgage?");
						ui.displayProperty(currPlayer);
						switch (ui.getCommandId()) {
						case UI.CMD_MORTGAGE :
							processMortgage();
							ui.displayString("You have mortgaged the property, and therefore are allowed to continue. Please type a command.");
							break;
						}
					}
					else if(input.equals("d")){
						ui.displayString("Which property would you like to demolish?");
						ui.displayProperty(currPlayer);
						switch (ui.getCommandId()) {
						case UI.CMD_DEMOLISH :
							processDemolish();
							ui.displayString("You have demolished the property, and therefore are allowed to continue. Please type a command.");
							break;
						}
					}
				}
				else if(currPlayer.getAssets()==0){
					ui.displayString("You must declare bankruptcy as you have no assets and your balance is below 0.");
					processBankrupt();
					turnFinished=true;
					rollDone=true;
				}
			}
			
			if (!rollDone) {
				if (!rentOwed) {
					// mock method to test 3 doubles
					// dice.roll();
					String n = JOptionPane.showInputDialog(null, "enter m(mock), d(diffRoll) or r(roll)");
					if (n.equals("m")) {
						dice.mockRoll();
					} else if (n.equals("r")) {
						dice.roll();
					}
					else if(n.equals("d")){
						dice.diffRoll();
					}

					ui.displayDice(currPlayer, dice);
					currPlayer.move(dice.getTotal());
					ui.display();
					if (currPlayer.passedGo()) {
						currPlayer.doTransaction(+GO_MONEY);
						ui.displayPassedGo(currPlayer);
						ui.displayBankTransaction(currPlayer);
					}
					ui.displaySquare(currPlayer, board, dice);
					if (board.getSquare(currPlayer.getPosition()) instanceof Property
							&& ((Property) board.getSquare(currPlayer.getPosition())).isOwned()
							&& !((Property) board.getSquare(currPlayer.getPosition())).getOwner().equals(currPlayer)) {
						rentOwed = true;
						rentPaid = false;
						// pay rent
						Property property = (Property) board.getSquare(currPlayer.getPosition());
						// mortgage
						if (property.isMortgaged() && !(property.getOwner().equals(currPlayer))) {
							rentOwed = false;
							rollDone = false;
							ui.displayString(property + " is mortgaged, therefore no rent is to be paid.");
							ui.displayBalance(currPlayer);
						} else {
							processPayRent();
							ui.displayString("You have landed on an owned property, therefore "
									+ ((Property) board.getSquare(currPlayer.getPosition())).getRent()
									+ " pounds is deducted out of your account.");
							ui.displayBalance(currPlayer);
						}

					} else {
						rentOwed = false;
					}
					if (!dice.isDouble()) {
						rollDone = true;
					}
					if (board.getSquareName(currPlayer.getPosition()).equals("Chance")) {

						Chance.CardCond(j, Chance.ShuffleDeck());

					}

					//moes implementation
					
					
					// community chest
					if (board.getSquareName(currPlayer.getPosition()).equals("Community Chest")) {
						s.CardCond(i, s.ShuffleDeck());

					}

					// 3 doubles in a row
					if (dice.noOfDoubles() >= 3) {
						ui.displayString("You have rolled 3 doubles in a row. You must go to jail.");
						currPlayer.goToJail();
						currPlayer.freeFromJail = false;
						rollDone = true;
						turnFinished = true;
						currPlayer.inJail = true;
						dice.resetCount();
					}

					// go to jail
					if (board.getSquareName(currPlayer.getPosition()).equals("Go To Jail")) {
						currPlayer.goToJail();
						currPlayer.freeFromJail = false;
						rollDone = true;
						turnFinished = true;
						currPlayer.inJail = true;
						dice.resetCount();
						ui.displayString("You landed on Go To Jail. Therefore you must go to jail.");
					}
					// penalties.. balance deducted
					if (board.getSquareName(currPlayer.getPosition()).equals("Income Tax")) {
						currPlayer.doTransaction(-200);
						ui.displayString(
								"You have landed on Income Tax, and therefore 200 has been deducted from your balance.");
						ui.displayString("Your balance is now: " + currPlayer.getBalance() + " pounds.");
					} else if (board.getSquareName(currPlayer.getPosition()).equals("Super Tax")) {
						currPlayer.doTransaction(-100);
						ui.displayString(
								"You have landed on Super Tax, and therefore 100 has been deducted from your balance.");
						ui.displayString("Your balance is now: " + currPlayer.getBalance() + " pounds.");
					}

				} else {
					ui.displayError(UI.ERR_RENT_OWED);
				}
			} else {
				ui.displayError(UI.ERR_DOUBLE_ROLL);
			}
		} else {
			if (!rentOwed) {
				dice.roll();
				if (dice.getDice1() == dice.getDice2()) {
					currPlayer.freeFromJail = true;
					currPlayer.inJail = false;
				} else {
					rollDone = true;
					currPlayer.freeFromJail = false;
					currPlayer.inJail = true;
					return;
				}
			}
		}
		return;
	}

	private void processBuy() {
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

	private void processPayRent() {
		if (board.getSquare(currPlayer.getPosition()) instanceof Property) {
			Property property = (Property) board.getSquare(currPlayer.getPosition());
			if (property.isOwned()) {
				if (!property.getOwner().equals(currPlayer)) {
					if (!property.isMortgaged()) {
						if (!rentPaid) {
							int rent = property.getRent();
							if (currPlayer.getBalance() >= rent) {
								Player owner = property.getOwner();
								currPlayer.doTransaction(-rent);
								owner.doTransaction(+rent);
								ui.displayTransaction(currPlayer, owner);
								rentPaid = true;
								rentOwed = false;
								ui.displayString("ENTERED IF STATEMENT!!!!");
							} else {
								ui.displayError(UI.ERR_INSUFFICIENT_FUNDS);
							}
						} else {
							ui.displayError(UI.ERR_RENT_ALREADY_PAID);
						}
					} else {
						ui.displayError(UI.ERR_IS_NOT_MORTGAGED);
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

	private void processBuild() {
		Property property = ui.getInputProperty();
		if (property.isOwned() && property.getOwner().equals(currPlayer)) {
			if (property instanceof Site) {
				Site site = (Site) property;
				if (currPlayer.isGroupOwner(site)) {
					// is it mortgaged?
					if (!site.isMortgaged()) {
						int numBuildings = ui.getInputNumber();
						if (numBuildings > 0) {
							if (site.canBuild(numBuildings)) {
								int debit = numBuildings * site.getBuildingPrice();
								if (currPlayer.getBalance() > debit) {
									site.build(numBuildings);
									currPlayer.doTransaction(-debit);
									ui.displayBuild(currPlayer, site, numBuildings);
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

	private void processDemolish() {
		Property property = ui.getInputProperty();
		if (property.isOwned() && property.getOwner().equals(currPlayer)) {
			if (property instanceof Site) {
				Site site = (Site) property;
				int numBuildings = ui.getInputNumber();
				if (numBuildings > 0) {
					if (site.canDemolish(numBuildings)) {
						site.demolish(numBuildings);
						int credit = numBuildings * site.getBuildingPrice() / 2;
						currPlayer.doTransaction(+credit);
						ui.displayDemolish(currPlayer, site, numBuildings);
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

	public void processCheat() {
		switch (ui.getInputNumber()) {
		case 1: // acquire colour group
			Property property = board.getProperty("kent");
			currPlayer.addProperty(property);
			property = board.getProperty("whitechapel");
			currPlayer.addProperty(property);
			break;
		case 2: // make zero balance
			currPlayer.doTransaction(-currPlayer.getBalance());
			break;
		}
		return;
	}

	public void processBankrupt() {
		ui.displayBankrupt(currPlayer);
		Player tempPlayer = players.getNextPlayer(currPlayer);
		players.remove(currPlayer);
		currPlayer = tempPlayer;
		if (players.numPlayers() == 1) {
			gameOver = true;
			onlyOneNotBankrupt = true;
		}
		ui.display();
		return;
	}

	public void processMortgage() {
		Property property = ui.getInputProperty();
		if (property.isOwned() && property.getOwner().equals(currPlayer)) {
			if ((property instanceof Site) && !((Site) property).hasBuildings() || (property instanceof Station)
					|| (property instanceof Utility)) {
				if (!property.isMortgaged()) {
					property.setMortgaged();
					currPlayer.doTransaction(+property.getMortgageValue());
					ui.displayMortgage(currPlayer, property);
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

	public void processRedeem() {
		Property property = ui.getInputProperty();
		if (property.isOwned() && property.getOwner().equals(currPlayer)) {
			if (property.isMortgaged()) {
				int price = property.getMortgageRemptionPrice();
				if (currPlayer.getBalance() >= price) {
					property.setNotMortgaged();
					currPlayer.doTransaction(-price);
					ui.displayMortgageRedemption(currPlayer, property);
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

	private void processDone() {
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

	public void processTurn() {
		turnFinished = false;
		rollDone = false;
		rentOwed = false;
		rentPaid = false;

		do {
			if(currPlayer.inJail==true){
				if(currPlayer.turnsInJail >0 && currPlayer.turnsInJail <3){
					ui.displayString(currPlayer
							+ " Would you like to pay or use a card to get out of jail? Please enter 'pay' or 'card'."
							+ " If neither, please enter 'roll'.");
					ui.inputString();
					String input = ui.getStringC();
					while(invalidInput==true){
						if(input.equals("pay")){
							currPlayer.doTransaction(-50);
							ui.displayString(
									"50 pounds has been deducted from your balance. You are now free from jail.");
							ui.displayBalance(currPlayer);
							currPlayer.inJail=false;
							invalidInput=false;
							currPlayer.turnsInJail=0;
						}
						else if(input.equals("card")){
							if(currPlayer.getOutOfJailFreeCard()){ //implement method
								ui.displayString("You are now out of jail. Please roll to continue.");
								currPlayer.inJail=false;
								currPlayer.freeFromJail=true;
								invalidInput=false;
								rollDone=false;
								currPlayer.turnsInJail=0;
							}
							else{
								ui.displayString("You do not have a get out of jail free card.");
								currPlayer.inJail=true;
								currPlayer.freeFromJail=false;
								invalidInput=false;
								turnFinished=false;
								//switch statements
								while(turnFinished==false){
									ui.inputCommand(currPlayer);
									switch (ui.getCommandId()) {
										case UI.CMD_ROLL :
											//processRoll();
											ui.displayString("Error: Too many rolls.");
											break;
										case UI.CMD_BALANCE :
											ui.displayBalance(currPlayer);
											break;
										case UI.CMD_PROPERTY :
											ui.displayProperty(currPlayer);
											break;
										case UI.CMD_HELP :
											ui.displayCommandHelp();
											break;
										case UI.CMD_DONE :
											//processDone();
											turnFinished=true;
											rollDone=true;
											break;
										case UI.CMD_QUIT : 
											turnFinished = true;
											gameOver = true;
											rollDone=true;
											break;
									}
								}
							}
						}
						else if(input.equals("roll")){
							String n = JOptionPane.showInputDialog(null, "enter m(mock) or r(roll)");
							if (n.equals("m")) {
								dice.mockRoll();
							} else if (n.equals("r")) {
								dice.roll();
							}
							ui.displayDice(currPlayer, dice);
							if(dice.isDouble()){
								ui.displayString(
										"You have rolled a double and therefore are out of jail. You can roll again to proceed.");
								currPlayer.move(dice.getTotal());
								currPlayer.inJail=false;
								currPlayer.freeFromJail=true;
								invalidInput=false;
								rollDone=true;
								ui.display();
								turnFinished=false;
								currPlayer.turnsInJail=0;
								
								while(turnFinished==false){
									ui.inputCommand(currPlayer);
									switch (ui.getCommandId()) {
										case UI.CMD_ROLL :
											processRoll();
											ui.display();
											//ui.displayString("Error: Too many rolls. Please enter another command.");
											break;
										case UI.CMD_BALANCE :
											ui.displayBalance(currPlayer);
											break;
										case UI.CMD_PROPERTY :
											ui.displayProperty(currPlayer);
											break;
										case UI.CMD_HELP :
											ui.displayCommandHelp();
											break;
										case UI.CMD_DONE :
											processDone();
											rollDone=true;
											turnFinished = true;
											break;
										case UI.CMD_QUIT : 
											turnFinished = true;
											gameOver = true;
											rollDone=true;
											break;
									}
								}
							}
							else if(!dice.isDouble()){
								ui.displayString(
										"You have not rolled a double, and therefore must remain in jail.");
								currPlayer.inJail=true;
								invalidInput=false;
								turnFinished=false;
								//switch statements
								while(turnFinished==false){
									ui.inputCommand(currPlayer);
									switch (ui.getCommandId()) {
										case UI.CMD_ROLL :
											//processRoll();
											ui.displayString("Error: Too many rolls.");
											break;
										case UI.CMD_BALANCE :
											ui.displayBalance(currPlayer);
											break;
										case UI.CMD_PROPERTY :
											ui.displayProperty(currPlayer);
											break;
										case UI.CMD_HELP :
											ui.displayCommandHelp();
											break;
										case UI.CMD_DONE :
											//processDone();
											turnFinished=true;
											rollDone=true;
											break;
										case UI.CMD_QUIT : 
											turnFinished = true;
											gameOver = true;
											rollDone=true;
											break;
									}
								}
							}	
						}
					}
					
				}//end if
				else if(currPlayer.turnsInJail>=2 && dice.noOfDoubles()!=1){
					currPlayer.doTransaction(-50);
					ui.displayString("You have been in jail for 3 turns, and havent rolled a double. "
							+ "Therefore, 50 pounds has been deducted from your balance. Please type a command to continue.");
					ui.displayBalance(currPlayer);
					currPlayer.inJail=false;
					rollDone=false;
					currPlayer.turnsInJail=0;
					turnFinished=false;
					
					//switch statements
					while(turnFinished==false){
					ui.inputCommand(currPlayer);
					switch (ui.getCommandId()) {
						case UI.CMD_ROLL :
							processRoll();
							ui.display();
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
//							int steps = Integer.parseInt(JOptionPane.showInputDialog("Please enter the amount you would like to move: "));
//							currPlayer.move(+steps);
//							ui.display();
							currPlayer.doTransaction(-(currPlayer.getBalance()+1));
							break;
						case UI.CMD_HELP :
							ui.displayCommandHelp();
							break;
						case UI.CMD_DONE :
							processDone();
							turnFinished=true;
							rollDone=true;
							break;
						case UI.CMD_QUIT : 
							turnFinished = true;
							gameOver = true;
							break;
					}
					}
				}
				if(currPlayer.inJail==true){
					turnFinished=true;
				}
			}//end while in jail
			else{
			//switch statements
			//switch statements
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
//					int steps = Integer.parseInt(JOptionPane.showInputDialog("Please enter the amount you would like to move: "));
//					currPlayer.move(+steps);
//					ui.display();
					currPlayer.doTransaction(-(currPlayer.getBalance()+1));
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
			}
			
		} while (!turnFinished);
		currPlayer.turnsInJail++;
		dice.resetCount();
		invalidInput = true;

		if (j <= Chance.N.size()) {
			j++;
			if (j == Chance.N.size()) {
				j = 0;
			}
		}
		if (i <= s.numbers.size()) {
			i++;
			if (i == s.numbers.size()) {
				i = 0;
			}

		}

		return;
	}

	// functions
	public static void GoToGo() {
		ui.displayString(Community_Chest_Card.getCardMessage());

		currPlayer.Moving(0);
		currPlayer.doTransaction(+GO_MONEY);
		ui.displayPassedGo(currPlayer);
		ui.displayBankTransaction(currPlayer);

	}

	public static void GoToOLD() {
		ui.displayString(Community_Chest_Card.getCardMessage());

		currPlayer.Moving(1);
	}

	public static void GoToJail() {
		ui.displayString(Community_Chest_Card.getCardMessage());

		currPlayer.Moving(10);
	}

	public static void PayHospital() {
		ui.displayString(Community_Chest_Card.getCardMessage());

		currPlayer.doTransaction(-100);
		ui.displayBankTransaction(currPlayer);
		ui.displayBalance(currPlayer);

	}

	public static void PayDoctorFee() {
		ui.displayString(Community_Chest_Card.getCardMessage());

		currPlayer.doTransaction(-50);
		ui.displayBankTransaction(currPlayer);
		ui.displayBalance(currPlayer);

	}

	public static void PayDoctorinsurence() {
		ui.displayString(Community_Chest_Card.getCardMessage());

		currPlayer.doTransaction(-50);
		ui.displayBankTransaction(currPlayer);
		ui.displayBalance(currPlayer);

	}

	public static void BankError() {
		ui.displayString(Community_Chest_Card.getCardMessage());

		currPlayer.doTransaction(+200);
		ui.displayBankTransaction(currPlayer);
		ui.displayBalance(currPlayer);

	}

	public static void Amunity() {
		ui.displayString(Community_Chest_Card.getCardMessage());

		currPlayer.doTransaction(+100);
		ui.displayBankTransaction(currPlayer);
		ui.displayBalance(currPlayer);

	}

	public static void Inheritence() {
		ui.displayString(Community_Chest_Card.getCardMessage());

		currPlayer.doTransaction(+100);
		ui.displayBankTransaction(currPlayer);
		ui.displayBalance(currPlayer);

	}

	public static void Stock() {
		ui.displayString(Community_Chest_Card.getCardMessage());

		currPlayer.doTransaction(+50);
		ui.displayBankTransaction(currPlayer);
		ui.displayBalance(currPlayer);

	}

	public static void Interest() {
		ui.displayString(Community_Chest_Card.getCardMessage());

		currPlayer.doTransaction(+25);
		ui.displayBankTransaction(currPlayer);
		ui.displayBalance(currPlayer);

	}

	public static void RefundInterest() {
		ui.displayString(Community_Chest_Card.getCardMessage());

		currPlayer.doTransaction(+20);
		ui.displayBankTransaction(currPlayer);
		ui.displayBalance(currPlayer);

	}

	public static void BeautyPresent() {
		ui.displayString(Community_Chest_Card.getCardMessage());

		currPlayer.doTransaction(+10);
		ui.displayBankTransaction(currPlayer);
		ui.displayBalance(currPlayer);

	}

	public static void BDPresent() {
		ui.displayString(Community_Chest_Card.getCardMessage());

		currPlayer.doTransaction(+10);
		ui.displayBankTransaction(currPlayer);
		ui.displayBalance(currPlayer);
	}

	public static void GetOJail() {
		ui.displayString(Community_Chest_Card.getCardMessage());
		currPlayer.JailCard=true;
	}

	public static void PayOrChance() {
		ui.displayString(Community_Chest_Card.getCardMessage());
		ui.displayString("Choose pay fine or chance ");
		ui.inputString();
		if (ui.getStringC().equals("pay fine")) {
			currPlayer.doTransaction(-10);
			ui.displayBankTransaction(currPlayer);
			ui.displayBalance(currPlayer);
		} else if (ui.getStringC().equals("chance")) {
			Chance.CardCond(j, Chance.ShuffleDeck());

		}

	}

	// chance
	public static void GoToGoChance() {
		ui.displayString(Chance_Card.getCardMessageC());
		currPlayer.Moving(0);
		currPlayer.doTransaction(+GO_MONEY);
		ui.displayPassedGo(currPlayer);
		ui.displayBankTransaction(currPlayer);

	}

	public static void GoToJailChance() {
		ui.displayString(Chance_Card.getCardMessageC());

		currPlayer.Moving(10);
	}

	public static void GotoPall() {
		ui.displayString(Chance_Card.getCardMessageC());
		currPlayer.MovingToPall();

	}

	public static void GotoStation() {
		ui.displayString(Chance_Card.getCardMessageC());
		currPlayer.MovingToMary();

	}

	public static void GotoTry() {
		ui.displayString(Chance_Card.getCardMessageC());
		currPlayer.MovingToTrg();

	}

	public static void GotToMay() {
		ui.displayString(Chance_Card.getCardMessageC());
		currPlayer.Moving(39);

	}

	public static void GobackThree() {
		ui.displayString(Chance_Card.getCardMessageC());
		currPlayer.move(-3);

	}

	public static void School() {
		ui.displayString(Chance_Card.getCardMessageC());

		currPlayer.doTransaction(-150);
		ui.displayBankTransaction(currPlayer);
		ui.displayBalance(currPlayer);
	}

	public static void Drunk() {
		ui.displayString(Chance_Card.getCardMessageC());

		currPlayer.doTransaction(-20);
		ui.displayBankTransaction(currPlayer);
		ui.displayBalance(currPlayer);
	}

	public static void Speeding() {
		ui.displayString(Chance_Card.getCardMessageC());

		currPlayer.doTransaction(-15);
		ui.displayBankTransaction(currPlayer);
		ui.displayBalance(currPlayer);
	}

	public static void mature() {
		ui.displayString(Chance_Card.getCardMessageC());

		currPlayer.doTransaction(+150);
		ui.displayBankTransaction(currPlayer);
		ui.displayBalance(currPlayer);
	}

	public static void WonCrosswards() {
		ui.displayString(Chance_Card.getCardMessageC());

		currPlayer.doTransaction(+100);
		ui.displayBankTransaction(currPlayer);
		ui.displayBalance(currPlayer);
	}

	public static void BankDivedend() {
		ui.displayString(Chance_Card.getCardMessageC());

		currPlayer.doTransaction(+50);
		ui.displayBankTransaction(currPlayer);
		ui.displayBalance(currPlayer);
	}

	public static void GetOfJail() {
		ui.displayString(Chance_Card.getCardMessageC());

		currPlayer.JailCard=true;
	}

	public static void GeneralRepair() {
		ui.displayString(Chance_Card.getCardMessageC());

		currPlayer.doTransaction(-(Site.getNumBuildings() * 25));
		ui.displayBankTransaction(currPlayer);
		ui.displayBalance(currPlayer);
	}

	public static void CostRepair() {
		ui.displayString(Chance_Card.getCardMessageC());

		currPlayer.doTransaction(-(Site.getNumBuildings() * 40));
		ui.displayBankTransaction(currPlayer);
		ui.displayBalance(currPlayer);
	}

	public void nextPlayer() {
		currPlayer = players.getNextPlayer(currPlayer);
		return;
	}

	public void decideWinner() {
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

	public void displayGameOver() {
		ui.displayGameOver();
		return;
	}

	public boolean isGameOver() {
		return gameOver;
	}
}