package src;
/* This class controls the manner in which the information panel works, such as the font we use, enabling scrolling. 
 * This class also controls the addText function that we use to get input from our command panel*/ 
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class InfoPanel extends JPanel {


	private static final long serialVersionUID = 1L;	
	private static final int TEXT_AREA_HEIGHT = 40;
	private static final int CHARACTER_WIDTH = 39;
	private static final int FONT_SIZE = 14;
	 JTextArea textArea = new JTextArea(TEXT_AREA_HEIGHT, CHARACTER_WIDTH);
	JScrollPane scrollPane = new JScrollPane(textArea); // Enabling scrolling
	DefaultCaret caret = (DefaultCaret)textArea.getCaret();
	String temp;
	
	//public static int dice;
	
	InfoPanel () {
		textArea.setEditable(false);
		textArea.setFont(new Font("Times New Roman", Font.PLAIN, FONT_SIZE)); // Here we are setting the font, and calling in the size
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);
		return;
	}
	
	public void addText (String text) // This is out add text function that allows us to input text via our command panel
	{
		
		textArea.setText(textArea.getText()+ text + " \n\n");	
}
}