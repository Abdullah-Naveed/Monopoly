

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class InfoPanel extends JPanel {
BankAccount info= new BankAccount();

//Player PlayerEntry= new Player();
	private static final long serialVersionUID = 1L;
	private static final int TEXT_AREA_HEIGHT = 40;
	private static final int CHARACTER_WIDTH = 39;
	private static final int FONT_SIZE = 14;
	DiceRoll die = new DiceRoll();
	//static int dice;
  JTextArea textArea = new JTextArea(TEXT_AREA_HEIGHT, CHARACTER_WIDTH);
	JScrollPane scrollPane = new JScrollPane(textArea);
	DefaultCaret caret = (DefaultCaret)textArea.getCaret();

	public static int dice;
	
	InfoPanel () {
		textArea.setEditable(false);
		textArea.setFont(new Font("Times New Roman", Font.PLAIN, FONT_SIZE));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);
		return;
	}
	
	
	public void addText (String text) 
	{
		Player test= new Player();
		BankAccount play= new BankAccount();
		textArea.setText(textArea.getText()+ text + " \n\n ");
		switch (text) 
		{
		case "balance":
			textArea.append(" > > > Your balance is: " + play.GetPlayerBalance() + "\n\n");
			break;
		case "position":
			textArea.append(" > > > Your position is: " + test.getPosition() + "\n\n");
			break;
		case "help":
			textArea.append("List of acceptable commands are: \n - balance \n - position \n - roll \n - property \n - buy \n - done \n - quit \n\n");
			break;
//		case "quit":
//			textArea.append("TO BE COMPLETED \n");
//			break;
//		case "roll":
//			textArea.append("TO BE COMPLETED \n");
//			break;
//		case "done":
//			textArea.append("TO BE COMPLETED \n");
//			break;
		}
		
		if (!text.equals("balance") && !text.equals("position") && !text.equals("help") && !text.equals("roll") && !text.equals("property") && !text.equals("buy") && !text.equals("done"))
		{
			textArea.setText(textArea.getText()+" > > Error - The command you've entered is invalid \n  > > Type in the word ''help'' for a list of acceptable commands \n\n");
		}	
		
	}
		
		}
