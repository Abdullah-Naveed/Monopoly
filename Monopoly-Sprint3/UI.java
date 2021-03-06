import java.awt.BorderLayout;

	import javax.swing.JFrame;

	import java.util.ArrayList;

	public class UI {

		private static final int FRAME_WIDTH = 1200;
		private static final int FRAME_HEIGHT = 800;
		private static final String CURRENCY = " pounds";
		
		public static final int CMD_QUIT = 0;
		public static final int CMD_DONE = 1;
		public static final int CMD_ROLL = 2;
		public static final int CMD_BUY = 3;
		public static final int CMD_PAY_RENT = 4;
		public static final int CMD_AUCTION = 5;
		public static final int CMD_PROPERTY = 6;
		public static final int CMD_BALANCE = 7;
		public static final int CMD_BANKRUPT = 8;
		public static final int CMD_HELP = 9;
		public static final int CMD_CHEAT = 10;
		public static final int CMD_BUILD = 11;
		public static final int CMD_DEMOLISH = 12;
		public static final int CMD_MORTGAGE = 13;
		public static final int CMD_REDEEM = 14;
		
		public static final int ERR_SYNTAX = 0;
		public static final int ERR_DOUBLE_ROLL = 1;
		public static final int ERR_NO_ROLL = 2;
		public static final int ERR_INSUFFICIENT_FUNDS = 3;
		public static final int ERR_NOT_A_PROPERTY = 4;
		public static final int ERR_RENT_ALREADY_PAID = 5;
		public static final int ERR_NOT_OWNED = 6;
		public static final int ERR_IS_OWNED = 7;
		public static final int ERR_SELF_OWNED = 8;
		public static final int ERR_RENT_OWED= 9;
		public static final int ERR_BUILD=10;
		public static final int ERR_DEMOLISH = 11;
		public static final int ERR_MORTGAGE = 12;
		public static final int ERR_REDEEM = 13;
		
		private final String[] errorMessages = {
			"Error: Not a valid command.",
			"Error: Too many rolls this turn.",
			"Error: You have a roll to play.",
			"Error: You don't have enough money.",
			"Error: This square is not a property.",
			"Error: You have already paid the rent.",
			"Error: The property is not owned.",
			"Error: The property is already owned.",
			"Error: You own the property.",
			"Error: You owe rent.",
			"Error: You do not own all properties in the same color group. Therefore, you can not build a house!",
			"Error: You do not own any properties(houses) and therfore cannot demolish anything! ",
			"Error: You cannot mortgage this property ",
			"Error: You cannot redeem as this property is not mortgaged"
		};
	
		private JFrame frame = new JFrame();
		private BoardPanel boardPanel;	
		private InfoPanel infoPanel = new InfoPanel();
		private CommandPanel commandPanel = new CommandPanel();
		private String string;
		private boolean done;
		private int commandId;

		UI (ArrayList<Player> players) {
			boardPanel = new BoardPanel(players);
			frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
			frame.setTitle("Monopoly");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.add(boardPanel, BorderLayout.LINE_START);
			frame.add(infoPanel, BorderLayout.LINE_END);
			frame.add(commandPanel,BorderLayout.PAGE_END);
			frame.setResizable(false);
			frame.setVisible(true);
			return;
		}
		
		UI(){
			
		}

	// INPUT METHODS
		
		public void inputName (int numPlayers) {
			if (numPlayers == 0) {
				infoPanel.displayString("Enter new player name (" + boardPanel.getTokenName(numPlayers) + "):");			
			} else {
				infoPanel.displayString("Enter new player name (" + boardPanel.getTokenName(numPlayers)  +  ") or done:");
			}
			commandPanel.inputString();
			string = commandPanel.getString();
			if ( (numPlayers > 0) && (string.trim().toLowerCase().equals("done")) ) {
				done = true;
			} else {
				done = false;
			}
			infoPanel.displayString("> " + string);
			return;
		}
		
		public void inputCommand (Player player) {
			boolean inputValid = false;
			do {
				infoPanel.displayString(player + " type your command:");
				commandPanel.inputString();
				string = commandPanel.getString();
				infoPanel.displayString("> " + string);
				string = commandPanel.getString();
				string = string.toLowerCase();
				string = string.trim();
				string = string.replaceAll("( )+", " ");
				switch (string) {
					case "build" :
						commandId = CMD_BUILD;
						inputValid = true;
						break;
					case "quit" :
						commandId = CMD_QUIT;
						inputValid = true;
						break;
					case "done" :
						commandId = CMD_DONE;
						inputValid = true;
						break;
					case "roll" :
						commandId = CMD_ROLL;
						inputValid = true;
						break;
					case "buy" :
						commandId = CMD_BUY;				
						inputValid = true;
						break;
					case "pay rent" :
						commandId = CMD_PAY_RENT;
						inputValid = true;
						break;
					case "auction" :
						commandId = CMD_AUCTION;
						inputValid = true;
						break;
					case "property" :
						commandId = CMD_PROPERTY;
						inputValid = true;
						break;
					case "balance" :
						commandId = CMD_BALANCE;
						inputValid = true;
						break;
					case "bankrupt" :
						commandId = CMD_BANKRUPT;
						inputValid = true;
						break;
					case "demolish" :
						commandId = CMD_DEMOLISH;
						inputValid = true;
						break;
					case "mortgage" :
						commandId= CMD_MORTGAGE;
						inputValid = true;
						break;
					case "redeem" :
						commandId = CMD_REDEEM;
						inputValid = true;
						break;
					case "help" :
						commandId = CMD_HELP;
						inputValid = true;
						break;
					case "cheat" :
						commandId = CMD_CHEAT;
						inputValid = true;
						break;
					default:
						inputValid = false;
					}
				if (!inputValid) {
					displayError(ERR_SYNTAX);
					displayCommandHelp ();
				}
			} while (!inputValid);
			if (commandId == CMD_DONE) {
				done = true;
			} else {
				done = false;
			}		
			return;
		}
		
		public String getString () {
			return string; 
		}
		
		public String getTokenName (int tokenId) {
			return boardPanel.getTokenName(tokenId);
		}
		
		public int getCommandId () {
			return commandId;
		}
		
		public boolean isDone () {
			return done;
		}
		
		
	// OUTPUT METHODS
		
		public boolean checking(Property property, String position){
			
				if(position.equals(property.getName())){
					return true;
			}
			return false;
		}
		
		public boolean colorMatching(Property prop, ArrayList<Property> properties){
			String color = prop.getColor();
			int counter = 0;
			
			for(int i=0; i<properties.size(); i++){
				if(color.equals(properties.get(i).getColor())){
					counter++;
				}
			}
			
			switch(color)
			{
			case "brown": 
				if(counter==2){
					return true;
				}
				return false;
			case "blue":
				if(counter==3){
					return true;
				}
				return false;
			case "purple":
				if(counter==3){
					return true;
				}
				return false;
			case "orange":
				if(counter==3){
					return true;
				}
				return false;
			case "red":
				if(counter==3){
					return true;
				}
				return false;
			case "yellow":
				if(counter==3){
					return true;
				}
				return false;
			case "green":
				if(counter==3){
					return true;
				}
				return false;
			case "navy":
				if(counter==2){
					return true;
				}
				return false;
			}
			
			return true;
		}
		
		public String InputOutput(){
			commandPanel.inputString();
			string = commandPanel.getString();
			return string;
		}

		public void display () {
			boardPanel.refresh();
			return;
		}
		
		public void displayString (String string) {
			infoPanel.displayString(string);
			return;
		}
		
		public void displayBankTransaction (Player player) {
			if (player.getTransaction() >= 0) {
				infoPanel.displayString(player + " receives " + player.getTransaction() + CURRENCY + " from the bank.");
			} else {
				infoPanel.displayString(player + " pays " + (-player.getTransaction()) + CURRENCY + " to the bank.");			
			}
			return;
		}
		
		public void displayTransaction (Player fromPlayer, Player toPlayer) {
			infoPanel.displayString(fromPlayer + " pays " + toPlayer.getTransaction() + CURRENCY + " to " + toPlayer);
			return;
		}
		
		public void displayDice (Player player, Dice dice) {
			infoPanel.displayString(player + " rolls " + dice + ".");
			return;
		}
		
		public void displayRollDraw () {
			infoPanel.displayString("Draw");
			return;
		}
		
		public void displayRollWinner (Player player) {
			infoPanel.displayString(player + " wins the roll.");
			return;
		}
		
		public void displayGameOver () {
			infoPanel.displayString("GAME OVER");
			return;
		}
		
		public void displayCommandHelp () {
			infoPanel.displayString("Available commands: roll, pay rent, buy, property, balance, done, quit. ");
			return;
		}
		
		public void displayBalance (Player player) {
			infoPanel.displayString(player + "'s balance is " + player.getBalance() + CURRENCY);
			return;
		}
		
		public void displayError (int errorId) {
			infoPanel.displayString(errorMessages[errorId]);
			return;
		}
		
		public void displayPassedGo (Player player) {
			infoPanel.displayString(player + " passed Go.");
			return;
		}
		
		public void displayLatestProperty (Player player) {
			infoPanel.displayString(player + " bought " + player.getLatestProperty());
			return;
		}
		
		public void displayNoOfHouses(Player player, int n){
			if(n==1){
				infoPanel.displayString(player + " has built a house on this property.");
			}
			else{
				infoPanel.displayString(player + " has built " + n + " house's on this property.");
			}
			return;
		}
		
		public void displayProperty (Player player) {
			ArrayList<Property> propertyList = player.getProperties();
			//properties
			if (propertyList.size() == 0) {
				infoPanel.displayString(player + " owns no property.");
			} 
			else {
				infoPanel.displayString(player + " owns the following property/properties...");
				for (Property p : propertyList) {
					infoPanel.displayString(p.getName() + ", rent " + p.getRent());				
				}
			}	
		}
		
		public void displayHouse(Player player){
			ArrayList<Property> houseList = player.getHouses();
			Property h;
			//houses
			if (houseList.size() == 0) {
				infoPanel.displayString(player + " owns no houses.");
			} 
			else {
				infoPanel.displayString(player + " owns houses on the following property/properties...");
				for(int i=0; i<houseList.size(); i++){
					h = houseList.get(i);
					infoPanel.displayString(h.getName());
				}
			}
		}
		
		public void displayHotel(Player player){
			ArrayList<Property> hotelList = player.getHotels();
			Property hlist;
			//hotels
			if (hotelList.size() == 0) {
				infoPanel.displayString(player + " owns no hotels.");
			} 
			else {
				infoPanel.displayString(player + " owns hotels on the following property/properties...");
				for(int i=0; i<hotelList.size(); i++){
					hlist = hotelList.get(i);
					infoPanel.displayString(hlist.getName());
				}
			}
		}
		
		public void displaySquare (Player player, Board board) {
			infoPanel.displayString(player + " arrives at " + board.getSquare(player.getPosition()).getName() + ".");
			if (board.isProperty(player.getPosition())) {
				Property property = board.getProperty(player.getPosition());
				if (property.isOwned()) {
					infoPanel.displayString("The property is owned by " + property.getOwner() + ". Rent is " + property.getRent() + CURRENCY + ".");				
				} else {
					infoPanel.displayString("The property is not owned. Rent is " + property.getRent() + CURRENCY + ".");								
				}
			}
			return;
		}
		
		public void displayAssets (Player player) {
			infoPanel.displayString(player + " has assets of " + player.getAssets() + CURRENCY);
			return;
		}
		
		public void displayWinner (Player player) {
			infoPanel.displayString("The winner is " + player);
			return;
		}
		
		public void displayDraw (ArrayList<Player> players) {
			infoPanel.displayString("The following players drew the game " + players);
			return;
		}

		public void househotelCost(Player player, String color, int noOfHouses) {
			int brown = 50, blue = 50;
			int purple = 100, orange = 100;
			int red = 150, yellow = 150;
			int green = 200, navy = 200;
			
			
			if(color.equals("brown")){
				player.doTransaction(-(brown*noOfHouses));
				displayString((brown*noOfHouses) + " has been deducted from your balance.");
			}
			else if(color.equals("blue")){
				player.doTransaction(-(blue*noOfHouses));
				displayString((blue*noOfHouses) + " has been deducted from your balance.");
			}
			else if(color.equals("purple")){
				player.doTransaction(-(purple*noOfHouses));
				displayString((purple*noOfHouses) + " has been deducted from your balance.");
			}
			else if(color.equals("orange")){
				player.doTransaction(-(orange*noOfHouses));
				displayString((orange*noOfHouses) + " has been deducted from your balance.");
			}
			else if(color.equals("red")){
				player.doTransaction(-(red*noOfHouses));
				displayString((red*noOfHouses) + " has been deducted from your balance.");
			}
			else if(color.equals("yellow")){
				player.doTransaction(-(yellow*noOfHouses));
				displayString((yellow*noOfHouses) + " has been deducted from your balance.");
			}
			else if(color.equals("green")){
				player.doTransaction(-(green*noOfHouses));
				displayString((green*noOfHouses) + " has been deducted from your balance.");
			}
			else if(color.equals("navy")){
				player.doTransaction(-(navy*noOfHouses));
				displayString((navy*noOfHouses) + " has been deducted from your balance.");
			}
		}
		public void DemolishCost(Player player, String color, int noOfHouses) {
			int brown = 50, blue = 50;
			int purple = 100, orange = 100;
			int red = 150, yellow = 150;
			int green = 200, navy = 200;
			
			
			if(color.equals("brown")){
				player.doTransaction(+(brown*noOfHouses)/2);
				displayString((brown*noOfHouses) + " has been deducted from your balance.");
			}
			else if(color.equals("blue")){
				player.doTransaction(+(blue*noOfHouses)/2);
				displayString((blue*noOfHouses) + " has been deducted from your balance.");
			}
			else if(color.equals("purple")){
				player.doTransaction(+(purple*noOfHouses)/2);
				displayString((purple*noOfHouses) + " has been deducted from your balance.");
			}
			else if(color.equals("orange")){
				player.doTransaction(+(orange*noOfHouses)/2);
				displayString((orange*noOfHouses) + " has been deducted from your balance.");
			}
			else if(color.equals("red")){
				player.doTransaction(+(red*noOfHouses)/2);
				displayString((red*noOfHouses) + " has been deducted from your balance.");
			}
			else if(color.equals("yellow")){
				player.doTransaction(+(yellow*noOfHouses)/2);
				displayString((yellow*noOfHouses) + " has been deducted from your balance.");
			}
			else if(color.equals("green")){
				player.doTransaction(+(green*noOfHouses)/2);
				displayString((green*noOfHouses) + " has been deducted from your balance.");
			}
			else if(color.equals("navy")){
				player.doTransaction(+(navy*noOfHouses)/2);
				displayString((navy*noOfHouses) + " has been deducted from your balance.");
			}
		}






	}
