package monopoly;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

/*
An object of class PairOfDice represents a pair of dice,
where each die shows a number between 1 and 6.  The dice
can be rolled, which randomizes the numbers showing on the
dice.
*/

public class DiceRoll {

static private int die1;   // Number showing on the first die.
static private int die2;   // Number showing on the second die.

public DiceRoll() {
       // Constructor.  Rolls the dice, so that they initially
       // show some random values.
   roll();  // Call the roll() method to roll the dice.
}

public void roll() {
       // Roll the dice by setting each of the dice to be
       // a random number between 1 and 6.
   die1 = (int)(Math.random()*6) + 1;
   die2 = (int)(Math.random()*6) + 1;
}
        
public int getDie1() {
     // Return the number showing on the first die.
  return die1;
}

public int getDie2() {
     // Return the number showing on the second die.
  return die2;
}

public int getTotal() {
     // Return the total showing on the two dice.
  return die1 + die2;
}



	public void rollOfDice() {

		DiceRoll d = new DiceRoll();
	
			JDialog.setDefaultLookAndFeelDecorated(true);

			int roll_dice = (JOptionPane.showConfirmDialog(null, "Would you like to roll the dice? ", null,
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE));

			if (roll_dice == JOptionPane.NO_OPTION) {
				JOptionPane.showConfirmDialog(null, "You have decided not to roll and therefore cannot proceed! ");
			} else if (roll_dice == JOptionPane.YES_OPTION) {
				JOptionPane.showMessageDialog(null,
						" The number of the first dice is: " + getDie1() + "\n The number of the second dice is: "
								+ d.getDie2() + "\n The sum of the two die is: " + d.getTotal());
			}

			if (d.getDie1()== d.getDie2()) {
				JOptionPane.showMessageDialog(null, "You have to roll again as you have rolled a double");
				d.rollOfDice();
			}

		}
	}


