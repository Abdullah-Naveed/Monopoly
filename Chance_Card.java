import java.util.ArrayList;
import java.util.Random;

public class Chance_Card {

	ArrayList<Integer> N = new ArrayList<Integer>();
	private static final int CMD_GO = 0;
	private static final int CMD_GO_JAIL = 1;
	private static final int CMD_GO_PALL = 2;
	private static final int CMD_PASS_MARY = 3;
	private static final int CMD_TRAF_GO = 4;
	private static final int CMD_GO_MAY = 5;
	private static final int CMD_BACK = 6;
	private static final int CMD_REPAIRS = 7;
	private static final int CMD_STREET_REPAIRS = 8;
	private static final int CMD_FEES = 9;
	private static final int CMD_DRUNK = 10;
	private static final int CMD_SPEEDING = 11;
	private static final int CMD_LOANS = 12;
	private static final int CMD_COMPE = 13;
	private static final int CMD_BANKP = 14;
	private static final int CMD_OUT_JAIL = 15;
	static int num;

	// cards
	private static final String[] CARDSC = { "Advance to Go",
			"Go to jail. Move directly to jail. Do not pass Go. Do not collect £200.",
			"Advance to Pall Mall. If you pass Go collect £200.",
			"Take a trip to Marylebone Station and if you pass Go collect £200.",
			"Advance to Trafalgar Square. If you pass Go collect £200.", "Advance to Mayfair.", "Go back three spaces.",
			"Make general repairs on all of your houses. For each house pay £25. For each hotel pay £100.",
			"You are assessed for street repairs: £40 per house, £115 per hotel.", "Pay school fees of £150.",
			"Drunk in charge fine £20.", "Speeding fine £15.", "Your building loan matures. Receive £150.",
			"You have won a crossword competition. Collect £100.", "Bank pays you dividend of £50.",
			"Get out of jail free. This card may be kept until needed or sold." };

	Chance_Card() {
		super();

		return;
	}

	public void setCardNummber(int a, int[] z) {

		Chance_Card.num = z[a];

	}

	public int getCardNumber() {
		return num;

	}

	public static String getCardMessageC() {
		return CARDSC[num];

	}

	// shuffling the deck
	public Object[] ShuffleDeck() {

		Random randomGenerator = new Random();
		while (N.size() < 16) {

			int random = randomGenerator.nextInt(16);
			if (!N.contains(random)) {
				N.add(random);

			}
		}
		// conveting it to an array
		Object[] temp = N.toArray();

		return temp;

	}
	// putting the cards in order

	public int GettingCards(Object[] temp) {

		int cardnumber = (int) temp[0];
		for (int j = 0; j < temp.length - 1; j++) {
			temp[j] = temp[j + 1];
			if (j == temp.length - 1) {
				temp[j] = cardnumber;
			}

		}
		return cardnumber;

	}
	//
	// public static void main(String[] args) {
	// Chance_Card s = new Card();
	// int i=0;
	// s.CardCond( i,s.ShuffleDeck());
	//
	//
	//
	//
	// }
	// left

	// Get out of jail free. This card may be kept until needed or sold.
	public void CardCond(int i, Object[] temp) {

		Chance_Card s = new Chance_Card();
		int[] list = new int[16];

		for (int k = 0; k < temp.length; k++) {
			list[k] = s.GettingCards(temp);
		}

		s.setCardNummber(i, list);


		// to get the certain card

		switch (s.getCardNumber()) {

		case CMD_GO:
			Monopoly.GoToGoChance();
			break;

		case CMD_GO_JAIL:
			Monopoly.GoToJailChance();
			break;
		case CMD_GO_PALL:
			Monopoly.GotoPall();
			break;

		case CMD_PASS_MARY:
			Monopoly.GotoStation();
			break;

		case CMD_TRAF_GO:
			Monopoly.GotoTry();
			break;

		case CMD_GO_MAY:
			Monopoly.GotToMay();
			break;

		case CMD_BACK:
			Monopoly.GobackThree();
			break;

		case CMD_REPAIRS:
			Monopoly.GeneralRepair();
			break;

		case CMD_STREET_REPAIRS:
			Monopoly.CostRepair();
			break;

		case CMD_FEES:
			Monopoly.School();
			break;

		case CMD_DRUNK:
			Monopoly.Drunk();
			break;

		case CMD_SPEEDING:
			Monopoly.Speeding();
			break;

		case CMD_LOANS:
			Monopoly.mature();
			break;

		case CMD_COMPE:
			Monopoly.WonCrosswards();

			break;

		case CMD_BANKP:
			Monopoly.BankDivedend();
			break;

		case CMD_OUT_JAIL:
			Monopoly.GetOfJail();
			break;

		}

	}

}