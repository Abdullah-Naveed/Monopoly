package src;

import java.util.ArrayList;

public class property {
	protected static final int TYP_GO = 0;
	protected static final int TYP_SITE = 1;
	protected static final int TYP_STATION = 2;
	protected static final int TYP_UTILITY = 3;
	protected static final int TYP_COMMUNITY = 4;
	protected static final int TYP_CHANCE = 5;
	protected static final int TYP_JAIL = 6;
	protected static final int TYP_PARKING = 7;
	protected static final int TYP_GOTO_JAIL = 8;
	protected static final int TYP_TAX = 9;
	public static final int NUM_SQUARES = 40; // This is the total number of squares we have on out board
	protected static final int NUM_PROPERTIES = 28;

	protected static final String[] ALL_BOARD_NAMES = { "GO", "Old Kent Rd", "Community Chest", "Whitechapel Rd",
			"Income Tax", "King's Cross Station", "The Angel Islington", "Chance", "Euston Rd", "Pentonville Rd",
			"Jail", "Pall Mall", "Electric Company", "Whitehall", "Northumberland Ave", "Marylebone Station", "Bow St",
			"Community Chest", "Marlborough St", "Vine St", "Free Parking", "Strand", "Chance", "Fleet St",
			"Trafalgar Sq", "Fenchurch St Station", "Leicester Sq", "Coventry St", "Water Works", "Piccadilly",
			"Go to Jail", "Regent St", "Oxford St", "Community Chest", "Bond St", "Liverpool Street Station", "Chance",
			"Park Lane", "Super Tax", "Mayfair" };
	protected static final int[] PROPERTY_VALUES = { 60, 60, 200, 100, 100, 120, 140, 150, 140, 160, 200, 180, 180, 200,
			220, 220, 240, 200, 260, 260, 150, 280, 300, 300, 320, 200, 350, 400 };
	protected static final int[][] PROPERTY_RENTS = { { 2, 10, 30, 90, 160, 250 }, { 4, 20, 60, 180, 320, 450 },
			{ 25, 50, 100, 200, 200, 200 }, { 6, 30, 90, 27, 0, 400, 550 }, { 6, 30, 90, 270, 400, 550 },
			{ 8, 40, 100, 300, 450, 600 }, { 10, 50, 150, 450, 625, 750 }, { 4, 10, 0, 0, 0, 0 },
			{ 10, 50, 150, 450, 625, 750 }, { 12, 60, 180, 500, 700, 900 }, { 14, 70, 200, 550, 750, 950 },
			{ 25, 50, 100, 200, 200, 200 }, { 14, 70, 200, 550, 750, 950 }, { 16, 8, 0, 220, 600, 800, 1000 },
			{ 18, 90, 250, 700, 875, 1050 }, { 4, 10, 0, 0, 0, 0 }, { 18, 90, 250, 700, 875, 1050 },
			{ 25, 50, 100, 20, 0, 200, 200 }, { 20, 100, 300, 750, 925, 1100 }, { 25, 50, 100, 200, 200, 200 },
			{ 22, 110, 330, 800, 975, 1150 }, { 22, 110, 330, 800, 975, 1150 }, { 4, 10, 0, 0, 0, 0 },
			{ 22, 120, 360, 850, 1025, 1200 }, { 26, 130, 390, 900, 1100, 1275 }, { 26, 130, 390, 900, 1100, 1275 },
			{ 28, 150, 450, 1000, 1200, 14, 00 }, { 25, 50, 100, 200, 200, 200 }, { 35, 175, 500, 1100, 1300, 1500 } };
	protected static final String GO_NAME = "GO";
	protected static final String[] SITE_NAMES = { "Old Kent Rd", "Whitechapel Rd", "The Angel Islington", "Euston Rd",
			"Pentonville Rd", "Pall Mall", "Whitehall", "Northumberland Ave", "Bow St", "Marlborough St", "Vine St",
			"Strand", "Fleet St", "Trafalgar Sq", "Leicester Sq", "Coventry St", "Piccadilly", "Regent St", "Oxford St",
			"Bond St", "Park Lane", "Mayfair" }; // These are the names of the sites which a player can purchase
	protected static final String[] SITE_SHORT_NAMES = { "kent", "whitechapel", "angel", "euston", "pentonville",
			"mall", "whitehall", "northumberland", "bow", "marlborough", "vine", "strand", "fleet", "trafalgar",
			"leicester", "coventry", "piccadilly", "regent", "oxford", "bond", "ark", "mayfair" };
	protected static final int[] SITE_PRICES = { 60, 60, 100, 100, 120, 140, 140, 160, 180, 180, 200, 220, 220, 240,
			260, 260, 280, 300, 300, 320, 350, 400 }; // These are the respective prices of the sites from the first position
	protected static final int[][] SITE_RENTS = { { 2, 10, 30, 90, 160, 250 }, { 4, 20, 60, 180, 320, 450 },
			{ 6, 30, 90, 270, 400, 550 }, { 6, 30, 90, 270, 400, 550 }, { 8, 40, 100, 300, 450, 600 },
			{ 10, 50, 150, 450, 625, 750 }, { 10, 50, 150, 450, 625, 750 }, { 12, 60, 180, 500, 700, 900 },
			{ 14, 70, 200, 550, 750, 950 }, { 25, 50, 100, 200, 200, 200 }, { 14, 70, 200, 550, 750, 950 },
			{ 16, 80, 220, 600, 800, 1000 }, { 18, 90, 250, 700, 875, 1050 }, { 4, 10, 0, 0, 0, 0 },
			{ 18, 90, 250, 700, 875, 1050 }, { 25, 50, 100, 200, 200, 200 }, { 20, 100, 300, 750, 925, 1100 },
			{ 25, 50, 100, 200, 200, 200 }, { 22, 110, 330, 800, 975, 1150 }, { 22, 110, 330, 800, 975, 1150 },
			{ 22, 120, 360, 850, 1025, 1200 }, { 26, 130, 390, 900, 1100, 1275 }, { 26, 130, 390, 900, 1100, 1275 },
			{ 28, 150, 450, 1000, 1200, 1400 }, { 25, 50, 100, 200, 200, 200 }, { 35, 175, 500, 1100, 1300, 1500 } };
	protected static final int[] HOUSE_PRICE = { 50, 50, 50, 50, 50, 100, 100, 100, 100, 100, 100, 150, 50, 150, 150,
			150, 150, 150, 200, 200, 200, 200, 200 };
	protected static final int[] SITE_MORTGAGE_VALUE = { 50, 50, 50, 50, 60, 70, 70, 80, 90, 90, 100, 110, 110, 120,
			150, 150, 150, 200, 200, 200, 175, 200 };
	protected static final String[] STATION_NAMES = { "King's Cross Station", "Marylebone Station",
			"Fenchurch St Station", "Liverpool St Station" };
	protected static final String[] STATION_SHORT_NAMES = { "kings", "marylebone", "fenchurch", "liverpool" };
	protected static final int STATION_PRICE = 200;
	protected static final int[] STATION_RENTS = { 25, 50, 100, 200, 200, 200 };
	protected static final int STATION_MORTGAGE_VALUE = 100;
	protected static final String[] UTILITY_NAMES = { "Electric Company", "Water Works" };
	protected static final int UTILITY_PRICE = 150;
	protected static final int[] UTILITY_RENTS = { 4, 10 };
	protected static final int UTILITY_MORTGAGE_VALUE = 75;
	protected static final String COMMUNITY_NAME = "Community Chest";
	protected static final String CHANCE_NAME = "Chance";
	protected static final String JAIL_NAME = "Jail (Just Visiting)";
	protected static final String PARKING_NAME = "Free Parking";
	protected static final String GOTO_JAIL_NAME = "Go To Jail";
	protected static final String[] TAX_NAMES = { "Income Tax", "Super Tax" };
	protected static final int[] TAX_AMOUNTS = { 200, 100 };
	protected static final int COL_BROWN = 0;
	protected static final int COL_LIGHT_BLUE = 1;
	protected static final int COL_PINK = 2;
	protected static final int COL_ORANGE = 3;
	protected static final int COL_RED = 4;
	protected static final int COL_YELLOW = 5;
	protected static final int COL_GREEN = 6;
	protected static final int COL_DARK_BLUE = 7;
	protected static final int[] SITE_COLOURS = { COL_BROWN, COL_BROWN, COL_LIGHT_BLUE, COL_LIGHT_BLUE, COL_LIGHT_BLUE,
												  COL_PINK, COL_PINK, COL_PINK, COL_ORANGE, COL_ORANGE, COL_ORANGE, COL_RED, 
												  COL_RED, COL_RED, COL_YELLOW,COL_YELLOW, COL_YELLOW, COL_GREEN, COL_GREEN,
												  COL_GREEN, COL_DARK_BLUE, COL_DARK_BLUE };
	protected static final String[] COLOUR_GROUP_NAME = { "brown", "light blue", "pink", "orange", "red", "yellow", "green", "dark blue" };
	protected static final int[] NUM_IN_GROUP = { 2, 3, 3, 3, 3, 3, 3, 2 };
	
	protected static boolean[] OWNED = {false,false,false,false,false,false,false,false,false,false,false,false,false,false,
										false,false,false,false,false,false,false,false,false,false,false,false,false,false,
										false,false,false,false,false,false,false,false,false,false,false,false}; // This array enables the current player to land on each position depending on the roll of the dice
	public static void settingProperty(String allBoardNames) 
	{
		ArrayList<PropertyValues> p = new ArrayList<PropertyValues>(); // This is the array list to store all the values of the properties, the for loops we link the names to the positions on the board along with the rent too 

		for (int i = 0; i < ALL_BOARD_NAMES.length; i++) 
		{

			for (int j = 0; j < SITE_NAMES.length; j++) 
			{
				if (allBoardNames.equals(SITE_NAMES[j])) 
				{
					p.add(new PropertyValues(SITE_PRICES[j], SITE_NAMES[j]));
				}

			}
		}

		for (int i = 0; i < ALL_BOARD_NAMES.length; i++) {

			for (int j = 0; j < STATION_NAMES.length; j++) {
				if (allBoardNames.equals(STATION_NAMES[j])) {
					p.add(new PropertyValues(STATION_PRICE, STATION_NAMES[j]));

				}

			}

		}
		for (int i = 0; i < ALL_BOARD_NAMES.length; i++) 
		{

			for (int j = 0; j < TAX_NAMES.length; j++) {
				if (allBoardNames.equals(TAX_NAMES[j])) {
					p.add(new PropertyValues(TAX_AMOUNTS[j], TAX_NAMES[j]));

				}

			}

		}
		for (int i = 0; i < ALL_BOARD_NAMES.length; i++) 
		{

			for (int j = 0; j < UTILITY_NAMES.length; j++) {
				if (allBoardNames.equals(UTILITY_NAMES[j])) {
					p.add(new PropertyValues(UTILITY_PRICE, UTILITY_NAMES[j]));

				}

			}

		}
		for (int i = 0; i < ALL_BOARD_NAMES.length; i++) {

			for (int j = 0; j < 1; j++) {
				if (allBoardNames.equals(GO_NAME)) {
					p.add(new PropertyValues(0, GO_NAME ));

				}

			}

		}
		for (int i = 0; i < ALL_BOARD_NAMES.length; i++) {

			for (int j = 0; j < 1; j++) {
				if (allBoardNames.equals(GO_NAME)||allBoardNames.equals(CHANCE_NAME)||allBoardNames.equals( COMMUNITY_NAME)||allBoardNames.equals( PARKING_NAME)||allBoardNames.equals( JAIL_NAME)||allBoardNames.equals( GOTO_JAIL_NAME)) {
					p.add(new PropertyValues(0, GO_NAME ));
					p.add(new PropertyValues(0, CHANCE_NAME ));
					p.add(new PropertyValues(0, COMMUNITY_NAME ));
					p.add(new PropertyValues(0, PARKING_NAME ));
					p.add(new PropertyValues(0,JAIL_NAME));
					p.add(new PropertyValues(0, GOTO_JAIL_NAME));
				}

			}

		}

	}

}

