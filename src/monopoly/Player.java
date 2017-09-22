package monopoly;

import javax.swing.JOptionPane;

public class Player {

	protected static int Cash;
	protected static int position;
	protected static int count = 1;
	protected static String user1 = "", user2 = "", user3 = "", user4 = "", user5 = "", user6 = "";

	Player() {
		position = 0;
		Cash = 1500;
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

	public int GetPlayerCash() {
		return Cash;
	}

	public int getPosition() {
		return position;
	}

}
